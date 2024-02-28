package org.sch.asm_tree_api.code;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

/**
 * Class 파일의 Annotation이 무엇인지를 확인한다. (@SpringAnntation을 탐색한다.)
 */
public class CheckSpringApplicationAnnotation {

    public static boolean check(byte[] classfileBuffer) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);

        // 클래스에 달려있는 어노테이션 확인
        if (cn.visibleAnnotations != null) { // null 체크
            for (AnnotationNode an : cn.visibleAnnotations) {
                if (an.desc.equals("Lorg/springframework/boot/autoconfigure/SpringBootApplication;")) {
                    return true; // 원하는 어노테이션을 찾았다면 true 반환
                }
            }
        }

        return false; //결과값 출력
    }
}
