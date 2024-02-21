//package org.agent.classloader;
//
//import lombok.extern.slf4j.Slf4j;
//import org.agent.asm_tree_api.VisitorAdapter;
//import org.agent.util.CodePrinter;
//import org.objectweb.asm.ClassWriter;
//import org.objectweb.asm.MethodVisitor;
//import org.objectweb.asm.Opcodes;
//import org.objectweb.asm.tree.ClassNode;
//
//import java.io.IOException;
//
//import static org.objectweb.asm.Opcodes.GETSTATIC;
//import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
//
///**
// * 기존에 있던 바이트코드를 변조하는 것이 아닌 생성하는 경우,
// '컴파일 - 런타임' 사이에 동적으로 Class 파일을 생성해서 적용하려면
//
// 1. 바이트코드 생성(만들 Class파일)
// 2. 커스텀 클래스로더 호출(지금 이 파일)
// 3. 이 클래스로더가 1의 바이트코드를 읽어서 return
// 4. runtime data area에 3으로 인해 만들어진 Class가 등록된다
//
// 의 과정을 실행해야 한다.
// */
//@Slf4j
//public class MakeClassClassLoaderSAVE extends ClassLoader {
//
//
//    public Class<?> defineClass(String name, boolean isPrint, boolean autoCompute) {
//        //ClassWriter 생성
//        ClassWriter classWriter;
//        if(autoCompute) {
//            classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
//        } else {
//            classWriter = new ClassWriter(0); //스택과 로컬 변수(visitMaxs) 직접 계산
//        }
//
//        //ClassNode를 통한 변조 진행
//        try {
//            ClassNode cn = VisitorAdapter.setClassNode(name); // name에 따른 ClassNode 생성
//            cn.accept(classWriter); //ClassNode의 정보를 ClassWriter에 적용하여 바이트코드로 변환(accept를 통해 writer가 확인)
//
//            //Hotfix : 생성자 추가, cn에서 추가가 안 되어서 임의로 추가하였음.
//            MethodVisitor mv = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
//            mv.visitVarInsn(Opcodes.ALOAD, 0); // this를 로드
//            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false); // 슈퍼 클래스의 생성자 호출
//            //sysout
//            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//            mv.visitLdcInsn("생성자 생성됨.");
//            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//
//            mv.visitInsn(Opcodes.RETURN);
//            mv.visitMaxs(0, 0);
//            mv.visitEnd();
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
//        byte[] bytecodes = classWriter.toByteArray(); //변경 사항(생성) 저장
//        return super.defineClass(name, bytecodes, 0, bytecodes.length);
//    }
//}
