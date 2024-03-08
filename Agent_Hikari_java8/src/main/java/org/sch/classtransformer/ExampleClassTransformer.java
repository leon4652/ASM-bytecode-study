//package org.sch.classtransformer;
//
//import org.objectweb.asm.ClassReader;
//import org.objectweb.asm.ClassWriter;
//import org.objectweb.asm.tree.ClassNode;
//import org.sch.asm_tree_api.code.hikari.AddLogger;
//import org.sch.util.CodePrinter;
//
//import java.lang.instrument.ClassFileTransformer;
//import java.lang.instrument.IllegalClassFormatException;
//import java.security.ProtectionDomain;
//
//public class ExampleClassTransformer implements ClassFileTransformer {
//    @Override
//    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//        String target = "com/zaxxer/hikari/pool/HikariProxyPreparedStatement";
////        String target = "com/dummy/jdbcserver/example_asm/testClass";
//
//        if(className.equals(target)) {
//            ClassReader classReader = new ClassReader(classfileBuffer);
//            ClassNode cn = new ClassNode();
//            classReader.accept(cn, 0);
//
//            try {
//                AddLogger.apply(cn);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
//            cn.accept(cw);
//            byte[] modified = cw.toByteArray();
//            CodePrinter.printClass(modified, "test", false);
//        }
//        return classfileBuffer;
//    }
//}
