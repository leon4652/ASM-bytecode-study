package com.dummy.jdbcserver.restapi.repository;

import com.dummy.jdbcserver.restapi.entity.SqlDummy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlDummyRepository extends JpaRepository<SqlDummy, Long> {

}
