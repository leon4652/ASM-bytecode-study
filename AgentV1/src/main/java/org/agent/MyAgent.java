package org.agent;

import org.agent.util.init.Banner;

import java.lang.instrument.Instrumentation;
import java.time.LocalDateTime;

public class MyAgent {

    /**
     *  premain 메서드는 Instrumentation 객체를 매개변수로 받는다.
     * 이 객체는 JVM이 제공하는 인스트루멘테이션 기능에 접근할 수 있게 해준다.
     */
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        Banner.send(agentArgs); //로그 찍기

        /**
         *  Instrumentation 객체의 addTransformer를 통해 인터페이스 구현 클래스를 '등록'한다.
         *  - 1. premain은 유일하게 단 한번 실행된다. 이 아래 구문을 통해 SimpleClassTransformer를 JVM의 Instrumentation에 등록했다.
         *  - 2. 이 인터페이스의 구현체(transform)은 JVM이 존재하는 클래스를 로드할 때마다 호출되며, 이 시점에서 바이트코드를 조사하고 변경할 수 있다.
         */
        instrumentation.addTransformer(new SimpleClassTransformer());
    }
}
