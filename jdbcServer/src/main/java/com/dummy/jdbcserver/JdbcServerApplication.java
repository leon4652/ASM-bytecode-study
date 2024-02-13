package com.dummy.jdbcserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JdbcServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(JdbcServerApplication.class, args);
    }


}
