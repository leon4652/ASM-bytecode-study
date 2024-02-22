package org.agent.asm_tree_api.testcode.chap07;

import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * 7.1.2. 메소드 생성(Generating methods)
 * Tree API를 사용한 메서드 생성
 */
public class checkAndSetF {
    protected ClassNode cn;
    public checkAndSetF(ClassNode cn) {
        this.cn = cn;
    }

    static void run() {
        MethodNode mn = new MethodNode(); //추가하고자 하는 메서드
        InsnList il = mn.instructions; //지시사항 리스트
        il.add(new VarInsnNode(ILOAD, 1));
        LabelNode label = new LabelNode(); //label 생성

        il.add(new JumpInsnNode(IFLT, label)); //stack IFLT 비교, false일 경우 label로 점프(A)
        il.add(new VarInsnNode(ALOAD, 0));
        il.add(new VarInsnNode(ILOAD, 1));
        il.add(new FieldInsnNode(PUTFIELD, "com/dummy/jdbcserver/example_asm/chap3/BasicExample", "f", "I"));
        LabelNode end = new LabelNode(); //end label 생성
        il.add(new JumpInsnNode(GOTO, end)); //end(B)로 점프
        il.add(label);  //(A) label

        il.add(new FrameNode(F_SAME, 0, null, 0, null)); //mv.visitFrame과 동일(스택 프레임 갱신)
        il.add(new TypeInsnNode(NEW, "java/lang/IllegalArgumentException")); //new Exception 생성
        il.add(new InsnNode(DUP)); //복사
        il.add(new MethodInsnNode(INVOKESPECIAL,
                "java/lang/IllegalArgumentException", "<init>", "()V")); //복사값으로 생성자 호출
        il.add(new InsnNode(ATHROW));
        il.add(end); //end(B)
        il.add(new FrameNode(F_SAME, 0, null, 0, null));
        il.add(new InsnNode(RETURN));

        mn.maxStack = 2;
        mn.maxLocals = 2;
    }
}
