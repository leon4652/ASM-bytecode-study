//package org.agent.code.asm;
//
//import lombok.extern.slf4j.Slf4j;
//import org.agent.code.asm.CodePrinter;
//import org.agent.code.asm.VisitorAdapter;
//import org.objectweb.asm.ClassReader;
//import org.objectweb.asm.ClassVisitor;
//import org.objectweb.asm.ClassWriter;
//import org.objectweb.asm.tree.ClassNode;
//import org.objectweb.asm.tree.FieldNode;
//import org.objectweb.asm.tree.MethodNode;
//
//import java.io.IOException;
//
//import static org.objectweb.asm.Opcodes.*;
//
//
///**
// * ASM Library 사용한 TestCode 실행 클래스
// * 클래스의 ByteCode 를 읽고 변형하며 조작한다.
// */
//@Slf4j
//public class AsmCodeFactoryTreeAPI {
//
//    public static byte[] doMethod(String name, boolean isPrint) {
//        ClassReader classReader = new ClassReader(new byte[]);
//        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
////        ClassWriter classWriter = new ClassWriter(classReader, 0); //스택과 로컬 변수(visitMaxs) 직접 계산
//
//        try {
//            ClassNode cn = VisitorAdapter.setClassNode(classWriter); // testClass 명에 따른 ClassVisitor 생성
//            cn.accept(classWriter); //ClassNode의 정보를 ClassWriter에 적용하여 바이트코드로 변환(accept를 통해 writer가 확인)
//
//            if(isPrint) {
//                CodePrinter.printClass(classWriter.toByteArray(), name); //출력
//                log.warn("[Print]{} 코드 출력 완료", name);
//            }
//        } catch (IOException e) {
//            log.warn("doMethod ERR[IOException : PRINT] : {}", e.getMessage());
//        } catch (Exception e) {
//            log.warn("doMethod ERR : {}", e.getMessage());
//        }
//        return classWriter.toByteArray();
//    }
//}
