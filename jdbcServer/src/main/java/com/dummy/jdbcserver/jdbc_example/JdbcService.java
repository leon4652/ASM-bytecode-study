package com.dummy.jdbcserver.jdbc_example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JdbcService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> simpleQuery() {
        String sql = "SELECT * FROM sql_dummy LIMIT 1"; // 쿼리는 상황에 맞게 변경
        return jdbcTemplate.queryForList(sql);
    }
}
