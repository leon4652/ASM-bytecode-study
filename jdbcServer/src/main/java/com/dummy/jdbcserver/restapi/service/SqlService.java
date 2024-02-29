package com.dummy.jdbcserver.restapi.service;

import com.dummy.jdbcserver.restapi.dto.SqlDummyDto;

import java.util.List;

public interface SqlService {
    void randomSQLInsert();

    void insertWithNativeQuery();

    List<SqlDummyDto> getLists();

    public List<SqlDummyDto> getLists(int pageNumber, int pageSize);
}
