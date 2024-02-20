package org.agent.asm_tree_api;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

public class VisitorAdapter {


    /**
     * ClassNode 생성 틀
     * @param classWriter
     * @param className
     * @return
     */
    public static ClassNode setClassNode(String className) {

        ClassNode cn = new ClassNode();
        cn.version = V21;
        cn.access = ACC_PUBLIC;
        cn.name = className; //패키지 + 클래스명
        cn.superName = "java/lang/Object"; //객체 타입
//        cn.interfaces.add(""); //implements

        //기본 생성자 생성 후 적용
        MethodNode mn = new MethodNode(ACC_PUBLIC, "<init>", "()V", null, null);
        InsnList il = new InsnList(); //바이트코드 조작 관련
        il.add(new VarInsnNode(ALOAD, 0)); //생성자 스택 적재
        il.add(new MethodInsnNode(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)); //init 호출
        mn.instructions.add(new InsnNode(RETURN));

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
