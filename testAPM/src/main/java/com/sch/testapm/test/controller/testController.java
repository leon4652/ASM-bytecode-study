package com.sch.testapm.test.controller;

import com.sch.testapm.reference.chap2.ClassPrinter;
import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;


@RestController
@RequestMapping("/chap-02")
@Slf4j
public class testController {


    @GetMapping("/print-test")
    public void printTest() throws IOException {
        ClassPrinter cp = new ClassPrinter();
        ClassReader cr = new ClassReader("java.lang.Runnable");

        // ClassReader로 java.lang.Runnable 클래스를 읽고, ClassPrinter를 사용하여 정보를 출력한다.
        cr.accept(cp, 0);
    }



    @GetMapping("/write-test")
    public void writeTest() {
        ClassWriter cw = new ClassWriter(0); //flag
        //org.objectweb.asm.Opcodes.*; 내부에 정의된 코드

        /*
        visit 하기 원하는 클래스 정보를 작성. 인자의 경우 아래와 같다.
        - java version
        - 접근 제어자 (클래스가 인터페이스이며, public이고 abstract(인스턴스화할 수 없기 때문에)임을 명시)
        - 상세 주소
        - 제네릭(인터페이스 타입 변수가 없기에 null)
        - 상속받은 원 인터페이스인 Mesurable의 내부 이름 배열
         */
        cw.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "com/sch/testapm/reference/chap2/Comparable", null, "java/lang/Object",
                new String[] { "com/sch/testapm/reference/chap2/Mesurable" });


        /*
        visitField = 클래스 내부의 변수(Field)에 접근한다. 인자는 아래와 같다.
        - Java 수정자에 해당하는 플래그 집합 : 해당 필드 변수가 public final static임을 의미
        - 필드 이름
        - 필드 타입(여기서는 primitive Int (I)로 인자를 집어넣었음)
        - 필드의 제네릭 : 여기서는 쓰지 않았으므로, Null
        - 필드의 상수 값(실제로 반영된 값)
         */
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, -1).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, 0).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, 1).visitEnd();


        /*
        visitMethod = 클래스 내부의 메서드(Method)에 접근한다. 인자는 아래와 같다.
        - Java 수정자에 해당하는 플래그 집합
        - 메서드 이름
        - 메서드 설명자(메서드 입출력을 바이트코드 형식으로 변환한 값 - chap2에 설명 있음.)
        - 제네릭
        - Exception (여기서는 예외 없음)
         */
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();

        /*
        cw 클래스 방문 완료 알림
         */
        cw.visitEnd();

        byte[] b = cw.toByteArray(); //byte 배열로 생성된 클래스를 저장

        //생성된 클래스 사용 -> 혹은 생성된 클래스(b)를 원본을 기반하여 동적으로 로드
        Class c = new MyClassLoader().defineClass("com.sch.testapm.reference.chap2.Comparable", b);

    }

    /*
    생성된 클래스 사용(Using generated classes)
    만들어진 바이트 배열은 향후 사용을 위해 Comparable.class 파일에 저장될 수 있다.
    혹은 아래와 같이 ClassLoader를 통해 동적으로 로드될 수 있다.
     */
    public static class MyClassLoader extends ClassLoader {
        public Class defineClass(String name, byte[] b){
            return defineClass(name, b, 0, b.length);
        }
    }

}
