package org.sch.asm_tree_api;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;

/**
 * Class 파일의 Annotation이 무엇인지를 확인한다. (@SpringAnntation을 탐색한다.)
 */
public class CheckSpringApplicationAnnotation {


    public static boolean check(byte[] classfileBuffer) {
        boolean haveAnnotation = false;
        ClassReader cr = new ClassReader(classfileBuffer);
        cr.accept(new CheckAnnotationVisitor(), 0);

        return true;
    }
}

/**
 * CORE API : Class Annotation Check
 */
class CheckAnnotationVisitor extends ClassVisitor {
    public CheckAnnotationVisitor() {
        super(Opcodes.ASM9);
        System.out.println("생성자!!!!!!");
    }

    //각 메서드 방문시 호출
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        System.out.println("method : " + name);
        return mv; // 변형된 MethodVisitor 반환
    }

}