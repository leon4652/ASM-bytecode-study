package org.agent.transform;

import lombok.extern.slf4j.Slf4j;
import org.agent.asm_core_api.AsmCodeFactory;
import org.agent.asm_tree_api.testcode.BasicClass;
import org.agent.asm_tree_api.testcode.BasicRemoveField;
import org.agent.asm_tree_api.testcode.BasicRemoveMethod;
import org.agent.util.CodePrinter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;


@Slf4j
public class TreeAPITransformer implements ClassFileTransformer {


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        String containsName = "BasicExample"; //스캔할 클래스명

        if (className.contains(containsName)) {
            log.warn("[TRANSFORM] Find ClassName : {}", containsName);
            byte[] modifiedCode = init(classfileBuffer); //here to modify
            CodePrinter.printClass(modifiedCode, containsName); //print
            return modifiedCode;
        } else {
            return classfileBuffer;
        }

    }

    public byte[] init(byte[] bytes) {
        ClassReader classReader = new ClassReader(bytes);
        ClassNode cn = new ClassNode();
        classReader.accept(cn, 0); //바이트코드 제공
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cn = BasicRemoveMethod.removeMethod(cn, "main", "()V"); //<< ClassNode testcode 적용점
        cn = BasicRemoveField.removeField(cn, "staticVal"); //<< ClassNode testcode 적용점
        cn.accept(cw); //변조된 classNode 바이트코드 제공
        return cw.toByteArray();
    }
}