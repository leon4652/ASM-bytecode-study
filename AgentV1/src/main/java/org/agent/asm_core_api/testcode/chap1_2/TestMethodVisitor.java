package org.agent.asm_core_api.testcode.chap1_2;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 테스트용 ClassVisitor 구현체
 * 메서드 구문 파싱 기능 추가
 */
public class TestMethodVisitor extends ClassVisitor {

    public TestMethodVisitor(ClassVisitor cv) {
        // 부모 클래스의 생성자에 ASM API 버전과 ClassVisitor 인스턴스(ClassWriter)를 전달
        super(Opcodes.ASM9, cv);
    }

    //각 메서드 방문시 호출
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        System.out.println("========================= 메서드 정보 파싱 =============================");
        System.out.println("access\t" + access);
        System.out.println("name\t" + name);
        System.out.println("desc\t" + desc);
        System.out.println("signature\t" + signature);
        if (exceptions != null) {
            for (String str : exceptions) {
                System.out.print(str + "\t");
            }
        }

        System.out.println("======================================================");
        System.out.println();

        
        return mv; // 변형된 MethodVisitor 반환
    }

    //ClassReader가 모든 요소를 처리하고 마무리할때 호출
    @Override
    public void visitEnd() {
        System.out.println("visitEnd");
    }
}
