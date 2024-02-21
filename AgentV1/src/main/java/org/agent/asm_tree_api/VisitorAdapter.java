package org.agent.asm_tree_api;

import org.agent.asm_tree_api.testcode.*;
import org.objectweb.asm.tree.*;

/**
 * TreeAPI의 testcode 클래스를 만들어주는 Visitor 클래스
 * Premain에서 Reflection 사용이 제한되므로, 대체적으로 생성하였음.
 */
public class VisitorAdapter {

    public static ClassNode setVisitor(String testcode, String className) {
        switch (testcode) {
            case "BasicClass" -> {
                return BasicClass.getClassNode(className);
            }
            case "BasicClassLocalValue" -> {
                return BasicClassLocalValue.getClassNode(className);
            }
            default -> throw new IllegalArgumentException("[Tree-Setvisitor]Unsupported method name: " + testcode);
        }
    }

}
