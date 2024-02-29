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

//        if(HikariAgent.firstSystemClassLoad && loader.getClass().getName().contains("AppClassLoader")) { //spring Main, 시스템 클래스로더가 가장 먼저 접근하는 클래스
//            //시스템 클래스로더가 읽은 파일 중 @SpringBootApplication를 가진 파일(목표)인지 스캔
//            if (CheckSpringApplicationAnnotation.check(classfileBuffer)) {
//                log.info("[CheckSpringApplicationAnnotation]@SpringBootApplication 확인 : {}", className);
//                HikariAgent.firstSystemClassLoad = false;
//                //시스템 클래스로더에서 먼저 부트하고자 하는 클래스 파일들을 변조하여 저장한다.
//                return BuildClassNode(classfileBuffer, "AddAdditionalObjectToMain");
//            }
//        }


//        //'HikariProxyPreparedStatement'의 변조를 수행
//        if(className.equals("com/zaxxer/hikari/pool/HikariProxyPreparedStatement")) {
//            int cnt = 1; while (cnt-- > 0) log.info("HIKARI CALL : " + loader.toString() + " " + className);
//            return BuildClassNode(classfileBuffer, "AddLoggerPSTMT");
//        }

//        //3. Connection Test
//        if(className.equals("com/zaxxer/hikari/pool/HikariProxyConnection") ||
//            className.equals("com/zaxxer/hikari/pool/HikariProxyPreparedStatement") ||
////        if(
//                className.equals("com/zaxxer/hikari/pool/HikariProxyResultSet") ||
//                className.equals("com/zaxxer/hikari/pool/HikariProxyStatement")) {
//            return BuildClassNode(classfileBuffer, "AddLoggerConn");
//        }


        switch (className) {
            case "com/zaxxer/hikari/pool/HikariProxyConnection" -> {}
            case "com/zaxxer/hikari/pool/HikariProxyPreparedStatement" -> {}
            case "com/zaxxer/hikari/pool/HikariProxyStatement" -> {}
            case "com/zaxxer/hikari/pool/HikariProxyResultSet" -> {}
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


