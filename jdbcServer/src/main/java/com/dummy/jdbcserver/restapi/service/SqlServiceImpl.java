package com.dummy.jdbcserver.restapi.service;

import com.dummy.jdbcserver.restapi.dto.EntityTransfer;
import com.dummy.jdbcserver.restapi.dto.SqlDummyDto;
import com.dummy.jdbcserver.restapi.entity.SqlDummy;
import com.dummy.jdbcserver.restapi.repository.SqlDummyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

//@Service("sqlService")
@Primary
@Service
@RequiredArgsConstructor
@Slf4j
public class SqlServiceImpl implements SqlService {

    private final SqlDummyRepository sqlDummyRepository;

    @Override
    public List<SqlDummyDto> select(int no) {
        return EntityTransfer.run(sqlDummyRepository.select(no));
    }
    @Override
    public void create(String etc) {
        SqlDummy s =  new SqlDummy();
        s.setEtc(etc);
        sqlDummyRepository.save(s);
    }
    @Override
    public void update(int no, LocalDateTime localDateTime) {
        sqlDummyRepository.update(no, localDateTime);
    }
    @Override
    public void delete(String etc) {
        sqlDummyRepository.delete(etc);
    }


    @Override
    public void randomSQLInsert() {
        LocalDateTime now = LocalDateTime.now();
        log.warn("[randomSQLInsert], nowTime = {}", now);

        SqlDummy sqlDummy = new SqlDummy();
        sqlDummy.setTime(now);
        sqlDummy.setEtc("random number = " + new Random().nextInt(100));
        sqlDummyRepository.save(sqlDummy);
    }

    //PSTMT 유도
    @Override
    public void insertWithNativeQuery() {
        LocalDateTime now = LocalDateTime.now();
        sqlDummyRepository.insertWithNativeQuery("TEST", now);
    }

    @Override
    public List<SqlDummyDto> getLists() {
        List<SqlDummy> list = sqlDummyRepository.getLists(PageRequest.of(0, 3));
        return EntityTransfer.run(list);
    }

    @Override
    public List<SqlDummyDto> getLists(int pageNumber, int pageSize) {
        List<SqlDummy> list = sqlDummyRepository.getLists(PageRequest.of(pageNumber, pageSize));
        return EntityTransfer.run(list);
    }
}
