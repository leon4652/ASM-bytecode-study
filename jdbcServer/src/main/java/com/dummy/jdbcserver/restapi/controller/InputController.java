package com.dummy.jdbcserver.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/input")
public class InputController {

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
        String rawString = "this is raw code";
        return rawString;
    }
}
