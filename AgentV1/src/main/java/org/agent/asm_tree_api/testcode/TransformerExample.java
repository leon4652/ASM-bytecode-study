package org.agent.asm_tree_api.testcode;

import org.agent.asm_tree_api.testcode.chap06.AddFieldTransformer;
import org.agent.asm_tree_api.testcode.chap06.ClassTransformer;
import org.agent.asm_tree_api.testcode.chap06.RemoveMethodTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

/**
 * ClassTransformer 사용 예시를 보여주는 Class
 */
public class TransformerExample {
    ClassNode cn;

    public TransformerExample(ClassNode cn) {
        this.cn = cn;
    }

    public void run() {

        ClassTransformer chain = new ClassTransformer(
                new AddFieldTransformer(ACC_PUBLIC, "newField", "Ljava/lang/String;",
                        new RemoveMethodTransformer("targetRemoveMethod", "()V", null)
                )
                //..이렇게 체이닝으로 다음 작업물 추가
        );

        chain.transform(cn); //실행
    }
}
