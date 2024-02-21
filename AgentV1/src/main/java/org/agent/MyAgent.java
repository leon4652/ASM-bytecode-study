package org.agent;

import lombok.extern.slf4j.Slf4j;
import org.agent.classloader.MakeClassClassLoader;
import org.agent.init.Banner;
import org.agent.init.ConfigRead;
import org.agent.transform.BasicTransformer;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


@Slf4j
public class MyAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation)  {

        Banner.send(agentArgs); //로그 찍기
        ConfigRead configRead = new ConfigRead(); //Config Read
        //CallThread.run(); JMX 쓰레드 생성 및 호출

        //동적 클래스로드 사용하여 새로운 클래스 빌드
        try {
            MakeClassClassLoader makeClassClassLoader = new MakeClassClassLoader();
            Class<?> newClass = makeClassClassLoader.defineClass("BasicClass","test1", true, true);

            // 로드된 클래스로부터 생성자를 가져와 인스턴스 생성
            Constructor<?> constructor = newClass.getDeclaredConstructor();
            Object instance = constructor.newInstance();

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            log.warn("[PREMAIN]Constructor<?> constructor ERR {}", e.getMessage());
            throw new RuntimeException(e);
        }

        instrumentation.addTransformer(new BasicTransformer());
    }
}

