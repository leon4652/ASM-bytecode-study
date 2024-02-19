package org.agent.code.asm.testcode.chap03;

import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class SetF extends ClassVisitor {
    public SetF(ClassVisitor classVisitor) {
        super(Opcodes.ASM9, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(
            final int access,
            final String name,
            final String descriptor,
            final String signature,
            final String[] exceptions) {
        if (cv != null) { //유효성 검사용.
            return cv.visitMethod(access, name, descriptor, signature, exceptions);
        }

        return cv.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        return cv.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public void visitEnd() {
        //1. 필드 추가 ('public int f' 가 있다면 visitField 수행, 아니라면 새로 생성)
        FieldVisitor fv = cv.visitField(ACC_PUBLIC, "f", "I", null, null);
        if (fv != null) {
            fv.visitEnd();
        }

        //2. setF 메서드 추가
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "setF", "(I)V", null, null);
        if (mv != null) { //MethodVisitor 객체의 유효성을 검사
            mv.visitCode();             //메서드의 바이트코드 방문을 시작
//            mv.visitLocalVariable("inputParam1", "I", start, end, 1); //param의 변수명을 바꾸고 싶다면.. start/end는 label 범위

            //if문 체크
            mv.visitVarInsn(ILOAD, 1); // 로컬 변수에서 첫번째 값을 로드 (0은 this)
            Label label = new Label();  // 점프할 레이블 설정
            mv.visitJumpInsn(IFLT, label); //IFLT 체크 (음수일 경우 label로 점프)

            //양수일 경우
            mv.visitVarInsn(ALOAD, 0); // 'this' 참조를 로드(자신)
            mv.visitVarInsn(ILOAD, 1); // 다시 첫 번째 파라미터 값을 로드 (입력 파라미터)
            mv.visitFieldInsn(PUTFIELD, "com/dummy/jdbcserver/example_asm/chap3/GetFSetF", "f", "I"); // 첫 번째 파라미터의 값을 'f' 필드에 저장
            Label end = new Label(); // 메서드의 종료 부분으로 점프할 레이블을 생성 (사실상 else이거나, if문 바깥)
            mv.visitJumpInsn(GOTO, end); // 'end' 레이블로 무조건 점프

            //음수일 경우
            mv.visitLabel(label); // 'label' 레이블 위치.
            mv.visitFrame(F_SAME, 0, null, 0, null); // 현재 스택과 로컬 변수의 상태를 JVM에 알림.
            mv.visitTypeInsn(NEW, "java/lang/IllegalArgumentException"); // `IllegalArgumentException` 인스턴스를 생성한다.
            mv.visitInsn(DUP); // 생성자 호출을 위해 인스턴스의 복사본을 스택에 둔다. -> INVOKESPECIAL에서 사용
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "()V"); // 예외 객체의 생성자 호출
            mv.visitInsn(ATHROW); // throw Exception.

            //End Label
            mv.visitLabel(end); // 'end' 레이블 위치
            mv.visitFrame(F_SAME, 0, null, 0, null); // 현재 스택과 로컬 변수의 상태를 JVM에 알림
            mv.visitInsn(RETURN); // 메서드를 종료
            mv.visitMaxs(2, 2); //스택의 최대 깊이와 변수 최대 수를 설정 (이 코드를 실제 JVM이 읽을 때, 프레임 크기를 몇으로 해야하는지 설정)
            mv.visitEnd();

            /**
             < 스택 설정과 상태 알림 >
             스택 설정 (visitMaxs):
             - 메서드의 실행 도중 스택의 최대 크기와 로컬 변수의 최대 수를 JVM에 알려준다. 즉, 메서드 실행에 필요한 최대 리소스를 설정
             - 이 설정은 메서드의 복잡성과 필요한 자원을 반영하며, JVM이 메모리를 효율적으로 할당하고 관리할 수 있도록 돕는다.

             상태 알림 (visitFrame):
             - 메서드의 실행 상태를 정확하게 JVM에 알리기 위해 필요.
             - JVM이 메서드의 실행을 정확하게 이해하고, 타입 검증 및 실행 시 발생할 수 있는 오류를 미리 방지하는 역할.

             이 과정을 통해, 메서드는 JVM에 의해 정확하게 실행되며 메모리 사용과 실행 흐름이 최적화된다.
             visitFrame, RETURN, visitMaxs, visitEnd의 호출은 메서드의 정확한 종료와 자원 사용의 명시를 보장하며, 바이트코드의 정확성과 효율성을 높인다.
             */
        }

        super.visitEnd();
    }

    //visitEnd : A -> B -> ClassVisitor의 VisitEnd 시행 후 종료
}
