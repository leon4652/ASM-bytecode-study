package org.agent.code.asm.testcode.chap03;


import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;


public class WrappingMethodVisitor extends ClassVisitor {
    public WrappingMethodVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM9, classVisitor);
        classVisitor.visitField(ACC_PUBLIC + ACC_STATIC, "timer", "J", null, null);
    }


    //각 메서드 방문시 호출
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        // (A) 올바른 로직
        if (name.equals("m")) {
            // MethodVisitor를 래핑하는 Adapter를 생성하여 커스텀 로직 적용
            return new MethodVisitor(ASM9, mv) {
                @Override
                public void visitCode() {
                    super.visitCode(); //메서드 방문의 시작을 ASM 시스템에 알리고, 필요한 초기화 작업을 수행
                    // `System.out.println` 호출을 위한 바이트코드 삽입
                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    mv.visitLdcInsn("Method m is modified.");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                }
            };

        }

        // (B) 이렇게 할 경우 적용되지 않음
        if (name.equals("m")) {
            // MethodVisitor를 래핑하는 Adapter를 생성하여 커스텀 로직 적용

            mv.visitCode(); //이렇게 해도 이 로직은 작용하지 않는다.

            // `System.out.println` 호출을 위한 바이트코드 삽입
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("Method m is modified.");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            return mv;
        }

        return mv; // 조건에 해당하지 않는 경우, 변경 없이 mv 반환
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

}
