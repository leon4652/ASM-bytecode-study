package com.dummy.jdbcserver.restapi.controller;

import com.dummy.jdbcserver.restapi.service.SqlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sql")
@RequiredArgsConstructor
@Slf4j
public class SQLContorller {

    private final SqlService sqlService;

    @GetMapping("/random")
    public void randomSQLInsert() {
        sqlService.randomSQLInsert();
    }
}
