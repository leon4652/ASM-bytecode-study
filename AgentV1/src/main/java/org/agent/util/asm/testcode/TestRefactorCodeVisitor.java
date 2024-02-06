package org.agent.util.asm.testcode;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 테스트용 ClassVisitor 구현체
 * 메서드 내부 값 변경하기
 */
public class TestRefactorCodeVisitor extends ClassVisitor {

    public TestRefactorCodeVisitor(ClassVisitor cv) {
        // 부모 클래스의 생성자에 ASM API 버전과 ClassVisitor 인스턴스(ClassWriter)를 전달
        super(Opcodes.ASM9, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        System.out.println("name : " + name);
        // "dummy-insert" 메서드일 경우에만 특별한 MethodVisitor를 반환
        if ("insertByteCode".equals(name)) {
            return new CustomMethodVisitor(mv);
        }

        return mv; // 기본 MethodVisitor 반환
    }

    //LDC 파싱을 위해, MethodVisitor의 visitLdcInsn을 오버라이드한 커스텀 객체 생성
    private static class CustomMethodVisitor extends MethodVisitor {

        public CustomMethodVisitor(MethodVisitor mv) {
            super(Opcodes.ASM9, mv);
        }

        @Override
        public void visitLdcInsn(Object cst) {
            //각 LDC마다 조건 확인, 문자열을 찾아 변경
            System.out.println("LDC 라인 확인 : " + cst);

            if ("this is raw code".equals(cst)) {
                super.visitLdcInsn("modified code");
            } else {
                // 다른 상수는 그대로
                super.visitLdcInsn(cst);
            }
        }
    }

}


