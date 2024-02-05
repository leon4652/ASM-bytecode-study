package org.agent.util.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;

import static org.objectweb.asm.Opcodes.ASM9;


/**
 * ASM Library 사용.
 * 이 클래스의 Byte코드를 읽는다.
 *
 */
public class ReadClass {

//
//    //조금 더 쉬운 버전
//    public static byte[] addLogging(byte[] classfileBuffer) {
//
//        ClassReader classReader = new ClassReader(classfileBuffer); // 클래스 파일 읽기
//        // 클래스 파일 쓰기, COMPUTE_MAXS와 COMPUTE_FRAMES 옵션으로 메서드의 최대 스택 크기와 로컬 변수를 자동 계산
//        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
//
//        // Classvisitor 내부에 MethodVisitor가 있으며, 이를 통해 write를 제어
//        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM9, classWriter) {
//            @Override
//            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//                // 기존 MethodVisitor 얻기
//                MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
//
//                // MethodVisitor를 래핑하여 메서드 시작 부분에 로그를 추가하는 AdviceAdapter 사용
//                return new AdviceAdapter(Opcodes.ASM9, mv, access, name, desc) {
//                    @Override
//                    protected void onMethodEnter() {
//                        // 메서드 시작 시 로그 출력 코드 삽입
//                        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//                        mv.visitLdcInsn("Entering method: " + name);
//                        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//                    }
//                };
//            }
//        };
//
//        // ClassReader로부터 클래스 파일 읽기 시작, ClassVisitor를 통해 변형 로직 적용
//        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
//
//        System.out.println("addLogging");
//        // 변경된 바이트코드를 byte 배열로 반환
//        return classWriter.toByteArray();
//    }
//
//    //Adapter 사용 버전
//    public static byte[] read(byte[] classfileBuffer) {
//
//        //ClassReader는 주어진 바이트 배열(classfileBuffer)에서 클래스 정보를 읽기 위한 객체
//        ClassReader classReader = new ClassReader(classfileBuffer);
//
//        //읽은 클래스 정보에 기반하여 새로운 바이트코드를 생성하거나 기존 바이트코드를 수정하기 위한 객체
//        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
//
//        /*
//        클래스의 구조(예: 클래스 자체, 메서드, 필드 등)를 순회하면서 필요한 작업을 수행할 수 있는 추상 클래스
//        실사용시에는 visitMethod 등을 오버라이드해서 사용해야 한다.
//        classWriter가 들어간 것 역시 이벤트 발생 시 작성해야 하기 때문.
//        */
//        ClassVisitor classVisitor = new AddLoggingClassAdapter(classWriter);
//
//        //실제 visitor 구현체의 예시 : AddLoggingClassAdapter를 사용하여 클래스 방문 시작
//        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
//
//        //ClassWriter로 조작된 바이트코드를 반환
//        return classWriter.toByteArray();
//    }

    //빌드 테스트용
    public static byte[] simpleTest(byte[] classfileBuffer) {
        System.out.println("simpleTest!!!!");
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        try {
            // TestVisitor 생성자에 classWriter를 인자로 전달
            TestVisitor classVisitor = new TestVisitor(classWriter);

            classReader.accept(classVisitor, 0);

        } catch (Exception e) {
            System.out.println("TRY-CATCH ERR");
        }

        System.out.println("END!!!!");
        return classWriter.toByteArray();
    }
}
