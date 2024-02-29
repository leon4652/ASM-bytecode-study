package org.sch.asm_tree_api.code.hikari;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.sch.util.CodePrinter;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

/**
 * use for modify test, add logging
 */
public class AddLoggerAllClass {
    public static void apply(ClassNode cn) {
        //2진수 bit And 검증 : ABSTRACT(1024), 그 외 1023 이하..(PUBLIC = 1)
        if((cn.access & Opcodes.ACC_ABSTRACT) != 0) return;

        Iterator<MethodNode> iterator = cn.methods.iterator();
        while (iterator.hasNext()) {
            MethodNode methodNode = iterator.next();
                InsnList il = new InsnList();
                il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
                il.add(new LdcInsnNode("[class/method] : " + cn.name + " : " + methodNode.name ));
                il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));

                methodNode.instructions.insert(il); //return보다 앞서서 메서드 맨 위에 지시문 추가.

        }
    }
}
