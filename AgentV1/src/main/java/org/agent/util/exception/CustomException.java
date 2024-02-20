//package org.agent.util.exception;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//
//@Getter
//public class CustomException extends RuntimeException {
//    private final String name;
//    private final int code;
//    private final String message;
//
//    public CustomException(ExceptionType exceptionType) {
//        super(exceptionType.getMessage()); // RuntimeException의 메시지로 예외 메시지 설정
//        this.name = exceptionType.getName();
//        this.code = exceptionType.getCode();
//        this.message = exceptionType.getMessage();
//    }
//
//    @Override
//    public String toString() {
//        return "CustomException {" + "\n" +
//                "\tname=" + name + "\n" +
//                "\tcode=" + code + "\n" +
//                "\tmessage='" + message + '\n' +
//                '}';
//    }
//
//    @Override
//    public void printStackTrace() {
//        System.err.println(this); // toString을 사용하여 예외 정보 출력
//        super.printStackTrace();
//    }
//}