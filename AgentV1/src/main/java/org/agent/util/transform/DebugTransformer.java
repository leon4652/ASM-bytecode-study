package org.agent.util.transform;

import lombok.extern.slf4j.Slf4j;
import org.agent.util.asm.AsmCodeFactory;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * Springboot Class 누락 버그 테스트용
 */
@Slf4j
public class DebugTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if(className.contains("dummy")) {
            long currentTime = System.currentTimeMillis();
            System.out.println("\n");
            System.out.println("Class Name: " + className.replace("/", "."));
            System.out.println("Timestamp: " + currentTime);

            if (loader != null) {
                System.out.println("Class Loader: " + loader.getClass().getName());
                java.net.URL[] urls = ((java.net.URLClassLoader)loader).getURLs();
                for (java.net.URL url : urls) {
                    System.out.println("Classpath: " + url.getFile());
                }
            } else {
                System.out.println("Class Loader: Bootstrap Loader");
            }

            return classfileBuffer; // 변환하지 않은 경우 null을 반환
        }

        return classfileBuffer;
    }
}
