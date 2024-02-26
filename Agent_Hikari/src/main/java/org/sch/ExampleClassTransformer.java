package org.sch;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ExampleClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        //System.out.println(className); //단순히 방문하는 className을 출력
        if(className.contains("jdbcserver")) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!");
            System.out.println(className);
        }
        return classfileBuffer;
    }
}
