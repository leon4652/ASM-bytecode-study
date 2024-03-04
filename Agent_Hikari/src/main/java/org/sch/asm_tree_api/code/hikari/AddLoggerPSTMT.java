package org.sch.asm_tree_api.code.hikari;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

/**
 * use for modify test, add logging
 */
public class AddLoggerPSTMT {
    public static void apply(ClassNode cn) {
        //2진수 bit And 검증 : ABSTRACT(1024), 그 외 1023 이하..(PUBLIC = 1)
        if ((cn.access & Opcodes.ACC_ABSTRACT) != 0) return;

        Iterator<MethodNode> iterator = cn.methods.iterator();
        while (iterator.hasNext()) {
            MethodNode methodNode = iterator.next();
//            Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc); //MethodNode의 입력 타입, void일 경우 Null
            System.out.println("TRIG, MethodName : " + methodNode.name);
            if (methodNode.name.contains("execute")) {
                InsnList il = new InsnList();
                String content = "[class/method] : " + cn.name + " : " + methodNode.name; //sysout content

                //입력 파라미터가 있다면 입력 파라미터도 출력


                il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
                il.add(new LdcInsnNode(content));
                il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));


                methodNode.instructions.insert(il); //return보다 앞서서 메서드 맨 위에 지시문 추가.
            }
        }
    }

    private void temp() {
    }
}
