package org.sch.classtransformer;

import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.sch.HikariAgent;
import org.sch.asm_tree_api.ClassNodeTransformationStrategy;
import org.sch.asm_tree_api.code.CheckSpringApplicationAnnotation;
import org.sch.util.CodePrinter;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * This class implements the ClassFileTransformer interface to transform class files.
 */
@Slf4j
public class TestClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

        //1. 이 로직은 main class에 Class.forName()을 삽입하여 로드시킨다. -> 이후 이 main에서 Class.forName을 읽을 것이다.
        if(HikariAgent.firstSystemClassLoad && loader.getClass().getName().contains("AppClassLoader")) { //spring Main, 시스템 클래스로더가 가장 먼저 접근하는 클래스
            //시스템 클래스로더가 읽은 파일 중 @SpringBootApplication를 가진 파일(목표)인지 스캔
            if(CheckSpringApplicationAnnotation.check(classfileBuffer)) {
            int cnt = 1; while (cnt-- > 0) log.info("MAIN CALL : " + loader.toString() + " " + className);
                log.info("[CheckSpringApplicationAnnotation]@SpringBootApplication 확인 : {}",className);
                HikariAgent.firstSystemClassLoad = false;
                return BuildClassNode(classfileBuffer, "AddAdditionalObjectToMain"); //Main Class 상단에 메서드 추가(조작)
            }
        }

        //2. 1에서 forName을 읽는 순간, 이 로직이 발동할 것이다. 이 로직은 'HikariProxyPreparedStatement'의 변조를 수행한다. 즉, main이 로드 된 이후 바로 이 로직을 통해 변조된 HikariProxyPreparedStatement가 로드된다.
        if(className.equals("com/zaxxer/hikari/pool/HikariProxyPreparedStatement")) {
            int cnt = 1; while (cnt-- > 0) log.info("HIKARI CALL : " + loader.toString() + " " + className);
            return BuildClassNode(classfileBuffer, "AddLoggerPSTMT");
        }
        //3. Connection Test
//        if(className.equals("com/zaxxer/hikari/pool/HikariProxyConnection") ||
        if(
                className.equals("com/zaxxer/hikari/pool/HikariProxyResultSet") ||
                className.equals("com/zaxxer/hikari/pool/HikariProxyStatement")) {
            int cnt = 1; while (cnt-- > 0) log.info("HIKARI_CONNECTION CALL : " + loader.toString() + " " + className);
            return BuildClassNode(classfileBuffer, "AddLoggerConn");
        }

        if(className.contains("SpringApplication")) {
            int cnt = 1; while (cnt-- > 0) log.info("SPRING CALL : " + loader.toString() + " " + className);
        }

        return classfileBuffer;
    }

    /**
     * 이 바이트코드(.class)를 특정 전략에 맞게(code) 조작 후 반환
     * @param classfileBuffer
     * @param code
     * @return
     */
    private byte[] BuildClassNode(byte[] classfileBuffer, String code) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);

        //여기서 code명에 따라 ClassNode 내부 로직을 변경하는 Class들을 호출한다.
        ClassNodeTransformationStrategy.select(cn, code); //Transfer

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cn.accept(cw);
        byte[] result = cw.toByteArray();
        CodePrinter.printClass(result, code, false); //PRINT
        return result;
    }
}


