package org.sch.classtransformer;

import lombok.extern.slf4j.Slf4j;
import org.sch.HikariAgent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

@Slf4j
public class TestClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if(HikariAgent.firstSystemClassLoad && loader.getName().equals("app")) { //spring Main, 시스템 클래스로더가 가장 먼저 접근하는 클래스
            System.out.println("첫번째 메서드 : " + className);
            HikariAgent.firstSystemClassLoad = false;
        }

        return classfileBuffer;
    }
}
