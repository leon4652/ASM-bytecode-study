package org.agent.asm_tree_api.testcode.control_statement;

import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;
import static org.objectweb.asm.Opcodes.*;

/**
 * 해당 클래스는 Instrument Transformer 구현체로 사용할 것.
 * TreeAPITransformer
 *
 * BasicExample Class에 for문 메서드 추가하기

 public void ifMethod(int var1) {
 if (var1 < 0) {
 System.out.println("true");
 } else {
 System.out.println("false");
 }
 }

 */
public class IfExampleTree {
    public static void run(ClassNode cn) {
        //1. MethodNode 설정 및 지시문 List, Label, 지역변수
        MethodNode mn = new MethodNode();
        mn.name = "ifMethod";
        mn.desc = "(I)V";
        mn.access = ACC_PUBLIC;
        InsnList il = new InsnList();
        LabelNode elseLabel = new LabelNode(); //if문
        LabelNode endLabel = new LabelNode();

        //if문 체크

        il.add(new VarInsnNode(ILOAD, 1)); //지역변수 idx1 : 입력파라미터 = 스택에 저장
        il.add(new JumpInsnNode(IFGE, elseLabel)); //if문 비교에 사용
        //if-true
        // "true" 출력
        il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")); // System.out 객체를 스택에 푸시
        il.add(new LdcInsnNode("true")); // "true" 문자열을 스택에 푸시
        il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)); // println 메소드 호출


        //else에 가지 않도록 end로 jump 처리
        il.add(new JumpInsnNode(GOTO, endLabel));
        //else 위치
        il.add(elseLabel);
        // "false" 출력
        il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")); // System.out 객체를 스택에 푸시
        il.add(new LdcInsnNode("false")); // "false" 문자열을 스택에 푸시
        il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)); // println 메소드 호출


        //end 위치 - if문 끝
        il.add(endLabel);

        il.add(new InsnNode(RETURN));

        mn.instructions.add(il);
//        mn.maxStack = 3; frame 계산 생략(Compute_max 사용)
//        mn.maxLocals = 2;
        cn.methods.add(mn);
    }
}
