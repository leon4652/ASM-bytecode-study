package org.agent.util.asm.testcode.chap03;

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

        //2. 메서드 추가
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "setF", "(I)V", null, null);
        if (mv != null) { //MethodVisitor 객체의 유효성을 검사
            mv.visitCode();             //메서드의 바이트코드 방문을 시작
            mv.visitVarInsn(ILOAD, 1); // 로컬 변수에서 첫번째 값을 로드 (0은 this)
            //if문
            Label label = new Label();  // 점프할 레이블 설정
            mv.visitJumpInsn(IFLT, label); //IFLT 체크 후 아니라면 label로 점프 [조건]
            mv.visitVarInsn(ALOAD, 0); // 'this' 참조를 로드(자신)
            mv.visitVarInsn(ILOAD, 1); // 다시 첫 번째 파라미터 값을 로드 (입력 파라미터)
            mv.visitFieldInsn(PUTFIELD, "com/dummy/jdbcserver/Chap03", "f", "I"); // 첫 번째 파라미터의 값을 'f' 필드에 저장
            Label end = new Label(); // 메서드의 종료 부분으로 점프할 레이블을 생성 (사실상 else이거나, if문 바깥)
            mv.visitJumpInsn(GOTO, end); // 'end' 레이블로 무조건 점프합니다.
            mv.visitLabel(label); // 'label' 레이블 위치입니다. 여기서 부터는 음수일 때 실행됩니다.
            mv.visitFrame(F_SAME, 0, null, 0, null); // 현재 스택과 로컬 변수의 상태를 JVM에 알립니다.
            mv.visitTypeInsn(NEW, "java/lang/IllegalArgumentException"); // `IllegalArgumentException` 인스턴스를 생성합니다.
            mv.visitInsn(DUP); // 생성자 호출을 위해 인스턴스의 복사본을 스택에 둡니다.
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "()V"); // 예외 객체의 생성자를 호출합니다.
            mv.visitInsn(ATHROW); // 예외를 던집니다.
            mv.visitLabel(end); // 'end' 레이블 위치 : 정상적인 종료 부분
            mv.visitFrame(F_SAME, 0, null, 0, null); // 현재 스택과 로컬 변수의 상태를 JVM에 알림
            mv.visitInsn(RETURN); // 메서드를 종료
            mv.visitMaxs(2, 2); //스택의 최대 깊이와 변수 최대 수를 설정
            mv.visitEnd();              //메서드 방문을 종료
        }

        super.visitEnd();
    }

    //visitEnd : A -> B -> ClassVisitor의 VisitEnd 시행 후 종료
}
