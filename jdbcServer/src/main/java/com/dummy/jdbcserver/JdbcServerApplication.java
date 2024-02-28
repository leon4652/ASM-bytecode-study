package com.dummy.jdbcserver;

import com.dummy.jdbcserver.example_asm.chap3.AddTimerAdapter;
import com.dummy.jdbcserver.example_asm.chap3.BasicExample;
import com.dummy.jdbcserver.example_asm.chap3.GetFSetF;
import com.dummy.jdbcserver.example_asm.chap3.StatelessTransformationsExample;
import com.dummy.jdbcserver.jdbc_example.GetPstmt;
import com.zaxxer.hikari.pool.HikariProxyPreparedStatement;
import com.zaxxer.hikari.pool.ProxyConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class JdbcServerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(JdbcServerApplication.class, args);
    }
}
