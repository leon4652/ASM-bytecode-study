package org.agent.asm_core_api.testcode.chap1_2;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.*;

public class GetF extends ClassVisitor {
    public GetF(ClassVisitor classVisitor) {
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
            //여기 새로운 field 값, 즉, "public int f" 을 만들고 싶음.
            fv.visitEnd();
        }

        //2. 메서드 추가
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "getF", "()I", null, null);
        if (mv != null) { //MethodVisitor 객체의 유효성을 검사
            mv.visitCode();             //메서드의 바이트코드 방문을 시작
            mv.visitVarInsn(ALOAD, 0);  //로컬 변수 배열에서 첫 번째 변수를 로드하여 피연산자 스택에 푸시
            mv.visitFieldInsn(GETFIELD, "com/dummy/jdbcserver/example_asm/chap3/GetFSetF", "f", "I"); //객체의 필드에 접근
            mv.visitInsn(IRETURN);      //정수 값을 반환. 피연산자 스택의 상단에 있는 값을 반환값으로 사용하여 메서드 호출을 종료
            mv.visitMaxs(1, 1);         //메서드의 최대 스택 크기와 지역 변수의 수를 지정.
            mv.visitEnd();              //메서드 방문을 종료 (visitEnd 종료) -- (A)
        }

        super.visitEnd(); //클래스 방문이 완전히 끝났음을 의미 (부모 클래스의 visitEnd() 호출로 정상적으로 종료 시 사용) -- (B)
    }
    
    //visitEnd : A -> B -> ClassVisitor의 VisitEnd 시행 후 종료
}
