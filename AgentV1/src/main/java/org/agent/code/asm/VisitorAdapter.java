package org.agent.code.asm;

import org.agent.code.asm.testcode.chap03.*;
import org.agent.code.asm.testcode.chap1_2.*;
import org.agent.code.asm.testcode.hikari.HikariAdd;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import static org.objectweb.asm.Opcodes.*;

/**
 *  For AsmCodeFactory
 *  Class 명에 따른 ClassVisitor 생성
 *  testcode 내부의 visitor 구현체를 생성해서 return한다.
 *  이를 통해 원하는 변조 로직을 가진 구현체를 return할 수 있음.
 * @param classWriter
 * @return classVisitor
 */
public class VisitorAdapter {

    public static ClassVisitor setVisitor(ClassWriter classWriter, String testCode) {
        switch (testCode) {
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
            case "VisitField" -> {
                return new VisitField(classWriter, "myStr");
            }
            case "HikariAdd" -> {
                return new HikariAdd(classWriter);
            }
            case "GetF" -> {
                return new GetF(classWriter);
            }
            case "SetF" -> {
                return new SetF(classWriter);
            }
            case "StatelessTransformationsExample" -> {
                return new StatelessTransformationsExample(classWriter);
            }
            case "BasicExample" -> {
                return new BasicExample(classWriter);
            }
            case "AddTimerAdapter" -> {
                return new AddTimerAdapter(classWriter);
            }
            default -> throw new IllegalArgumentException("Unsupported method name: " + testCode);
        }
    }

    public static ClassNode setClassNode(ClassWriter classWriter) {

        ClassNode cn = new ClassNode();
        cn.version = V21;
        cn.access = ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE;
        cn.name = "com/dummy/jdbcserver/example_asm/chap7/FirstTest";
        cn.superName = "java/lang/Object";
//        cn.interfaces.add(""); //implements
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC,
                "LESS", "I", null, -1));
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC,
                "EQUAL", "I", null, 0));
        cn.fields.add(new FieldNode(ACC_PUBLIC + ACC_FINAL + ACC_STATIC,
                "GREATER", "I", null, 1));
        cn.methods.add(new MethodNode(ACC_PUBLIC + ACC_ABSTRACT,
                "compareTo", "(Ljava/lang/Object;)I", null, null));

        return cn;
    }
}
