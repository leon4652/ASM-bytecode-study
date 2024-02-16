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
        String containsName = "StatelessTransformationsExample"; //스캔할 Class네임 일부 입력

        //GetFSetF
        if (className.contains(containsName)) {
            log.warn("[TRANSFORM] Find ClassName : {}", containsName);
            AsmCodeFactory.testCode = containsName; //이 Agent 파일에서 실행할 Class testcode 이름 입력(일반적으로 containsName과 동일함)
            return AsmCodeFactory.doMethod(classfileBuffer, true); //테스트 코드 실행
        }

        return classfileBuffer;
    }
}