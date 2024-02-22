package org.agent.asm_tree_api.testcode.insnlist;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.*;

/**
 * 해당 클래스는 Instrument Transformer 구현체로 사용할 것.
 * TreeAPITransformer
 */
public class BasicModify {

    //메서드 이름 변경
    public static void ModifyMethodName(ClassNode cn, String methodName, String methodDesc, String modifyName) {
        Iterator<MethodNode> iterator = cn.methods.iterator();
        MethodNode modifiedMethod = null;
        boolean modified = false;
        while (iterator.hasNext()) {
            MethodNode mn = iterator.next(); //MethodNode

            if(methodName.equals(mn.name) && methodDesc.equals(mn.desc)) {
                modified = true;
                modifiedMethod = mn;
                iterator.remove();      //만약 이름과 description이 같다면 Iterator 제거
                break; //순회 종료
            }
        }

        if(modified) {
            modifiedMethod.name = modifyName; //변경
            cn.methods.add(modifiedMethod);
        }
    }

    public static void ModifyFieldName(ClassNode cn, String fieldName, String modifyName) {
        Iterator<FieldNode> iterator = cn.fields.iterator();
        FieldNode modifiedField = null;
        boolean modified = false;
        while (iterator.hasNext()) {
            FieldNode fn = iterator.next();

            if(fieldName.equals(fn.name)) {
                modified = true;
                modifiedField = fn;
                iterator.remove();
                break;
            }
        }

        if(modified) {
            modifiedField.name = modifyName; //변경
            cn.fields.add(modifiedField);
        }

    }
}
