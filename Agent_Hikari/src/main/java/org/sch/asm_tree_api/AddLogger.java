package org.sch.asm_tree_api;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Arrays;
import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;


public class AddLogger {
    public AddLogger(ClassNode cn) {
        Iterator<MethodNode> iterator = cn.methods.iterator();
        while (iterator.hasNext()) {
            MethodNode methodNode = iterator.next();
            Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc); //MethodNode의 입력 타입, void일 경우 Null
            System.out.println("TIG : " + methodNode.name);
            if (methodNode.name.contains("execute") && argumentTypes.length > 0) {
                System.out.println("TTTTTTTTT");
                InsnList il = new InsnList();
//                il.add(new VarInsnNode(ALOAD, 0)); //this save
//                il.add(new MethodInsnNode(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false)); //object constructor
//                il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
//                il.add(new VarInsnNode(ALOAD, 1)); //input param 1 save
//                il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

                il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
                il.add(new LdcInsnNode("생성자 생성됨."));
                il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

                System.out.println("로그 추가 : " + methodNode.name);
                methodNode.instructions.insert(il); //return보다 앞서서 메서드 맨 위에 지시문 추가.
            }
        }
    }
}
