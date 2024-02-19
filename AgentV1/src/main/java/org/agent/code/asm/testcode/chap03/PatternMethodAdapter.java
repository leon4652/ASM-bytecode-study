package org.agent.code.asm.testcode.chap03;

import org.objectweb.asm.MethodVisitor;

/**
 * 추상 클래스
 * 1. 각 메서드 내 바이트코드 명령이 실행될 때 해당 명령에 해당하는 visitXXXInsn 메서드 호출
 * 2. 방문한 메서드는 visitInsn() 호출
 */

public abstract class PatternMethodAdapter extends MethodVisitor {
    protected final static int SEEN_NOTHING = 0; //패턴 인식 전 초기 상태
    protected int state; //상태 변수(패턴 상태 추척)

    public PatternMethodAdapter(int api, MethodVisitor mv) {
        super(api, mv);
    }

    @Override
    public void visitInsn(int opcode) {
        visitInsn();
        mv.visitInsn(opcode);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        visitInsn();
        mv.visitIntInsn(opcode, operand);
    }

    protected abstract void visitInsn();
}
