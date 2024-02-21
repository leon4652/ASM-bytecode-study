package org.agent.asm_tree_api.testcode;

import org.objectweb.asm.tree.ClassNode;

/**
 * 해당 클래스는 Instrument Transformer 구현체로 사용할 것.
 * TreeAPITransformer
 */
public class BasicRemoveField {
    public static ClassNode removeField(ClassNode cn, String fieldName) {
        cn.fields.removeIf(fieldNode -> fieldNode.name.equals(fieldName));
        return cn;
    }
}
