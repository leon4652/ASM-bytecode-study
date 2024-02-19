package org.agent.code.asm.testcode.chap03;

import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;


public class StatelessTransformationsExample extends ClassVisitor {
    public StatelessTransformationsExample(ClassVisitor classVisitor) {
        super(Opcodes.ASM9, classVisitor);
        classVisitor.visitField(ACC_PUBLIC + ACC_STATIC, "timer", "J", null, null);
    }


    //각 메서드 방문시 호출
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        if (name.equals("m")) {
            // MethodVisitor를 래핑하는 Adapter를 생성하여 커스텀 로직 적용
            return new AddTimerMethodAdapter(mv);
        }


        return mv; // 조건에 해당하지 않는 경우, 변경 없이 mv 반환
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

    class AddTimerMethodAdapter extends MethodVisitor {
        public AddTimerMethodAdapter(MethodVisitor mv) {
            super(ASM9, mv);
        }

        @Override
        public void visitCode() {
            super.visitCode();

            //현재 시간을 timer에 저장
            mv.visitVarInsn(ALOAD, 0); //1. this 참조 스택 저장 (이걸 먼저 수행해야 함. 순서 영향 있음 [객체, 변수])
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            //2. 시간 함수 호출, 스택에 J 타입 값 저장
            mv.visitFieldInsn(PUTSTATIC, "com/dummy/jdbcserver/example_asm/chap3/StatelessTransformationsExample", "timer", "J");
            //1, 2 동시 사용

            //print 1
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"); //1 메서드 스택 저장
            mv.visitLdcInsn("now time is "); //2 스택 저장
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false); //사용
            //print 2
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"); //1 스택 저장(메서드)
            mv.visitFieldInsn(GETSTATIC, "com/dummy/jdbcserver/example_asm/chap3/StatelessTransformationsExample", "timer", "J");
            // 2. timer 필드 값 로드(지역 변수 -> 스택)
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V", false); //1 2 사용

            //Sleep() 기존 로직
            mv.visitLdcInsn(2000L); // sleep 시간인 2000L 스택에 푸시
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Thread", "sleep", "(J)V", false);


            //현재 시간을 timer에 저장
            mv.visitVarInsn(ALOAD, 0); //1. this 참조 스택 저장 (이걸 먼저 수행해야 함. 순서 영향 있음 [객체, 변수])
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            //2. 시간 함수 호출, 스택에 J 타입 값 저장
            mv.visitFieldInsn(PUTSTATIC, "com/dummy/jdbcserver/example_asm/chap3/StatelessTransformationsExample", "timer", "J");
            //1, 2 동시 사용
            //print 1
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"); //1 메서드 스택 저장
            mv.visitLdcInsn("And now time is "); //2 스택 저장
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false); //사용
            //print 2
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"); //1 스택 저장(메서드)
            mv.visitFieldInsn(GETSTATIC, "com/dummy/jdbcserver/example_asm/chap3/StatelessTransformationsExample", "timer", "J");
            // 2. timer 필드 값 로드(지역 변수 -> 스택)
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V", false); //1 2 사용
        }
    }
}
