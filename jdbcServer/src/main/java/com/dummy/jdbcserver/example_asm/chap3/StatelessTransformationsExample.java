package com.dummy.jdbcserver.example_asm.chap3;

import org.springframework.context.annotation.Configuration;

/**
 * 3.2.4. Stateless transformations (상태 없는 변환) 예제.
 이렇게 바꾸고 싶다!

public class StatelessTransformationsExample {
    public static long timer;
    public void m() throws Exception {
        timer -= System.currentTimeMillis();
        Thread.sleep(100);
        timer += System.currentTimeMillis();
    }
}

 */
public class StatelessTransformationsExample {
    public void m() throws Exception {
        Thread.sleep(100);
    }
}


