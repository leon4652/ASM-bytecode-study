package com.dummy.jdbcserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class JdbcServerApplication {

    //일반적으로 spring에서 시스템 클래스로더가 가장 먼저 읽는 Class는 이것이다
    //정확히 말하자면 -cp main이자, entry point를 가장 먼저 읽는다.
    public static void main(String[] args) throws Exception {

        //이 클래스(메서드)에서 부트에 필요한 Tomcat 빌드, Context Scan 등이 이루어진다.
        //그리고 이 과정에서 사용자가 작성한 코드, 빌드에 필요한 Class들을 각각 로드해서 읽어낼 것이다.
        SpringApplication.run(JdbcServerApplication.class, args);
    }
}
