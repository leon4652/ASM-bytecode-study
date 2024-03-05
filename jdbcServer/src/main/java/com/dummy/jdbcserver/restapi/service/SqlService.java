package com.dummy.jdbcserver.restapi.service;

import com.dummy.jdbcserver.restapi.dto.SqlDummyDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SqlService {
    List<SqlDummyDto> select(int no);

    void create(String etc);
    void update(int no, LocalDateTime localDateTime);
    void delete(String etc);


    void randomSQLInsert();

    void insertWithNativeQuery();

    List<SqlDummyDto> getLists();

    public List<SqlDummyDto> getLists(int pageNumber, int pageSize);
}
