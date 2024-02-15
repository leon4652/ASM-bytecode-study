package com.dummy.jdbcserver;

import com.dummy.jdbcserver.example_asm.chap3.BasicExample;
import com.dummy.jdbcserver.example_asm.chap3.StatelessTransformationsExample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JdbcServerApplication {

    public static void main(String[] args) {
        // 가장 먼저 Load되는 main 내부에 Scan할 수 있도록 객체 생성
        BasicExample basicExample = new BasicExample();
        StatelessTransformationsExample statelessTransformationsExample = new StatelessTransformationsExample();

        SpringApplication.run(JdbcServerApplication.class, args);
    }


}
