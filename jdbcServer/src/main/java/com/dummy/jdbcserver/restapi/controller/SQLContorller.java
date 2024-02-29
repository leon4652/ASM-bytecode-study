package com.dummy.jdbcserver.restapi.controller;

import com.dummy.jdbcserver.restapi.dto.SqlDummyDto;
import com.dummy.jdbcserver.restapi.service.SqlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


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

    @GetMapping("/t2")
    public void checkPstmtUseInsert() {
        sqlService.insertWithNativeQuery();
    }

    @GetMapping("/t3")
    public List<SqlDummyDto> getList() {
        return sqlService.getLists();
    }

    @GetMapping("/t4")
    public List<SqlDummyDto> getList(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return sqlService.getLists(pageNumber, pageSize) ;
    }
}
