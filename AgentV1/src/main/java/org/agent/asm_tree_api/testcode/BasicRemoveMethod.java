package org.agent.asm_tree_api.testcode;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.IRETURN;

/**
 * 해당 클래스는 Instrument Transformer 구현체로 사용할 것.
 * TreeAPITransformer
 */
public class BasicRemoveMethod {

    public static ClassNode removeMethod(ClassNode cn, String methodName, String methodDesc) {
        cn.methods.removeIf(methodNode -> methodNode.name.equals(methodName) && methodNode.desc.equals(methodDesc));
        return cn;
    }
}
