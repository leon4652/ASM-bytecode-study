package org.sch.asm_tree_api.code;

import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.ListIterator;

import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

/**
 * public static void main(String[] args)를 찾고, 이 메서드의 가장 상단에 특정 객체를 생성한다.
 * HikariProxyPreparedStatement 객체를 main 구문 가장 위로 로드
 */
public class AddAdditionalObjectToMain {
    static public void apply(ClassNode cn) {
        Iterator<MethodNode> methodNodeIterator = cn.methods.iterator();
        while (methodNodeIterator.hasNext()) {
            MethodNode methodNode = methodNodeIterator.next();
            //name == main && desc == ([Ljava/lang/String;)V && sig == null
            if(methodNode.name.equals("main") && methodNode.desc.equals("([Ljava/lang/String;)V")) {
                InsnList il = new InsnList();
                il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
                il.add(new LdcInsnNode("생성자 생성됨."));
                il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));
                methodNode.instructions.insert(il); //return보다 앞서서 메서드 맨 위에 지시문 추가.
                return;
            }
        }
    }
}
