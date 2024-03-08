package org.sch;

import org.sch.classtransformer.HikariTransformer;
//import org.sch.classtransformer.SpringClassPathTransformer;
import org.sch.init.Banner;
import org.sch.util.CodePrinter;

import java.lang.instrument.Instrumentation;

public class HikariAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        Banner.send(agentArgs); //banner
        CodePrinter.deleteFiles(true); //파일 초기화


        //이 Transformer는 나중에 한 가지로 결합해야 함. (성능 이슈)
        //현재는 개발 단계이므로, 각 Transformer별로 분리하였음.
        instrumentation.addTransformer(new HikariTransformer()); //HikariCP 기능
//        instrumentation.addTransformer(new SpringClassPathTransformer()); //Spring Class 변조하는 기능

    }
}
