//package org.agent.util.asm;
//
//import org.objectweb.asm.*;
//
//public class AddLoggingClassAdapter extends ClassVisitor {
//    public AddLoggingClassAdapter(ClassVisitor cv) {
//        super(Opcodes.ASM9, cv);
//    }
//
//    /**
//     - visitMethod 메서드는 클래스의 메서드를 방문할 때마다 호출된다.
//     - 이 메서드는 메서드의 메타데이터(접근 지시자, 이름, 설명자, 시그니처, 예외 등)를 포함한 정보와 함께 호출된다.
//     - visitMethod의 주요 목적은 방문 중인 메서드에 대한 정보를 기반으로 특정 작업을 수행하기 위한 MethodVisitor 인스턴스를 생성하고 반환하는 것
//     즉, visitMethod는 메서드의 구조를 정의하며, 반환하는 MethodVisitor 인스턴스를 통해 해당 메서드의 바이트코드 내부에 추가적인 조작이나 분석이 가능
//
//     < 파라미터의 의미 ></>
//     1. int access: 메서드의 접근 지시자(access modifiers).
//     메서드가 public, private, protected, static 등 어떤 접근 수준이며 어떤 속성(예: final, synchronized)인지 나타내는 비트 필드이다.
//     2. String name: 방문하고 있는 메서드의 이름
//     3. String desc: 메서드의 서술자(descriptor)로, 메서드의 파라미터 타입과 반환 타입을 나타내는 문자열. ex : (Ljava/lang/String;I)V
//     4. String signature: 제네릭 타입을 사용하는 메서드의 경우, 메서드의 서명(signature)
//     5. String[] exceptions: 메서드가 던질 수 있는 예외들의 내부 이름
//     이러한 방식으로 각 메서드를 방문할 때마다 메서드에 대한 정보가 visitMethod에 전달되고, 이를 기반으로 메서드의 바이트코드를 조작
//     */
//    @Override
//    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//        //MethodVisitor 선언 후 Adapter Return
//        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
//        return new AddLoggingMethodAdapter(Opcodes.ASM9, mv, access, name, desc);
//    }
//
//    static class AddLoggingMethodAdapter extends MethodVisitor {
//        private String methodName;
//
//        public AddLoggingMethodAdapter(int api, MethodVisitor mv, int access, String name, String desc) {
//            super(api, mv);
//            this.methodName = name;
//        }
//
//        /**
//         - visitCode 메서드는 MethodVisitor에서 오버라이드되며, 메서드의 바이트코드 순회가 시작될 때 호출
//         - 이는 메서드의 실제 바이트코드(명령어)를 방문하기 직전의 단계에 해당(진입점)
//         - 주요 목적 : 바이트코드 조작을 시작하기 전에 필요한 초기화나 준비 작업을 수행하는 것
//         - 실제 본문 코드에서는 '메서드 시작 시 로그를 출력하고자, visitCode 내에서 로그 출력 관련 바이트코드를 삽입' 하였음.
//
//         *
//         * 동작 과정 :
//         * visitFieldInsn : System.out 객체를 스택에 푸시. GETSTATIC은 정적 필드에 접근하는 명령어로, System.out 정적 필드(표준 출력 스트림)를 참조.
//         * visitLdcInsn : 로그 메시지 문자열을 스택에 푸시.
//         * visitMethodInsn : PrintStream.println(String) 메서드를 호출하여 스택에 있는 문자열(로그 메시지)를 출력. INVOKEVIRTUAL은 가상 메서드를 호출하는 명령어.
//         */
//        @Override
//        public void visitCode() {
//            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//            mv.visitLdcInsn("Entering method: " + methodName);
//            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//            super.visitCode();
//        }
//
//
//    }
//}
