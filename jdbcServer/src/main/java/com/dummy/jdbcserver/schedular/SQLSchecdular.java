package com.dummy.jdbcserver.schedular;

import com.dummy.jdbcserver.restapi.entity.SqlDummy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class SQLSchecdular {

    @Scheduled(fixedRate = 5000)
    public void autoLog() {
        String apiUrl = "http://localhost:8080/sql/random";

        WebClient webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        webClient.get()
                .retrieve()
                .bodyToMono(SqlDummy.class) // 응답을 SqlDummy 객체로 변환
                .subscribe(response -> {
                    log.info("Received response: {}", response);
                });
    }

}
