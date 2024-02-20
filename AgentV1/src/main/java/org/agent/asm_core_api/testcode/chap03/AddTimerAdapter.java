package org.agent.asm_core_api.testcode.chap03;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;


import static org.objectweb.asm.Opcodes.*;



public class AddTimerAdapter extends ClassVisitor {
    private String owner;
    private boolean isInterface;
    public AddTimerAdapter(ClassVisitor cv) {
        super(ASM9, cv);
    }

    //Class 및 인터페이스 방문 시 호출, 속성에 대한 정보를 가져온다.
    @Override public void visit(int version, int access, String name,
                                String signature, String superName, String[] interfaces) {
        cv.visit(version, access, name, signature, superName, interfaces);
        owner = name; //1. visit name
        isInterface = (access & ACC_INTERFACE) != 0; //2. 인터페이스 여부

    }


    @Override public MethodVisitor visitMethod(int access, String name,
                                               String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
                exceptions);

        //인터페이스가 아니고, init(생성자)가 아닌경우 적용. 즉, 생성자가 아닌 Class 내부 메서드에 적용
        if (!isInterface && mv != null && !name.equals("<init>")) {
            mv = new AddTimerMethodAdapter(mv);
        }
        return mv;
    }

    @Override public void visitEnd() {

        //public static timer 전역변수
        if (!isInterface) {
            FieldVisitor fv = cv.visitField(ACC_PUBLIC + ACC_STATIC, "timer",
                    "J", null, null);
            if (fv != null) {
                fv.visitEnd();
            }
        }
        cv.visitEnd();
    }

    class AddTimerMethodAdapter extends MethodVisitor {
        public AddTimerMethodAdapter(MethodVisitor mv) {
            super(ASM9, mv);
        }
        @Override public void visitCode() {
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, owner, "timer", "J");
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System",
                    "currentTimeMillis", "()J");
            mv.visitInsn(LSUB);
            mv.visitFieldInsn(PUTSTATIC, owner, "timer", "J");
        }

        //1. visitInsn 메서드는 메서드 내의 모든 인스트럭션(opcode)을 순회할 때 ASM 프레임워크에 의해 자동으로 호출된다.
        //2. 즉, opcode가 IRETURN부터 RETURN 사이, 또는 ATHROW일 때 이 로직이 적용된다.
        @Override public void visitInsn(int opcode) {
            if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
                mv.visitFieldInsn(GETSTATIC, owner, "timer", "J");
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System",
                        "currentTimeMillis", "()J");
                mv.visitInsn(LADD);
                mv.visitFieldInsn(PUTSTATIC, owner, "timer", "J");
            }
            mv.visitInsn(opcode);
        }
        @Override public void visitMaxs(int maxStack, int maxLocals) {
            mv.visitMaxs(maxStack + 4, maxLocals);
        }
    }
}