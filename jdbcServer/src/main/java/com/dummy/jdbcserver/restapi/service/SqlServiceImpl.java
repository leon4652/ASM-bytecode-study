package com.dummy.jdbcserver.restapi.service;

import com.dummy.jdbcserver.restapi.entity.SqlDummy;
import com.dummy.jdbcserver.restapi.repository.SqlDummyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

//@Service("sqlService")
@Primary
@Service
@RequiredArgsConstructor
@Slf4j
public class SqlServiceImpl implements SqlService {

    private final SqlDummyRepository sqlDummyRepository;

    @Override
    public void randomSQLInsert() {
        LocalDateTime now = LocalDateTime.now();
        log.warn("SQL insert, nowTime = {}", now);

        SqlDummy sqlDummy = new SqlDummy();
        sqlDummy.setTime(now);
        sqlDummy.setEtc("random number = " + new Random().nextInt(100));
        sqlDummyRepository.save(sqlDummy);
    }
}
