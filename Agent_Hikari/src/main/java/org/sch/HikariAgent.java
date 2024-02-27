package org.sch;

import lombok.extern.slf4j.Slf4j;
import org.sch.classtransformer.TestClassTransformer;
import org.sch.init.Banner;

import java.lang.instrument.Instrumentation;

@Slf4j
public class HikariAgent {
    public static boolean firstSystemClassLoad = true; //첫번째 시스템 로더 호출인지 확인
    public static boolean successBuild = false;

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        instrumentation.addTransformer(new TestClassTransformer());
        if(successBuild) Banner.send(agentArgs); //문제 없을 시 로그 찍기
    }
}
