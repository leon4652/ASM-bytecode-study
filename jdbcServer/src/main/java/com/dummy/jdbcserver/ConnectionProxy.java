//package com.dummy.jdbcserver;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class ConnectionProxy implements Connection {
//    private final Connection connection;
//
//    public ConnectionProxy(Connection connection) {
//        this.connection = connection;
//    }
//
//    @Override
//    public PreparedStatement prepareStatement(String sql) throws SQLException {
//        System.out.println("Executing SQL: " + sql); // SQL 로그 출력
//        return (PreparedStatement) new PreparedStatementProxy(connection.prepareStatement(sql), sql);
//    }
//
//}
