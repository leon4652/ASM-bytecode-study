package org.sch.classtransformer;

import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.sch.HikariAgent;
import org.sch.asm_tree_api.ClassNodeTransformationStrategy;
import org.sch.asm_tree_api.code.AddAdditionalObjectToMain;
import org.sch.asm_tree_api.code.CheckSpringApplicationAnnotation;
import org.sch.util.CodePrinter;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

@Slf4j
public class TestClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

        if(HikariAgent.firstSystemClassLoad && loader.getClass().getName().contains("AppClassLoader")) { //spring Main, 시스템 클래스로더가 가장 먼저 접근하는 클래스
            //시스템 클래스로더가 읽은 파일 중 @SpringBootApplication를 가진 파일(목표)인지 스캔
            if(CheckSpringApplicationAnnotation.check(classfileBuffer)) {
                log.info("[CheckSpringApplicationAnnotation]@SpringBootApplication 확인 : {}",className);
                HikariAgent.firstSystemClassLoad = false;
                HikariAgent.successBuild = true;
                //시스템 클래스로더에서 먼저 부트하고자 하는 클래스 파일들을 변조하여 저장한다.
                return BuildClassNode(classfileBuffer, "AddAdditionalObjectToMain");
            }
        }
        return classfileBuffer;
    }

    /**
     * 이 바이트코드(.class)를 특정 전략에 맞게(code) 조작 후 반환
     * @param classfileBuffer
     * @param code
     * @return
     */
    private byte[] BuildClassNode(byte[] classfileBuffer, String code) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);

        //여기서 code명에 따라 ClassNode 내부 로직을 변경하는 Class들을 호출한다.
        ClassNodeTransformationStrategy.select(cn, code);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cn.accept(cw);

        byte[] result = cw.toByteArray();
        CodePrinter.printClass(result, code, true); //PRINT
        return result;
    }
}


