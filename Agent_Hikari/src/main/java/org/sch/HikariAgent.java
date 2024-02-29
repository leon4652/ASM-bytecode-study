package org.sch;

import lombok.extern.slf4j.Slf4j;
import org.sch.classtransformer.TestClassTransformer;
import org.sch.init.Banner;

import java.lang.instrument.Instrumentation;

@Slf4j
public class HikariAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        Banner.send(agentArgs); //banner
        instrumentation.addTransformer(new TestClassTransformer());
    }
}
