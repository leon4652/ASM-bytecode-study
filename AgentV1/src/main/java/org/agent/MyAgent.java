package org.agent;

import org.agent.util.init.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.instrument.Instrumentation;


public class MyAgent {

    /**
     *  premain 메서드는 Instrumentation 객체를 매개변수로 받는다.
     * 이 객체는 JVM이 제공하는 인스트루멘테이션 기능에 접근할 수 있게 해준다.
     */
    public static void premain(String agentArgs, Instrumentation instrumentation)  {

        Banner.send(agentArgs); //로그 찍기
        ConfigRead configRead = new ConfigRead(); //Config Read
        //CallThread.run(); JMX 쓰레드 생성 및 호출


        //Instrumentation 객체의 addTransformer를 통해 인터페이스 구현 클래스를 '등록'한다.
        //premain은 유일하게 단 한번 실행. addTransformer를 통해 Transformer를 JVM의 Instrumentation에 등록했다.
        //이 인터페이스의 구현체(transform)은 JVM이 존재하는 클래스를 로드할 때마다 호출되며, 이 시점에서 바이트코드를 조사하고 변경할 수 있다.
        instrumentation.addTransformer(new MyClassTransformer());

    }
}
