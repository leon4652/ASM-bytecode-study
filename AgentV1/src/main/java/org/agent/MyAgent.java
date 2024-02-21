package org.agent;

import lombok.extern.slf4j.Slf4j;
import org.agent.classloader.TreeAPI_MakeClassLoader;
import org.agent.init.Banner;
import org.agent.init.ConfigRead;
import org.agent.transform.BasicTransformer;
import org.agent.transform.CoreAPITransformer;
import org.agent.transform.TreeAPITransformer;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


@Slf4j
public class MyAgent {

    //런타임 전 정적 수행
    public static void premain(String agentArgs, Instrumentation instrumentation)  {

        Banner.send(agentArgs); //로그 찍기
        ConfigRead configRead = new ConfigRead(); //Config Read
        //CallThread.run(); JMX 쓰레드 생성 및 호출

        //동적 클래스로드 사용하여 새로운 클래스 빌드
        try {
            TreeAPI_MakeClassLoader makeClassClassLoader = new TreeAPI_MakeClassLoader();
            Class<?> newClass = makeClassClassLoader.defineClass("BasicClassLocalValue","testLocalClass", true, true);

            // 로드된 클래스로부터 생성자를 가져와 인스턴스 생성
            Constructor<?> constructor = newClass.getDeclaredConstructor();
            Object instance = constructor.newInstance();

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.warn("[PREMAIN]Constructor<?> constructor ERR {}", e.getMessage());
            throw new RuntimeException(e);
        }

        instrumentation.addTransformer(new CoreAPITransformer());
//        instrumentation.addTransformer(new TreeAPITransformer());
    }

    //런타임 이후 Agent 호출 시 동적 수행(Attached API 추가해야 함 .. 보류)
    public static void agentMain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("AgentMain called");
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000); // 10초 대기
                    System.out.println("Hi there from Agent every 10 seconds");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

