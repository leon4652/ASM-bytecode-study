//package org.sch.classtransformer;
//
//import java.lang.instrument.ClassFileTransformer;
//import java.security.ProtectionDomain;
//
//public class SpringClassPathTransformer implements ClassFileTransformer {
//    @Override
//    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
//        //시스템 클래스 내부 로직만 동작
//        if(loader.getName() == "AppClassLoader") {
//            System.out.println("TEST : " +className);
//        }
//        else {
//            System.out.println("=================");
//            System.out.println(loader);
//            System.out.println(loader.getClass());
//            System.out.println(loader.getName());
//        }
//
//        if (loader instanceof sun.misc.Launcher.AppClassLoader) {
//            // 시스템 클래스로더에 의해 로드된 클래스입니다.
//            // 여기에 특정 로직을 적용하세요.
//        }
//
//        return classfileBuffer;
//    }
//
//}
