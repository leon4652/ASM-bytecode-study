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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/crud")
@RequiredArgsConstructor
@Slf4j
public class SQLContorller {

    private final SqlService sqlService;
    @GetMapping("/create")
    public void create() {
        sqlService.create("ABCD");
    }

    @GetMapping("/select")
    public void select() {
        List<SqlDummyDto> list = sqlService.select(1);
        for(SqlDummyDto s : list) System.out.println(s.no() + " " + s.etc());
    }

    @GetMapping("/delete")
    public void delete() {
        String tempStr = "tempString";
        sqlService.create(tempStr);
        sqlService.delete(tempStr);
    }

     @GetMapping("/update")
    public void update() {
        sqlService.update(1, LocalDateTime.now());
    }



    @GetMapping("/t4")
    public List<SqlDummyDto> getList(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return sqlService.getLists(pageNumber, pageSize) ;
    }
}
