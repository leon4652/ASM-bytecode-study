package org.sch.asm_tree_api.code.hikari;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;

/**
 * ProxyConnection 객체의 'statement'가 들어가는 객체를 ProxyFactory에 넘겨주는 메서드들 로깅
 */
public class AddLoggerProxyConnection_statement {
    public static void apply(ClassNode cn) {
        System.out.println("TRIGGGGGGGGGG");
        for (MethodNode mn : cn.methods) {
            System.out.println("TRIG : " + mn.name);
        }
    }
}
