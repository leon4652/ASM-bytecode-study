package org.sch.classtransformer;

import lombok.extern.slf4j.Slf4j;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.sch.HikariAgent;
import org.sch.asm_tree_api.AddAdditionalObjectToMain;
import org.sch.asm_tree_api.CheckSpringApplicationAnnotation;
import org.sch.util.CodePrinter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

@Slf4j
public class TestClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        //System.out.println(loader.getClass().getName());
        if(HikariAgent.firstSystemClassLoad && loader.getClass().getName().contains("AppClassLoader")) { //spring Main, 시스템 클래스로더가 가장 먼저 접근하는 클래스
            //시스템 클래스로더가 읽은 파일 중 @SpringBootApplication를 가진 파일(목표)인지 스캔
            if(CheckSpringApplicationAnnotation.check(classfileBuffer)) {
                HikariAgent.firstSystemClassLoad = false;
            }

            HikariAgent.successBuild = true;
            System.out.println("!!!!!!!!!!!" + loader + " \t" + className);
            return BuildClassNode(classfileBuffer); //시스템 클래스로더에서 먼저 부트하고자 하는 클래스 파일들을 변조하여 저장한다.
        }

        return classfileBuffer;
    }

    private byte[] BuildClassNode(byte[] classfileBuffer) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassNode cn = new ClassNode();
        cr.accept(cn, 0);

        AddAdditionalObjectToMain.apply(cn);

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cn.accept(cw);

        byte[] result = cw.toByteArray();
        CodePrinter.printClass(result, "test", true); //PRINT
        return result;
    }
}


