package com.dummy.jdbcserver.restapi.repository;

import com.dummy.jdbcserver.restapi.dto.SqlDummyDto;
import com.dummy.jdbcserver.restapi.entity.SqlDummy;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SqlDummyRepository extends JpaRepository<SqlDummy, Long> {

    //PSTMT 유도
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO sql_dummy (etc, send_time) VALUES (?1, ?2)", nativeQuery = true)
    void insertWithNativeQuery(String etc, LocalDateTime sendTime);

    @Transactional
    @Query("SELECT s FROM SqlDummy s")
    List<SqlDummy> getLists(Pageable pageable);
}
