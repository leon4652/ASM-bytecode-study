package org.agent.util.asm.testcode.chap1_2;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 테스트용 ClassVisitor 구현체
 * 메서드 제거하기
 */
public class TestRemoveCodeVisitor extends ClassVisitor {

    public TestRemoveCodeVisitor(ClassVisitor cv) {
        // 부모 클래스의 생성자에 ASM API 버전과 ClassVisitor 인스턴스(ClassWriter)를 전달
        super(Opcodes.ASM9, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        System.out.println("name : " + name);
        // "dummy-insert" 메서드일 경우에만 제거 : 이 경우 null만 return하면 제거된다.
        if ("insertByteCode".equals(name)) {
            return null;
        }

        return mv; // 기본 MethodVisitor 반환
    }
 }



