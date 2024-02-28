//package com.dummy.jdbcserver.proxytest;
//import javax.sql.DataSource;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.sql.Connection;
//
///**
// * javax.sql.DataSource 인터페이스를 기반
// * DataSource의 getConnection() 메서드 호출을 가로채서,
// * 반환되는 Connection 객체에 로깅 등의 추가 작업을 수행할 수 있도록 하는 InvocationHandler 구현체
// */
//public class DataSourceProxyHandler implements InvocationHandler {
//    private final DataSource targetDataSource;
//
//    public DataSourceProxyHandler(DataSource targetDataSource) {
//        this.targetDataSource = targetDataSource;
//    }
//
//    /**
//     * proxy 인스턴스, 호출된 메서드의 Method 인스턴스, 메서드 전달 인수
//     1. getConnection 메서드가 호출될 때, 실제 DataSource 객체의 해당 메서드를 호출하여 Connection 객체를 가져온다.
//     2. 이 Connection 객체에 대한 새로운 프록시를 생성하여 반환한다.
//     3. 프록시는 Connection 인터페이스의 모든 메서드 호출을 가로채고, prepareStatement 같은 메서드가 호출될 때 SQL 쿼리를 로깅.
//     */
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        if ("getConnection".equals(method.getName())) {
//            final Connection connection = (Connection) method.invoke(targetDataSource, args);
//            // Connection에 대한 프록시를 반환
//            return Proxy.newProxyInstance(
//                    Connection.class.getClassLoader(),
//                    new Class<?>[]{Connection.class},
//                    (proxyConn, methodConn, argsConn) -> {
//                        //prepareStatement 메서드 호출 시 로깅
//                        if ("prepareStatement".equals(methodConn.getName())) {
//                            System.out.println("SQL proxy 로그: " + argsConn[0]);
//                        }
//                        return methodConn.invoke(connection, argsConn);
//                    });
//        }
//        return method.invoke(targetDataSource, args);
//    }
//
//    //DataSource 인스턴스를 받아서, 이를 대리하는 프록시 DataSource 인스턴스를 생성
//    public static DataSource createProxy(DataSource realDataSource) {
//        return (DataSource) Proxy.newProxyInstance(
//                DataSource.class.getClassLoader(),
//                new Class<?>[]{DataSource.class},
//                new DataSourceProxyHandler(realDataSource)
//        );
//    }
//}
