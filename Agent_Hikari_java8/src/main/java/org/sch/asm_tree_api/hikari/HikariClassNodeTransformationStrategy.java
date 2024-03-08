package org.sch.asm_tree_api.hikari;


import org.objectweb.asm.tree.ClassNode;
import org.sch.asm_tree_api.hikari.main.*;
import org.sch.asm_tree_api.hikari.legacy.AddAdditionalObjectToMain;

/**
 * Code에 따라, 다른 클래스를 선택한다. ClassNode 참조를 통해 ClassNode의 값을 변경한다.
 * '/hikari' package 내부의 전략을 적용한다.
 * 이는 premain에서 reflection이 사용 불가함에 따라(Method Area에 로드 안되어 있음), 유동적으로 새로운 객체를 넣어주기 위함이다.
 */

public class HikariClassNodeTransformationStrategy {
    public static void select(ClassNode cn, String code) {

        switch (code) {
            case "AddAdditionalObjectToMain":
            {
                AddAdditionalObjectToMain.apply(cn);
                break;
            }
            case "AddLoggerProxyPreparedStatement":{
                AddLoggerProxyPreparedStatement.apply(cn);
                break;
            }
            case "AddLoggerAllClass" : {
                AddLoggerAllClass.apply(cn);
                break;
            }
            case "AddLoggerProxyConnection" : {
                AddLoggerProxyConnection.apply(cn);
                break;
            }
            case "AddLoggerMethodNameAndInputParam" : {
                AddLoggerMethodNameAndInputParam.apply(cn);
                break;
            }
            default : throw new IllegalArgumentException("Unsupported name: " + code);
        }

    }
}
