package org.agent.code.asm.testcode.chap03;

import org.objectweb.asm.*;
import static org.objectweb.asm.Opcodes.*;

//PatternMethodAdapter 구현체이자, 특정 MethodVisitor에서 호출될 수 있는 Adapter Class
/*
주된 목적 : ICONST_0 다음에 오는 IADD 인스트럭션을 최적화하여 제거하는 것
ICONST_0은 스택에 정수 0을 푸시하고, IADD는 스택의 상위 두 정수를 더하는 연산.
만약 IADD 연산 전에 스택에 푸시된 값이 0이라면, 이 연산은 실질적으로 아무런 변화도 주지 않는다(0 + x = x).
따라서 이러한 패턴을 감지하고 불필요한 IADD 연산을 제거함으로써 실행 시간과 바이트코드의 크기를 최적화할 수 있다.
 */
public class RemoveAddZeroAdapter extends PatternMethodAdapter {
    private static int SEEN_ICONST_0 = 1; //기본 상태 : 1
    public RemoveAddZeroAdapter(MethodVisitor mv) {
        super(ASM9, mv);
    }

    //MethodVisitor 구현체 : 메서드 바이트코드의 각 인스트럭션(opcode)을 방문할 때마다 호출
    @Override public void visitInsn(int opcode) {
        if (state == SEEN_ICONST_0) { //기본 상태일 경우
            if (opcode == IADD) { //그리고 IADD opcodes 요청일 경우
                state = SEEN_NOTHING; //아무것도 하지 않음 (0 상태 변경)
                return;
            }
        }
        visitInsn(); //(A) 구현체 호출
        if (opcode == ICONST_0) {  //ICONST_0 opcode일 경우
            state = SEEN_ICONST_0; //기본 상태로 변경
            return;
        }
        mv.visitInsn(opcode);
    }

    //PatternMethodAdapter - visitInsn 추상 메서드 구현체
    //위처럼 자동으로 호출되는 것이 아니라, 이 메서드가 필요할 때 호출해서 쓴다. (a)의 경우처럼
    @Override protected void visitInsn() {
        if (state == SEEN_ICONST_0) {
            mv.visitInsn(ICONST_0); //상태가 0이라면 스택에 0 저장
        }
        state = SEEN_NOTHING;
    }
}
