package org.agent.asm_tree_api.testcode.control_statement;

import org.objectweb.asm.tree.*;
import static org.objectweb.asm.Opcodes.*;

/**
 * while 레퍼런스
 */
public class WhileExampleTree {
    public static void run(ClassNode cn) {
        MethodNode mn = new MethodNode(ACC_PRIVATE, "whileMethod", "(I)I", null, null);
        InsnList il = new InsnList();

        //label
        LabelNode localValStart = new LabelNode();
        LabelNode localValEnd = new LabelNode();
        LabelNode startWhile = new LabelNode();
        LabelNode endWhile = new LabelNode();

        //Method
        il.add(localValStart); //method start

        //지역변수 값 할당
        il.add(new LdcInsnNode(10));
        il.add(new VarInsnNode(ISTORE, 2));

        //sysout : 이 구문이 없다면 while문이 for문으로 변경됨 : for(trig = 10; var1 <= trig; ++trig) .. 동일한 로직이기는 함
        il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")); // System.out 객체를 스택에 푸시
        il.add(new VarInsnNode(ILOAD, 2));
        il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false)); // println 메소드 호출


        //while문 start
        il.add(startWhile); //start
        il.add(new VarInsnNode(ILOAD, 1)); //입력 param 저장
        il.add(new VarInsnNode(ILOAD, 2)); //trig 지역 변수 저장
        il.add(new JumpInsnNode(IF_ICMPGT, endWhile)); //비교 후 점프
        il.add(new IincInsnNode(1, 1)); // 입력 파라미터 값 증가
        il.add(new JumpInsnNode(GOTO, startWhile));
        il.add(endWhile); //end while

        il.add(new VarInsnNode(ILOAD, 1));  //메서드 입력 파라미터 1의 값을 스택에 저장
        il.add(new InsnNode(IRETURN));              //이 값을 return
        il.add(localValEnd); //method end

        //localVariables
        mn.localVariables.add(new LocalVariableNode("trig", "I", null, localValStart, localValEnd, 2));

        mn.instructions.add(il);
        cn.methods.add(mn);
    }
}
