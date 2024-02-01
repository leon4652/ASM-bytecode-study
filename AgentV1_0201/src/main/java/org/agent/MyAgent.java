package org.agent;

import java.lang.instrument.Instrumentation;
import java.time.LocalDateTime;

public class MyAgent {
    /*
    JVM이 시작될때 premain 메소드가 실행되고, Transformar 클래스를 클래스 변환자로 등록한다.
    클래스 변환자는 ClassFileTransformer 인터페이스를 구현한 객체로, 클래스의 바이트 코드를 수정할 수 있다.
    */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("[Custom MyAgent Run]");
        System.out.println("nowTime : " + LocalDateTime.now() + " my Agent has been invoked with args: " + agentArgs);
        inst.addTransformer(new SimpleClassTransformer());
    }
}
