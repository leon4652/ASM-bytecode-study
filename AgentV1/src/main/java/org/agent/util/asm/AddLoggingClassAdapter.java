package org.agent.util.asm;

import org.objectweb.asm.*;

public class AddLoggingClassAdapter extends ClassVisitor {
    public AddLoggingClassAdapter(ClassVisitor cv) {
        super(Opcodes.ASM9, cv);
    }

    /**
        visitMethod의 Override 구현체.
        각 메서드 방문시마다 AddLoggingMethodAdapter를 반환하게 된다.

     파라미터의 의미는 다음과 같다.
     1. int access: 메서드의 접근 지시자(access modifiers).
     메서드가 public, private, protected, static 등 어떤 접근 수준이며 어떤 속성(예: final, synchronized)인지 나타내는 비트 필드이다.
     2. String name: 방문하고 있는 메서드의 이름
     3. String desc: 메서드의 서술자(descriptor)로, 메서드의 파라미터 타입과 반환 타입을 나타내는 문자열. ex : (Ljava/lang/String;I)V
     4. String signature: 제네릭 타입을 사용하는 메서드의 경우, 메서드의 서명(signature)
     5. String[] exceptions: 메서드가 던질 수 있는 예외들의 내부 이름
     이러한 방식으로 각 메서드를 방문할 때마다 메서드에 대한 정보가 visitMethod에 전달되고, 이를 기반으로 메서드의 바이트코드를 조작
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        //MethodVisitor 선언 후 Adapter Return
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return new AddLoggingMethodAdapter(Opcodes.ASM9, mv, access, name, desc);
    }

    static class AddLoggingMethodAdapter extends MethodVisitor {
        private String methodName;

        public AddLoggingMethodAdapter(int api, MethodVisitor mv, int access, String name, String desc) {
            super(api, mv);
            this.methodName = name;
        }

        @Override
        public void visitCode() {
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("Entering method: " + methodName);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            super.visitCode();
        }
    }
}
