package org.sch.asm_tree_api;

import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.tree.ClassNode;
import org.sch.asm_tree_api.code.*;
import org.sch.asm_tree_api.code.hikari.AddLoggerPSTMT;
import org.sch.asm_tree_api.code.hikari.AddLoggerAllClass;
import org.sch.asm_tree_api.code.hikari.ModifyPreparedStatement;
import org.sch.util.CodePrinter;

/**
 * Code에 따라, 다른 클래스를 선택한다. ClassNode 참조를 통해 ClassNode의 값을 변경한다.
 * '/code' package 내부의 전략을 적용한다.
 * 이는 premain에서 reflection이 사용 불가함에 따라(Method Area에 로드 안되어 있음), 유동적으로 새로운 객체를 넣어주기 위함이다.
 */
@Slf4j
public class ClassNodeTransformationStrategy {
    public static void select(ClassNode cn, String code) {

        switch (code) {
            case "AddAdditionalObjectToMain" -> {
                AddAdditionalObjectToMain.apply(cn);
            }
            case "AddLoggerPSTMT" -> {
                AddLoggerPSTMT.apply(cn);
            }
            case "AddLoggerAllClass" -> {
                AddLoggerAllClass.apply(cn);
            }
            case "ModifyPreparedStatement" -> {
                ModifyPreparedStatement.apply(cn);
            }
            default -> throw new IllegalArgumentException("Unsupported name: " + code);
        }

    }
}
