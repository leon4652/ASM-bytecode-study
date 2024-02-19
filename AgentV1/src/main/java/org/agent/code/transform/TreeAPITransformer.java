package org.agent.code.transform;

import lombok.extern.slf4j.Slf4j;
import org.agent.MyAgent;
import org.agent.code.asm.AsmCodeFactory;
import org.agent.code.asm.AsmCodeFactoryTreeAPI;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;


@Slf4j
public class TreeAPITransformer implements ClassFileTransformer {


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {

        if(!MyAgent.globalFlag) {
            MyAgent.globalFlag = true;

            //input의 classfileBuffer <- 아마도 원래 DebuggerAgent의 바이트코드일 것이다.

            return AsmCodeFactoryTreeAPI.doMethod(classfileBuffer, true); //테스트 코드 실행
            //실행 결과 원래 바이트코드가 아니라, 새로운 값이 만들어졌다.
        }

        return classfileBuffer;
    }
}