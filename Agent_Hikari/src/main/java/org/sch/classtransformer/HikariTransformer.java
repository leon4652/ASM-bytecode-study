package org.sch.classtransformer;

import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.sch.asm_tree_api.ClassNodeTransformationStrategy;
import org.sch.util.CodePrinter;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * This class implements the ClassFileTransformer interface to transform class files.
 */
@Slf4j
public class HikariTransformer implements ClassFileTransformer {
    byte[] modifiedBytecodeInLocalMethod; //지역변수에서 수정된 바이트코드
//    public static boolean isModifiedMain = false; //main Class 변조 유무
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {


        if (className.contains("com/zaxxer/hikari/pool/")) {
//            if(modifyHikariCP_addLog(classfileBuffer, className)) return modifiedBytecodeInLocalMethod;

            if(className.equals("com/zaxxer/hikari/pool/HikariProxyPreparedStatement")) {
                return modifyByteCode(classfileBuffer, "AddLoggerMethodNameAndInputParam", true, false);
            }
            if(className.contains("ProxyPreparedStatement")) {
                System.out.println("1차 TRIG : " + className);
                return modifyByteCode(classfileBuffer, "AddLoggerProxyPreparedStatement", true, false);
            }

            log.warn("[PREMAIN]==========> CLASS : {} ", className);
            return modifyByteCode(classfileBuffer, "AddLoggerAllClass", true, false);
        }



        return classfileBuffer;
    }

    private boolean modifyHikariCP_addLog(byte[] classfileBuffer, String className) {
        switch (className) {
            case "com/zaxxer/hikari/pool/HikariProxyConnection" -> {
                log.warn("[CONN 호출]");
                modifiedBytecodeInLocalMethod = modifyByteCode(classfileBuffer, "AddLoggerAllClass", true, false);
                return true;
            }
            case "com/zaxxer/hikari/pool/HikariProxyPreparedStatement" -> {
                log.warn("[PSTMT 호출]");
                modifiedBytecodeInLocalMethod = modifyByteCode(classfileBuffer, "AddLoggerPSTMT", true, false);
                return true;
            }
            case "com/zaxxer/hikari/pool/HikariProxyStatement" -> {
                log.warn("[STMT 호출]");
                modifiedBytecodeInLocalMethod = modifyByteCode(classfileBuffer, "AddLoggerAllClass", true, false);
                return true;
            }
            case "com/zaxxer/hikari/pool/HikariProxyResultSet" -> {
                log.warn("[RS 호출]");
                modifiedBytecodeInLocalMethod = modifyByteCode(classfileBuffer, "AddLoggerAllClass", true, false);
                return true;
            }
        }
        return false;
    }

    /**
     * 'AppClassLaoder'사용 && 'SpringApplication이 존재하는 Main 메서드 탐색'하여 True시 전역변수 코드 변조
     */
//    private boolean modifyMainClass_addClassForNameCode(byte[] classfileBuffer, String ClassLoaderName) {
//        if(ClassLoaderName.contains("AppClassLoader")) { //Application Class Loader인지 여부
//            //Class에 @SpringBootApplication가 있는지? (SpringApplication에 존재)
//            if (CheckSpringApplicationAnnotation.check(classfileBuffer)) {
//                log.info("[CheckSpringApplicationAnnotation]@SpringBootApplication 확인");
//                isModifiedMain = true;
//                modifiedBytecodeInLocalMethod = modifyByteCode(classfileBuffer, "AddAdditionalObjectToMain", true, false);
//                return true;
//            }
//        }
//        return false;
//    }


    /**
     * 이 바이트코드(.class)를 특정 전략에 맞게(code) 조작 후 반환
     *
     * @param classfileBuffer
     * @param code
     * @param setPrint
     * @param deleteLastFile
     * @return
     */
    private byte[] modifyByteCode(byte[] classfileBuffer, String code, boolean setPrint, boolean deleteLastFile) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);
        ClassNodeTransformationStrategy.select(cn, code); //[HERE] Transfer Logic Apply
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cn.accept(cw);
        byte[] result = cw.toByteArray(); //result
        if (setPrint) CodePrinter.printClass(result, cn.name, deleteLastFile); //Print class
        return result;
    }
}


