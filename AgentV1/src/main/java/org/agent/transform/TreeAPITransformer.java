package org.agent.transform;

import lombok.extern.slf4j.Slf4j;
import org.agent.asm_tree_api.testcode.control_statement.ForExampleTree;
import org.agent.asm_tree_api.testcode.control_statement.IfExampleTree;
import org.agent.util.CodePrinter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.PrintWriter;
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
            byte[] modifiedCode = init(classfileBuffer); //this method 'init' set modify
            CodePrinter.printClass(modifiedCode, containsName, true); //print
            return modifiedCode;
        } else {
            return classfileBuffer;
        }

    }

    public byte[] init(byte[] classfileBuffer) {
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassNode cn = new ClassNode();
        classReader.accept(cn, 0); //바이트코드 제공
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        //바이트코드 변조 로직 적용
//        cn = BasicRemoveMethod.removeMethod(cn, "main", "()V"); //<< ClassNode testcode 적용점
//        cn = BasicRemoveField.removeField(cn, "staticVal"); //<< ClassNode testcode 적용점
//        new TransformerExample(cn).run(); //Chaining Example
//        BasicModify.ModifyMethodName(cn, "main", "()V", "modifyMain");
//        BasicModify.ModifyFieldName(cn, "vSix", "vFiveModify");
        ForExampleTree.run(cn);

        return cw.toByteArray();
    }
}