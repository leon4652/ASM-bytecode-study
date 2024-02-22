package org.agent.asm_tree_api.testcode.chap06;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

/**
 * 체이닝 :
 * 체이닝을 거는 방법 : 다음 ClassTransformer를 ct의 입력값으로 받는다.
 * 생성자에서 super(ct)를 통해 다음 체인 class를 이 객체(ct)에 저장하고, 마지막에 super.transform(cn)으로 저장된 체인을 실행한다.
 */
public class AddFieldTransformer extends ClassTransformer {
    private int fieldAccess; //접근제어자
    private String fieldName; //필드명
    private String fieldDesc; //설명자
    public AddFieldTransformer(int fieldAccess, String fieldName, String fieldDesc, ClassTransformer ct) {
        super(ct); //부모 생성자 호출로 생성할 때 ct값 부모 필드에 저장
        this.fieldAccess = fieldAccess;
        this.fieldName = fieldName;
        this.fieldDesc = fieldDesc;
    }

    @Override
    public void transform(ClassNode cn) {
        boolean isPresent = false;
        //동일 필드 조회 여부
        for (FieldNode fn : cn.fields) { //필드 노드 내부 필드 파라미터 순회
            if (fieldName.equals(fn.name)) {
                isPresent = true;
                break;
            }
        }

        //동일 필드 없다면 추가
        if (!isPresent) {
            cn.fields.add(new FieldNode(fieldAccess, fieldName, fieldDesc, null, null));
        }

        //체이닝 수행
        super.transform(cn); //부모 메서드 호출로 다음 변환기에게 위임(체이닝)
    }
}
