package org.sch.asm_tree_api.code.hikari;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

/**
 * use for modify test, add logging
 */
public class AddLoggerPSTMT {
    public static void apply(ClassNode cn) {
        Iterator<MethodNode> iterator = cn.methods.iterator();
        while (iterator.hasNext()) {
            MethodNode methodNode = iterator.next();
            Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc); //MethodNode의 입력 타입, void일 경우 Null
            if (methodNode.name.contains("execute") && argumentTypes.length > 0) {
                InsnList il = new InsnList();

                il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
                il.add(new LdcInsnNode("이 자리에 바이트코드 변조 진행하였음."));
                il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

                methodNode.instructions.insert(il); //return보다 앞서서 메서드 맨 위에 지시문 추가.
            }
        }
    }
}
