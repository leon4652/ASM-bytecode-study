package com.dummy.jdbcserver.restapi.controller;

import com.dummy.jdbcserver.jdbc_example.JdbcService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import com.zaxxer.hikari.pool.HikariProxyPreparedStatement;
@RestController
@RequiredArgsConstructor
@RequestMapping("/input")
public class InputController {

    private final JdbcService jdbcService;

    @GetMapping("/")
    public String test() {
        return "hello";
    }

    @GetMapping("/dummy")
    public String dummy() {
        return "hello, dummy";
    }

    @GetMapping("/dummy-insert")
    public String insertByteCode() {
        String rawString = "this is raw code"; //기존 변수
        return rawString;
    }

    @GetMapping("/jdbc")
    public List<Map<String, Object>> executeQueryWithJdbcTemplate() {
        System.out.println("START");
        return jdbcService.simpleQuery();
    }
}
