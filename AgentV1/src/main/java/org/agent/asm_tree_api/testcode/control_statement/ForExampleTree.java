package org.agent.asm_tree_api.testcode.control_statement;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.TraceClassVisitor;

import static org.objectweb.asm.Opcodes.*;

/**
for문 구현
 */
public class ForExampleTree {
    public static void run(ClassNode cn) {

        MethodNode mn = new MethodNode(ACC_PROTECTED, "forMethod", "(I)V", null, null);
        InsnList il = new InsnList();

        //label 목록
        LabelNode forStart = new LabelNode(); //label
        LabelNode forEnd = new LabelNode();
        LabelNode localStart = new LabelNode(); //localA
        LabelNode localEnd = new LabelNode();

        //지역변수들 목록
        mn.localVariables.add(new LocalVariableNode("localA", "I", null, localStart, localEnd, 2));
        mn.localVariables.add(new LocalVariableNode("i", "I", null, forStart, forEnd, 3)); //for문 내부 I 생성

        //+=====세부 로직=====+
        il.add(localStart);//local start

        //1. method 지역변수(idx 2) 추가. 0 = this, 1 = inputParam
        il.add(new VarInsnNode(ILOAD, 1));//stack에 입력 파라미터(idx1) 대입
        il.add(new LdcInsnNode(1));//stack에 상수 1 대입
        il.add(new InsnNode(IADD)); //두 상수를 더하기
        il.add(new VarInsnNode(ISTORE, 2)); //localA 변수에 더한 상수 대입

        //2. for문
        il.add(new LdcInsnNode(0)); //Stack에 0 저장
        il.add(new VarInsnNode(ISTORE, 3)); //지역변수 I에 0 대입
        il.add(forStart); //for 시작
        //for 내부 구문 비교 (for(int i = 0;i < localA; i++)
        //I값이랑 localA값 비교 조건
        il.add(new VarInsnNode(ILOAD, 3)); //i 로드
        il.add(new VarInsnNode(ILOAD, 2)); //localA 로드
        il.add(new JumpInsnNode(IF_ICMPGE, forEnd)); //로드한 값 비교하여 클 경우 forEnd로 이동

        //sysout
        il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")); // System.out 객체를 스택에 푸시
        il.add(new VarInsnNode(ILOAD, 2));
        il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false)); // println 메소드 호출

        //i++
        il.add(new IincInsnNode(3, 1)); // i 값을 1 증가시킴

        il.add(new JumpInsnNode(GOTO, forStart)); //반복 시행
        il.add(forEnd);   //for 루프 종료
        il.add(localEnd); //local End

        //return
        il.add(new InsnNode(RETURN)); //return;
        mn.instructions.add(il);
        cn.methods.add(mn);

    }
}
