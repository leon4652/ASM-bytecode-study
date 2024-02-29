import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.sch.util.CodePrinter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;


public class main {
    //컴파일하지 않고 직접 만드는 테스팅보드
    public static void main(String[] args) throws IOException {

        //------Build--------
        ClassNode cn = tempMakeClass();

        //------APPLY--------
        apply(cn);

        //-------Print--------
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cn.accept(cw);
        CodePrinter.printClass(cw.toByteArray(), "TEST", true);
    }

    public static void apply(ClassNode cn) {
        Iterator<MethodNode> iterator = cn.methods.iterator();
        while (iterator.hasNext()) {
            MethodNode methodNode = iterator.next();
            InsnList il = new InsnList();
            // 파라미터 타입 가져오기
            Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc);
            System.out.println(Arrays.toString(argumentTypes));
            System.out.println(methodNode.name + " : 파라미터개수 : " + argumentTypes.length);



            //Static 메서드 체크
            boolean isStatic = (methodNode.access & ACC_STATIC) != 0;
            
            //static이면 지역배열 0번부터 스택
            //타입 별로 입력 파라미터가 다르니, argumentTypes을 활용

//            for (; idx <= inputParmLength; idx++) {
//                il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
//
//                Type paramType = argumentTypes[i];
//                System.out.println(paramType);
////                int loadOpcode = paramType.getOpcode(ILOAD); // 파라미터 타입에 맞는 로드 명령
////
////                // 해당 타입에 맞는 로드 명령 추가
////                il.add(new VarInsnNode(loadOpcode, idx + i));
////
////                // 적절한 println 메서드 호출을 위한 타입 설명자
////                String descriptor = "(" + paramType.getDescriptor() + ")V";
////                il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", descriptor, false));
//            }
            methodNode.instructions.add(il);
        }
    }

    public static ClassNode tempMakeClass() {
        ClassNode cn = new ClassNode();
        cn.name = "TestClass";
        cn.fields.add(new FieldNode(ACC_PUBLIC, "v1", "I", null, null));
        cn.fields.add(new FieldNode(ACC_STATIC, "v2", "I", null, 3));
        //M2
        MethodNode m2 = new MethodNode(ACC_PROTECTED, "m2", "(I)I", null, null);
        InsnList il = new InsnList();
        il.add(new VarInsnNode(ILOAD, 1));
        il.add(new InsnNode(IRETURN));
        m2.instructions.add(il);

        //M3
        MethodNode m3 = new MethodNode(ACC_PUBLIC, "m3", "(II)I", null, null);
        il = new InsnList();
        il.add(new VarInsnNode(ILOAD, 1));
        il.add(new VarInsnNode(ILOAD, 2));
        il.add(new InsnNode(IADD));
        il.add(new InsnNode(IRETURN));
        m3.instructions.add(il);

        //M4
        MethodNode m4 = new MethodNode(ACC_PUBLIC + ACC_STATIC, "m4", "(II)I", null, null);
        il = new InsnList();
        il.add(new VarInsnNode(ILOAD, 1));
        il.add(new VarInsnNode(ILOAD, 2));
        il.add(new InsnNode(IADD));
        il.add(new InsnNode(IRETURN));
        m4.instructions.add(il);


        cn.methods.add(new MethodNode(ACC_PUBLIC, "m1", "()V", null, null));
        cn.methods.add(m2);
        cn.methods.add(m3);
        cn.methods.add(m4);
        cn.methods.add(new MethodNode(ACC_PUBLIC + ACC_STATIC, "m5", "(Ljava/lang/String;)V", null, null));
        return cn;
    }
}
