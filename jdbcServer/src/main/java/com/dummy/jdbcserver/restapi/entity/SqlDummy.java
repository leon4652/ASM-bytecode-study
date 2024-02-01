package com.dummy.jdbcserver.restapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class SqlDummy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String etc;
    private LocalDateTime sendTime;

    public void setTime(LocalDateTime now) {
        this.sendTime = now;
    }
    public void setEtc(String input) {
        this.etc = input;
    }
}
