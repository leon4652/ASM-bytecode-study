package org.agent.asm_tree_api.testcode.chap06;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;

public class RemoveMethodTransformer extends ClassTransformer {
    private String methodName;
    private String methodDesc;
    public RemoveMethodTransformer(String methodName, String methodDesc, ClassTransformer ct) {
        super(ct);
        this.methodName = methodName;
        this.methodDesc = methodDesc;
    }

    @Override
    public void transform(ClassNode cn) {
        Iterator<MethodNode> i = cn.methods.iterator();
        while (i.hasNext()) { //Iterator를 통해 다음 요소가 없을 때까지 순회
            MethodNode mn = i.next(); //MethodNode
            if(methodName.equals(mn.name) && methodDesc.equals(mn.desc)) {
                i.remove(); //만약 이름과 description이 같다면 제거
            }
        }
        super.transform(cn); //원본 ClassNode 호출
    }

}
