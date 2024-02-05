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


    /**
     * 빌드 테스트용, TestVisitor 사용
     */
    public static byte[] simpleTest(byte[] classfileBuffer) {
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        try {
            // TestVisitor 생성자에 classWriter를 인자로 전달
            TestVisitor classVisitor = new TestVisitor(classWriter);
            classReader.accept(classVisitor, 0);

        } catch (Exception e) {
            System.out.println("try catch Err 발생 : " + e.getMessage());
        }
        return classWriter.toByteArray();
    }

    /**
     * TestMethodVisitor 사용
     */
    public static byte[] TestMethodVisitor(byte[] classfileBuffer) {
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        try {
            // TestVisitor 생성자에 classWriter 를 인자로 전달
            TestMethodVisitor classVisitor = new TestMethodVisitor(classWriter);
            classReader.accept(classVisitor, 0);

        } catch (Exception e) {
            System.out.println("try catch Err 발생 : " + e.getMessage());
        }
        return classWriter.toByteArray();
    }

    /**
     * TestRefactorCodeVisitor 사용
     */
    public static byte[] TestRefactorCodeVisitor(byte[] classfileBuffer) {
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        try {
            //생성자에 classWriter 를 인자로 전달
            TestRefactorCodeVisitor classVisitor = new TestRefactorCodeVisitor(classWriter);
            classReader.accept(classVisitor, 0);

        } catch (Exception e) {
            System.out.println("try catch Err 발생 : " + e.getMessage());
        }
        return classWriter.toByteArray();
    }
}
