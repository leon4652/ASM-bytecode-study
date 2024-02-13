package org.agent.util.asm;

import lombok.extern.slf4j.Slf4j;
import org.agent.util.asm.testcode.chap03.GetF;
import org.agent.util.asm.testcode.chap03.SetF;
import org.agent.util.asm.testcode.chap1_2.*;
import org.agent.util.asm.testcode.hikari.HikariAdd;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;


/**
 * ASM Library 사용한 TestCode 실행 클래스
 * 클래스의 ByteCode 를 읽고 변형하며 조작한다.
 */
@Slf4j
public class AsmCodeFactory {

    public static String testCode;

    public static byte[] doMethod(byte[] classfileBuffer, boolean isPrint) {
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        try {
            ClassVisitor classVisitor = setVisitor(classWriter); // testClass 명에 따른 ClassVisitor 생성
            classReader.accept(classVisitor, 0); //ClassVisitor 에게 정보 부여
            if(isPrint) {
                CodePrinter.printClass(classWriter.toByteArray()); //출력
                log.warn("[Print]{} 코드 출력 완료", testCode);
            }
        } catch (Exception e) {
            log.warn("doMethod ERR : {}", e.getMessage());
        }
        return classWriter.toByteArray();
    }

    /**
     *  Class 명에 따른 ClassVisitor 생성
     *  /asm/testcode 내부의 visitor를 return
     * @param classWriter
     * @return classVisitor
     */
    private static ClassVisitor setVisitor(ClassWriter classWriter) {
        switch (testCode) {
            case "TestVisitor" -> {
                return new TestVisitor(classWriter);
            }
            case "TestMethodVisitor" -> {
                return new TestMethodVisitor(classWriter);
            }
            case "TestRefactorCodeVisitor" -> {
                return new TestRefactorCodeVisitor(classWriter);
            }
            case "TestRemoveCodeVisitor" -> {
                return new TestRemoveCodeVisitor(classWriter);
            }
            case "PreparedStatementModifyVisitor" -> {
                return new PreparedStatementModifyVisitor(classWriter);
            }
            case "DataSourceVisitor" -> {
                return new DataSourceVisitor(classWriter);
            }
            case "VisitField" -> {
                return new VisitField(classWriter, "myStr");
            }
            case "HikariAdd" -> {
                return new HikariAdd(classWriter);
            }
            case "GetF" -> {
                return new GetF(classWriter);
            }
            case "SetF" -> {
                return new SetF(classWriter);
            }
            default -> throw new IllegalArgumentException("Unsupported method name: " + testCode);
        }
    }

}
