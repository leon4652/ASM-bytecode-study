package org.agent.code.asm.testcode.chap1_2;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 테스트용 ClassVisitor 구현체
 */
public class TestVisitor extends ClassVisitor {
    static String className;

    public TestVisitor(ClassVisitor cv) {
        // 부모 클래스의 생성자에 ASM API 버전과 ClassVisitor 인스턴스(ClassWriter)를 전달
        super(Opcodes.ASM9, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        className = name.replace('/', '.');
    }

    //각 메서드 방문시 호출
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        System.out.println("method : " + name);
        return mv; // 변형된 MethodVisitor 반환
    }

    //ClassReader가 모든 요소를 처리하고 마무리할 때 호출
    @Override
    public void visitEnd() {
        //System.out.println("visitEnd");
    }
}
