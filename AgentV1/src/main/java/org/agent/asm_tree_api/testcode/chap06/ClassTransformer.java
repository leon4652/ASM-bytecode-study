package org.agent.asm_tree_api.testcode.chap06;

import org.objectweb.asm.tree.ClassNode;

public class ClassTransformer {
    protected ClassTransformer ct;
    public ClassTransformer(ClassTransformer ct) {
        this.ct = ct;
    }

    //변환 작업을 체이닝하기 위한 수단
    //ClassTransformer를 체이닝하여, 여러 로직을 반복적으로 실행하는 변환 과정을 수행한다.
    public void transform(ClassNode cn) {
        if(ct != null) {
            ct.transform(cn);
        }
    }

}
