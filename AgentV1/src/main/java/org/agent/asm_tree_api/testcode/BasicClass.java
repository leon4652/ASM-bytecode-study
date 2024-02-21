package org.agent.asm_tree_api.testcode;

import org.objectweb.asm.tree.*;
import static org.objectweb.asm.Opcodes.*;

public class BasicClass {
    static private ClassNode cn = new ClassNode();
    public BasicClass() {
    }

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
        il.add(new LdcInsnNode("생성자 생성됨."));
        il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
        il.add(new InsnNode(RETURN));
        mn.instructions = il;   //메서드 노드에 지시사항 추가
        cn.methods.add(mn);     //클래스에 메서드 노드 추가

        //필드값
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC,
                "LESS", "I", null, -1));
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC,
                "EQUAL", "I", null, 0));
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC,
                "GREATER", "I", null, 1));
        cn.methods.add(new MethodNode(ACC_PUBLIC + ACC_ABSTRACT,
                "compareTo", "(Ljava/lang/Object;)I", null, null));

        return cn;
    }
}
