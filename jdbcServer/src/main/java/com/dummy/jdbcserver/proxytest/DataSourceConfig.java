//package com.dummy.jdbcserver.proxytest;
//
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//
//    private final DataSourceProperties properties;
//
//    public DataSourceConfig(DataSourceProperties properties) {
//        this.properties = properties;
//    }
//
//    @Bean
//    @Primary
//    public DataSource dataSource() {
//        // DataSourceProperties를 사용하여 실제 DataSource 구성 (순환 참조 이슈)
//        DataSource realDataSource = DataSourceBuilder.create()
//                .driverClassName(properties.getDriverClassName())
//                .url(properties.getUrl())
//                .username(properties.getUsername())
//                .password(properties.getPassword())
//                .build();
//
//        // 프록시 DataSource 생성 및 반환
//        return DataSourceProxyHandler.createProxy(realDataSource);
//    }
//}
