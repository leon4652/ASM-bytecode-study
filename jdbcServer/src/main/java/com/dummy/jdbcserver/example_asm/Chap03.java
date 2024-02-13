package com.dummy.jdbcserver.example_asm;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * ASM Docs 3장 예제를 위한 클래스 파일
 */
@Configuration
public class Chap03 {
    //It's empty, but add in bytecode file
    
    static int legacyInt; //원래 있던 변수
    
    public void legacyMethod(int a, int b) {
        //원래 있던 메서드 1
    }
}
