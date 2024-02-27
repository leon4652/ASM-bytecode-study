package org.sch.asm_tree_api;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * HikariProxyPreparedStatement 객체를 main 구문 가장 위로 로드
 */
public class AddAdditionalObjectToMain {
    static public void apply(ClassNode cn) {
        Iterator<MethodNode> methodNodeIterator = cn.methods.iterator();
        while (methodNodeIterator.hasNext()) {
            MethodNode methodNode = methodNodeIterator.next();
            System.out.println("이름 : "  + methodNode.name);
            System.out.println("desc : "  + methodNode.desc);
            System.out.println("sig : "  + methodNode.signature);
        }
    }
}
