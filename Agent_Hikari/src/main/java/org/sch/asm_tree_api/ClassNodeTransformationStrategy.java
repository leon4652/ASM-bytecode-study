package org.sch.asm_tree_api;

import org.objectweb.asm.tree.ClassNode;
import org.sch.asm_tree_api.code.*;

/**
 Code에 따른 ClassNode 변경 로직을 적용한다.
 /code package 내부의 전략을 적용한다.
 */
public class ClassNodeTransformationStrategy {
    public static void select(ClassNode cn, String code) {
        switch (code) {
            case "AddAdditionalObjectToMain" -> {
                AddAdditionalObjectToMain.apply(cn);
            }
            case "AddLogger" -> {
                AddLogger.apply(cn);
            }
            case "AddLoggerTemp" -> {
                AddLoggerTemp.apply(cn);
            }
            default -> throw new IllegalArgumentException("Unsupported name: " + code);
        }
    }
}
