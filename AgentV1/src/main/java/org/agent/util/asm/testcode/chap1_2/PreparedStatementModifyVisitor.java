package org.agent.util.asm.testcode.chap1_2;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 테스트용 ClassVisitor 구현체
 * psmt 변조
 */
public class PreparedStatementModifyVisitor extends ClassVisitor {
    private String currentClassName;

    public PreparedStatementModifyVisitor(ClassVisitor cv) {
        // 부모 클래스의 생성자에 ASM API 버전과 ClassVisitor 인스턴스(ClassWriter)를 전달
        super(Opcodes.ASM9, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        currentClassName = name.replace('/', '.');
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        // execute, executeQuery, executeUpdate 메서드 확인
        if ("execute".equals(name) || "executeQuery".equals(name) || "executeUpdate".equals(name)) {
            System.out.println("check method : " + name);
            return new AddLogVisitor(mv, name, currentClassName);
        }

        return mv; // 기본 MethodVisitor 반환
    }

    //메서드 최상단에 로그 생성
    private static class AddLogVisitor extends MethodVisitor {
        private final String methodName;
        private final String className;

        public AddLogVisitor(MethodVisitor mv, String methodName, String className) {
            super(Opcodes.ASM9, mv);
            this.methodName = methodName;
            this.className = className;
        }

        @Override
        public void visitCode() {
            super.visitCode();

            /*
            System.out.println("Method start: ")
            - Opcodes.GETSTATIC : 정적 필드 접근 / "out"은 접근하려는 필드 / "Ljava/io/PrintStream;"은 필드의 타입
            - mv.visitLdcInsn("Method start: "); - 상수 풀에서 문자열 상수를 로드하는 바이트코드 생성
            - Opcodes.INVOKEVIRTUAL : 가상 메서드 호출 / "java/io/PrintStream" : 메서드가 속한 클래스의 내부 이름 / println : 메서드 / 서술자
             */
            mv.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("Method start : " + methodName + " \t Class name : " + className);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }

        @Override
        public void visitEnd() {
            super.visitEnd();
        }
    }

}


