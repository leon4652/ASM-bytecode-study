package org.sch;

import lombok.extern.slf4j.Slf4j;
import org.sch.classtransformer.HikariTransformer;
import org.sch.init.Banner;
import org.sch.util.CodePrinter;

import java.lang.instrument.Instrumentation;

@Slf4j
public class HikariAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        Banner.send(agentArgs); //banner
        CodePrinter.deleteFiles(true); //파일 초기화
        instrumentation.addTransformer(new HikariTransformer());
    }
}
