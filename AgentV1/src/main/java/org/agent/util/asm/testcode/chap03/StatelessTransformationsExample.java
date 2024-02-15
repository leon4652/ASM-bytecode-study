package org.agent.util.asm.testcode.chap03;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import static org.objectweb.asm.Opcodes.*;

/**
 * 3.2.4. Stateless transformations (상태 없는 변환) 예제.
 * 아래의 원문 코드를
 * public class StatelessTransformationsExample {
 * public void m() throws Exception {
 * Thread.sleep(100);
 * }
 * }
 * <p>
 * 이렇게 바꾸고 싶다!
 * <p>
 * public class StatelessTransformationsExample {
 * public static long timer;
 * public void m() throws Exception {
 * timer -= System.currentTimeMillis();
 * Thread.sleep(100);
 * timer += System.currentTimeMillis();
 * }
 * }
 */

public class StatelessTransformationsExample extends ClassVisitor {
    public StatelessTransformationsExample(ClassVisitor classVisitor) {
        super(Opcodes.ASM9, classVisitor);
    }


    @Override
    public void visitEnd() {
        String[] exceptions = {"java/lang/NullPointerException", "java/lang/ClassNotFoundException"};
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "addSysout", "(ILjava/lang/String;Ljava/lang/String;)V", null, exceptions);
        mv.visitCode(); //방문
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitMaxs(5, 5);
        mv.visitInsn(RETURN);
        super.visitEnd();
    }


}