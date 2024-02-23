package org.agent.asm_core_api;

import lombok.extern.slf4j.Slf4j;
import org.agent.util.CodePrinter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;


/**
 * ASM Library 사용한 TestCode 실행 클래스
 * 클래스의 ByteCode 를 읽고 변형하며 조작한다.
 */
@Slf4j
public class AsmCodeFactory {
    public static byte[] doMethod(byte[] classfileBuffer, String testCode, boolean isPrint) {
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
//        ClassWriter classWriter = new ClassWriter(classReader, 0); //스택과 로컬 변수(visitMaxs) 직접 계산

        ClassVisitor classVisitor = VisitorAdapter.setVisitor(classWriter, testCode); // testClass 명에 따른 ClassVisitor 생성
        classReader.accept(classVisitor, 0); //ClassVisitor 에게 정보 부여
        if (isPrint) {
            CodePrinter.printClass(classWriter.toByteArray(), testCode, true); //출력
            log.warn("[Print]{} 코드 출력 완료", testCode);
        }

        return classWriter.toByteArray();
    }
}
