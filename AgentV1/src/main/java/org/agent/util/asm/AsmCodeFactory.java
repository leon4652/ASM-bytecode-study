package org.agent.util.asm;

import lombok.extern.slf4j.Slf4j;
import org.agent.util.asm.testcode.*;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * ASM Library 사용한 TestCode 실행 클래스
 * 클래스의 ByteCode 를 읽고 변형하며 조작한다.
 */
@Slf4j
public class AsmCodeFactory {

    public static String methodName;

    public static byte[] doMethod(byte[] classfileBuffer) {
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        try {
            ClassVisitor classVisitor = setVisitor(classWriter); // method 명에 따른 ClassVisitor 생성
            classReader.accept(classVisitor, 0); //ClassVisitor 에게 정보 부여
            printClass(classWriter.toByteArray()); //class 파일 출력
            log.info("done print");
        } catch (Exception e) {
            System.out.println("try catch Err 발생 : " + e.getMessage());
        }
        return classWriter.toByteArray();
    }


    /**
     *   바이트코드 조작 후 Class 파일 출력 로직
     */
    public static void printClass(byte[] bytecodes) throws IOException {
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        String fileName = "printer " + LocalDateTime.now().format(formattedDate) + ".class";
        String filePath = System.getProperty("user.dir") + File.separator + "byteLog" + File.separator + fileName;    //path
        FileOutputStream out = new FileOutputStream(filePath);      //stream

        out.write(bytecodes);
        out.close();
    }

    /**
     *  method 명에 따른 ClassVisitor 생성
     *  /asm/testcode 내부의 visitor를 return
     * @param classWriter
     * @return classVisitor
     */
    private static ClassVisitor setVisitor(ClassWriter classWriter) {
        switch (methodName) {
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
            default -> throw new IllegalArgumentException("Unsupported method name: " + methodName);
        }
    }

}
