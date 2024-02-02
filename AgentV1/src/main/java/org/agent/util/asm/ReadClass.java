package org.agent.util.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;

import static org.objectweb.asm.Opcodes.ASM9;


/**
 * ASM Library 사용.
 * 이 클래스의 Byte코드를 읽는다.
 */
public class ReadClass {

    //classfileBuffer는 ClassFileTransformer 구현체의 입력 parameter로 받은 byte[] classfileBuffer이다.
    public static void read(byte[] classfileBuffer) {

        //ClassReader는 주어진 바이트 배열(classfileBuffer)에서 클래스 정보를 읽기 위한 객체
        ClassReader classReader = new ClassReader(classfileBuffer);

        //읽은 클래스 정보에 기반하여 새로운 바이트코드를 생성하거나 기존 바이트코드를 수정하기 위한 객체
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);

        /*
        클래스의 구조(예: 클래스 자체, 메서드, 필드 등)를 순회하면서 필요한 작업을 수행할 수 있는 추상 클래스
        실사용시에는 visitMethod 등을 오버라이드해서 사용해야 한다.
        classWriter가 들어간 것 역시 이벤트 발생 시 작성해야 하기 때문.
        */
        ClassVisitor classVisitor = new ClassVisitor(ASM9, classWriter) {};

        //실제 visitor 구현체의 예시 : AddLoggingClassAdapter를 사용하여 클래스 방문 시작
        classReader.accept(new AddLoggingClassAdapter(classWriter), 0);


        /*
        ClassVisitor 객체(classVisitor)를 accept 메서드의 인자로 넘기는 이유?
        클래스 파일을 순회하며 발생하는 각종 이벤트(예: 클래스 방문 시작, 메서드 방문, 필드 방문 등)를 처리하기 위함.

        ClassVisitor는 이러한 이벤트들을 받아서 처리하는 콜백 메서드들의 집합을 제공한다.
         */
        //classReader.accept(classVisitor, 0);



    }
}
