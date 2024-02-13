package org.agent.util.transform;

import lombok.extern.slf4j.Slf4j;
import org.agent.util.asm.AsmCodeFactory;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;


@Slf4j
public class Chap03Transformer implements ClassFileTransformer {


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        String containsName = "Chap03";


        if (className.contains(containsName)) {
            log.warn("[TRANSFORM] Find ClassName : {}", containsName);
            AsmCodeFactory.testCode = "SetF";
            return AsmCodeFactory.doMethod(classfileBuffer, true); //테스트 코드 실행
        }

        return classfileBuffer;
    }
}