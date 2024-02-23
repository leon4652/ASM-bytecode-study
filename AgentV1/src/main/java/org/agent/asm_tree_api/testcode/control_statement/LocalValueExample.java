package org.agent.asm_tree_api.testcode.control_statement;

import org.objectweb.asm.tree.*;

import java.util.ArrayList;

import static org.objectweb.asm.Opcodes.*;

/**

 */
public class LocalValueExample {
    public static void run(ClassNode cn) {
        //1. MethodNode 설정 및 지시문 List, Label, 지역변수
        MethodNode mn = new MethodNode();
        mn.name = "localMethod";
        mn.desc = "(I)V";
        mn.access = ACC_PUBLIC;
        InsnList il = new InsnList();
        LabelNode startLabel = new LabelNode(); //if문
        LabelNode endLabel = new LabelNode();

        // 로컬 변수 추가
        // localVariables 리스트 초기화
        mn.localVariables = new ArrayList<>();
        mn.localVariables.add(new LocalVariableNode("param", "I", null, startLabel, endLabel, 1)); // 메서드 입력 파라미터
        mn.localVariables.add(new LocalVariableNode("localVar", "I", null, startLabel, endLabel, 2)); // 추가한 로컬 변수

        il.add(startLabel); //local Start
        // 매개변수 값을 로컬 변수에 저장 (예를 들어, 매개변수의 인덱스가 1인 경우)
        il.add(new InsnNode(ICONST_5)); // 상수 5를 로드
        il.add(new VarInsnNode(ISTORE, 2)); // 로컬 변수 인덱스 2에 저장


        //출력
        il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")); // System.out 객체를 스택에 푸시
        il.add(new VarInsnNode(ILOAD, 1)); // 저장된 로컬 변수 값을 스택에 푸시
        il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false)); // println 메소드 호출

        //출력
        il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")); // System.out 객체를 스택에 푸시
        il.add(new VarInsnNode(ILOAD, 2)); // 저장된 로컬 변수 값을 스택에 푸시
        il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false)); // println 메소드 호출



        il.add(endLabel); //local End
        il.add(new InsnNode(RETURN));


        mn.instructions.add(il);
        cn.methods.add(mn);

    }
}
