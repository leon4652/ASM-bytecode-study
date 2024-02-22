package org.agent.asm_tree_api.testcode.insnlist;

import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.ACC_ABSTRACT;

/**
 * 새로운 class 생성
 * ClassNode에 생성자 및 메서드, 지역 변수 추가 예제
 */
public class BasicClassLocalValue {
    static private ClassNode cn = new ClassNode();

    public static ClassNode getClassNode(String className) {
        cn.version = V21;
        cn.access = ACC_PUBLIC;
        cn.name = className; //패키지 + 클래스명
        cn.superName = "java/lang/Object"; //객체 타입
//        cn.interfaces.add(""); //implements

        //기본 생성자 생성 후 적용
        MethodNode mn = new MethodNode(ACC_PUBLIC, "<init>", "()V", null, null); //메서드 노드
        InsnList il = new InsnList(); //지시사항
        il.add(new VarInsnNode(ALOAD, 0)); //생성자 스택 적재
        il.add(new MethodInsnNode(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)); //init 호출
        il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
        il.add(new LdcInsnNode("생성자 생성됨. BasicClassLocalValue"));
        il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
        il.add(new InsnNode(RETURN));
        mn.instructions = il;   //메서드 노드에 지시사항 추가
        cn.methods.add(mn);     //클래스에 메서드 노드 추가

        //특정 메서드의 지역 변수 넣기,
        //Static 정적 변수는 this 지역변수 0번 인덱스를 가지지 않는다. 이를 유의
        MethodNode mnLocalValue = new MethodNode(ACC_PUBLIC + ACC_STATIC, "addTwoNumbers", "()I", null, null);
        il = new InsnList();

        // 지역 변수 'a'에 5 할당
        il.add(new LdcInsnNode(5));
        il.add(new VarInsnNode(ISTORE, 1));

        // 지역 변수 'b'에 10 할당
        il.add(new LdcInsnNode(10));
        il.add(new VarInsnNode(ISTORE, 2));

        // 'a'와 'b' 불러와 더하기
        il.add(new VarInsnNode(ILOAD, 1));
        il.add(new VarInsnNode(ILOAD, 2));
        il.add(new InsnNode(IADD));

        // 결과 반환
        il.add(new InsnNode(IRETURN));

        mnLocalValue.instructions = il; //메서드 지시사항 추가
        cn.methods.add(mnLocalValue); //메서드 추가

        return cn;
    }
}
