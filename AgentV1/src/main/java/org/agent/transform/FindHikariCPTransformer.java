package org.agent.transform;


import lombok.extern.slf4j.Slf4j;
import org.agent.util.CodePrinter;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

@Slf4j
public class FindHikariCPTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {

        //com/zaxxer/hikari/pool/ProxyPreparedStatement
        String hikariAddr = "com/zaxxer/hikari/pool/ProxyPreparedStatement"; //스캔할 클래스명

        if (className.equals(hikariAddr)) {
            log.warn("[TRANSFORM] Find ClassName : {}", hikariAddr);
            CodePrinter.printClass(classfileBuffer, "PRINT", true);
            return classfileBuffer;
        } else {
            return classfileBuffer;
        }

    }
}
