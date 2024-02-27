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

///**
// * Tree API : Class Annotation Check
// */
//class CheckAnnotationVisitor extends ClassVisitor {
//    public CheckAnnotationVisitor() {
//        super(Opcodes.ASM9);
//    }
//
//    //각 메서드 방문시 호출
//    @Override
//    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
//        System.out.println("method : " + name);
//        return mv; // 변형된 MethodVisitor 반환
//    }
//
//    @Override
//    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
//        if (desc.equals("Lorg/springframework/boot/autoconfigure/SpringBootApplication;")) {
//            CheckSpringApplicationAnnotation.haveAnnotation = true; //발견
//            super.visitEnd(); //더 이상 순회할 필요 없으니 종료
//        }
//        return super.visitAnnotation(desc, visible);
//    }
//
//}