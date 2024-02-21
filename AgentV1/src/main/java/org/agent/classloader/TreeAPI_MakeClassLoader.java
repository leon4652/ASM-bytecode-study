package org.agent.classloader;

import lombok.extern.slf4j.Slf4j;
import org.agent.asm_tree_api.VisitorAdapter;
import org.agent.asm_tree_api.testcode.BasicClass;
import org.agent.util.CodePrinter;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;


/**
 * 기존에 있던 바이트코드를 변조하는 것이 아닌 생성하는 경우,
 '컴파일 - 런타임' 사이에 동적으로 Class 파일을 생성해서 적용하려면

 1. 바이트코드 생성(만들 Class파일)
 2. 커스텀 클래스로더 호출(지금 이 파일)
 3. 이 클래스로더가 1의 바이트코드를 읽어서 return
 4. runtime data area에 3으로 인해 만들어진 Class가 등록된다

 의 과정을 실행해야 한다.
 */
@Slf4j
public class TreeAPI_MakeClassLoader extends ClassLoader {

    /**
     *
     * @param testcode : testcode 패키지 안 실행할 ClassNode명
     * @param className : 만들어질 클래스 이름
     * @param isPrint : 출력 유무
     * @param autoCompute : 스택과 로컬 변수(visitMaxs) 자동 계산 유무
     * @return
     */
    public Class<?> defineClass(String testcode, String className, boolean isPrint, boolean autoCompute) {
        //1. ClassWriter 생성 및 설정
        ClassWriter classWriter;
        if(autoCompute) { //stack frame 크기 자동 계산 유무
            classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        } else {
            classWriter = new ClassWriter(0);
        }

        //2. ClassNode를 통한 변조 진행
        try {
            ClassNode cn = VisitorAdapter.setVisitor(testcode, className); //testcode를 통해 입력값에 맞는 classNode 호출
            cn.accept(classWriter);
            if(isPrint) {
                CodePrinter.printClass(classWriter.toByteArray(), className); //출력
                log.warn("[Print]{} 코드 출력 완료", className);
            }
        } catch (IOException e) {
            log.warn("[defineClass][IOException : PRINT] : {}", e.getMessage());
        }
        byte[] bytecodes = classWriter.toByteArray(); //변경 사항(생성) 저장
        return super.defineClass(className, bytecodes, 0, bytecodes.length);
    }

}
