//package org.agent;
//
//import org.agent.asm_tree_api.testcode.control_statement.ForExampleTree;
//import org.agent.asm_tree_api.testcode.control_statement.IfExampleTree;
//import org.agent.asm_tree_api.testcode.control_statement.LocalValueExample;
//import org.agent.asm_tree_api.testcode.control_statement.WhileExampleTree;
//import org.agent.util.CodePrinter;
//import org.objectweb.asm.ClassReader;
//import org.objectweb.asm.ClassVisitor;
//import org.objectweb.asm.ClassWriter;
//import org.objectweb.asm.util.TraceClassVisitor;
//import org.objectweb.asm.tree.*;
//import static org.objectweb.asm.Opcodes.*;
//
//import java.io.PrintWriter;
//
//
////for test, 실제로는 사용 불가
//public class Main {
//
//    public static void main(String[] args) {
//        //ClassNode
//        ClassNode cn = new ClassNode();
//        cn.version = V21; //java 21
//        cn.access = ACC_PRIVATE;
//        cn.name = "Class0223";
//        cn.superName = "java/lang/Object"; //객체 타입
//
//        //Method
//        MethodNode mn = new MethodNode(ACC_PUBLIC, "method", "()I", null, null);
//        InsnList il = new InsnList();
//        il.add(new InsnNode(ICONST_5));
//        il.add(new InsnNode(IRETURN));
//        mn.instructions.add(il);
//        cn.methods.add(mn);
//        cn.fields.add(new FieldNode(ACC_PRIVATE, "field", "F", null, -1F));
//
//        //Logic
//        IfExampleTree.run(cn);
//        ForExampleTree.run(cn);
//        LocalValueExample.run(cn);
//        WhileExampleTree.run(cn);
//
//        //Accept
//        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
//        TraceClassVisitor tcv = new TraceClassVisitor(cw, new PrintWriter(System.out));
//        cn.accept(tcv);
//
//        CodePrinter.printClass(cw.toByteArray(), "TEMP", true);
//        System.out.println(cw.toByteArray());
//    }
//}
