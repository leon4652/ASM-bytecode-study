package com.dummy.jdbcserver.schedular;

import com.dummy.jdbcserver.restapi.entity.SqlDummy;
import com.dummy.jdbcserver.restapi.repository.SqlDummyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class AutoSaveSQL {

    private final SqlDummyRepository sqlDummyRepository;

    @Scheduled(fixedRate = 60000)
    public void autoLog() {
        LocalDateTime now = LocalDateTime.now();
        log.warn("nowTime = {}", now);

        SqlDummy sqlDummy = new SqlDummy();
        sqlDummy.setTime(now);
        sqlDummy.setEtc("random number = " + new Random().nextInt(100));
        sqlDummyRepository.save(sqlDummy);
    }

}
