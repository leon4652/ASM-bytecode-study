package com.sch.testapm.test.entity;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

//https://kangwoo.tistory.com/84
public class MyAgent implements ClassFileTransformer {

    public static void premain(String agentArgument, Instrumentation instrumentation) throws Exception {
        System.out.println("premain");
        instrumentation.addTransformer(new MyAgent());
    }

    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("transform : " + className);
        return classfileBuffer;
    }

}
