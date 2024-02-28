package com.dummy.jdbcserver.restapi.dto;

import com.dummy.jdbcserver.restapi.entity.SqlDummy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity -> DTO
 */
public class EntityTransfer {

    public static List<SqlDummyDto> run(List<SqlDummy> legacyList) {
        List<SqlDummyDto> newlist = new ArrayList<>();
        return legacyList.stream()
                .map(EntityTransfer::mapToDto)
                .collect(Collectors.toList());
    }

    private static SqlDummyDto mapToDto(SqlDummy entity) {
        return new SqlDummyDto(entity.getNo(), entity.getEtc(), entity.getSendTime());
    }
}
