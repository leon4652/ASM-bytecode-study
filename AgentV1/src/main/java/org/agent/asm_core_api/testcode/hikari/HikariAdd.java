package org.agent.asm_core_api.testcode.hikari;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.*; //for opcodes

public class HikariAdd extends ClassVisitor {

    public HikariAdd(ClassVisitor classVisitor) {
        super(Opcodes.ASM9, classVisitor);
    }

    @Override
    public void visitEnd() {
        // 새로운 메서드 추가를 위한 MethodVisitor 생성
        MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "TestHikariADD", "(II)I", null, null);

        // 메서드 바이트코드 작성 시작
        mv.visitCode();
        // 메서드의 파라미터 로드
        mv.visitVarInsn(Opcodes.ILOAD, 0); // 첫 번째 인자 로드
        mv.visitVarInsn(Opcodes.ILOAD, 1); // 두 번째 인자 로드
        // 두 인자의 합 연산
        mv.visitInsn(Opcodes.IADD);
        // 연산 결과 반환
        mv.visitInsn(Opcodes.IRETURN);

        // 최대 스택 크기와 지역 변수의 수 설정
        mv.visitMaxs(2, 2);

        // 메서드 방문 종료
        mv.visitEnd();
        System.out.println("Add method");
        super.visitEnd();
    }
}
