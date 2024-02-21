package org.agent.classloader;

import lombok.extern.slf4j.Slf4j;
import org.agent.asm_tree_api.testcode.BasicClass;
import org.agent.util.CodePrinter;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;

/**
특정 Class의 특정 Method를 제거하는 예제
 */
@Slf4j
public class TreeAPI_RemoveMethodInClassLoader extends ClassLoader {

    /**
     *
     * @param testcode : testcode 패키지 안 실행할 ClassNode명
     * @param isPrint : 출력 유무
     * @param autoCompute : 스택과 로컬 변수(visitMaxs) 자동 계산 유무
     * @return
     */
    public Class<?> defineClass(String testcode, boolean isPrint, boolean autoCompute) {
        //1. ClassWriter 생성 및 설정
        ClassWriter classWriter;
        if(autoCompute) { //stack frame 크기 자동 계산 유무
            classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        } else {
            classWriter = new ClassWriter(0);
        }

        //2. ClassNode를 통한 변조 진행
        try {
            ClassNode cn = setVisitor(testcode, null); //testcode를 통해 입력값에 맞는 classNode 호출
            cn.accept(classWriter);
            if(isPrint) {
                CodePrinter.printClass(classWriter.toByteArray(), testcode); //출력
                log.warn("[Print]{} 코드 출력 완료", testcode);
            }
        } catch (IOException e) {
            log.warn("[defineClass][IOException : PRINT] : {}", e.getMessage());
        }
        byte[] bytecodes = classWriter.toByteArray(); //변경 사항(생성) 저장
        return super.defineClass(testcode, bytecodes, 0, bytecodes.length);
    }

    public ClassNode setVisitor(String testcode, String className) {
        switch (testcode) {
            case "BasicClass" -> {
                return BasicClass.getClassNode(className);
            }

            case "RemoveMethodExample" -> {
                return null; //여기서부터 다시 진행
            }

            default -> throw new IllegalArgumentException("[Tree-Setvisitor]Unsupported method name: " + testcode);
        }
    }
}
