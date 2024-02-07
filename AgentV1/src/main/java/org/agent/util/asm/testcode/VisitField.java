package org.agent.util.asm.testcode;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 클래스 필드 추가 및 삭제
 */
public class VisitField extends ClassVisitor {

    private String fName;
    private boolean isFieldPresent;

    public VisitField(ClassVisitor cv, String fName) {
        super(Opcodes.ASM9, cv);
        this.fName = fName;

    }

    // 필드 방문 시 호출. 기존 필드를 수정할 수 있음
    //here 2
    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if(name.equals(fName)){
            isFieldPresent = true;
        }

        // "a"라는 이름의 static int 필드를 삭제하는 조건 : 순회 시 발견
        if ("a".equals(name) && "I".equals(descriptor) && (access & Opcodes.ACC_STATIC) != 0) {
            // 특정 조건을 만족하는 필드는 방문 처리를 생략하여 삭제 효과를 낸다.
            System.out.println("find static A method");
            return null;
        }

        System.out.println("현재 필드 : " + name + " " + descriptor + " " + signature + " " + value);
        return super.visitField(access, name, descriptor, signature, value);
    }

    //여기의 visitEnd는 lassVisitor의 visitEnd
    @Override
    public void visitEnd() {
        System.out.println("visitEnd 호출 : ");
        if(!isFieldPresent){
            cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, fName, "Ljava/lang/String", null, null);
//            cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "newFieldInt", "I", null, 42);
        }
        super.visitEnd();
    }
}
