package org.agent.transform;

import lombok.extern.slf4j.Slf4j;
import org.agent.asm_core_api.AsmCodeFactory;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;


@Slf4j
public class CoreAPITransformer implements ClassFileTransformer {


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        String containsName = "AddTimerAdapter"; //스캔할 Class네임 일부 입력
        String testCode = "AddTimerAdapter"; //testcode package name

        if (className.contains(containsName)) {
            log.warn("[CoreAPITransformer] Find ClassName : {}", containsName);
            return AsmCodeFactory.doMethod(classfileBuffer, testCode,true); //테스트 코드 실행
        }

        return classfileBuffer;
    }
}