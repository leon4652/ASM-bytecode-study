package org.agent.asm_tree_api.testcode.chap07;

import org.objectweb.asm.tree.*;
import static org.objectweb.asm.Opcodes.*;
import org.agent.asm_tree_api.testcode.chap06.ClassTransformer;

import java.util.Iterator;
import java.util.List;

public class AddTimerTransformer extends ClassTransformer {
    public AddTimerTransformer(ClassTransformer ct) {
        super(ct);
    }

    @Override
    public void transform(ClassNode cn) {

        //cn method 영역 순회
        for (MethodNode mn : (List<MethodNode>) cn.methods) {
            //생성자(static, 인스턴스)일 경우 스킵
            if ("<init>".equals(mn.name) || "<clinit>".equals(mn.name)) {
                continue;
            }
            InsnList insns = mn.instructions; //insns : mn 메서드에 속한 지시사항들의 List
            if (insns.size() == 0) {
                continue;
            }
            //j는 지시사항 List를 순회하는 insns의 Iterater
            //리스트 인덱스 순회보다, InsnList는 Iterator 사용이 효과적이라고 함.
            Iterator<AbstractInsnNode> j = insns.iterator();
            while (j.hasNext()) {
                AbstractInsnNode in = j.next();
                int op = in.getOpcode(); //operands 지시문 받기
                if ((op >= IRETURN && op <= RETURN) || op == ATHROW) { //return 구문 전(timer+=..)과 Athrow 구문 전(timer-=) 사용
                    InsnList il = new InsnList();
                    il.add(new FieldInsnNode(GETSTATIC, cn.name, "timer", "J"));
                    il.add(new MethodInsnNode(INVOKESTATIC, "java/lang/System",
                            "currentTimeMillis", "()J"));
                    il.add(new InsnNode(LADD));
                    il.add(new FieldInsnNode(PUTSTATIC, cn.name, "timer", "J"));
                    insns.insert(in.getPrevious(), il);
                }
            }
            InsnList il = new InsnList();
            il.add(new FieldInsnNode(GETSTATIC, cn.name, "timer", "J"));
            il.add(new MethodInsnNode(INVOKESTATIC, "java/lang/System",
                    "currentTimeMillis", "()J"));
            il.add(new InsnNode(LSUB));
            il.add(new FieldInsnNode(PUTSTATIC, cn.name, "timer", "J"));
            insns.insert(il);
            mn.maxStack += 4;
        }

        //timer 필드 선언
        int acc = ACC_PUBLIC + ACC_STATIC;
        cn.fields.add(new FieldNode(acc, "timer", "J", null, null));
        super.transform(cn);
    }
}