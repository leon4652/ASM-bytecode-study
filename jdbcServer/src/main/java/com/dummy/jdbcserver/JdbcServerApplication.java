package com.dummy.jdbcserver;

import com.dummy.jdbcserver.example_asm.chap3.AddTimerAdapter;
import com.dummy.jdbcserver.example_asm.chap3.BasicExample;
import com.dummy.jdbcserver.example_asm.chap3.GetFSetF;
import com.dummy.jdbcserver.example_asm.chap3.StatelessTransformationsExample;
import com.dummy.jdbcserver.example_asm.testClass;
import com.zaxxer.hikari.pool.HikariProxyPreparedStatement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JdbcServerApplication {

    public static void main(String[] args) throws Exception {
        HikariProxyPreparedStatement hikariProxyPreparedStatement; //1. 여기서 클래스를 호출하면, 시스템 클래스로더는 먼저 이 클래스를 호출한다(B)
        SpringApplication.run(JdbcServerApplication.class, args); //2. 여기에서 tomcat이 빌드되니, 1번에서 먼저 변조하고자 하는 클래스를 호출한다.
    }
}
