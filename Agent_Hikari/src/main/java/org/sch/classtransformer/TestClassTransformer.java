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

//        //'HikariProxyPreparedStatement'의 변조를 수행
//        if(className.equals("com/zaxxer/hikari/pool/HikariProxyPreparedStatement")) {
//            int cnt = 1; while (cnt-- > 0) log.info("HIKARI CALL : " + loader.toString() + " " + className);
//            return BuildClassNode(classfileBuffer, "AddLoggerPSTMT");
//        }

        //3. Connection Test
        if(className.equals("com/zaxxer/hikari/pool/HikariProxyConnection") ||
            className.equals("com/zaxxer/hikari/pool/HikariProxyPreparedStatement") ||
//        if(
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


