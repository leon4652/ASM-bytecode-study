//import org.objectweb.asm.ClassReader;
//import org.objectweb.asm.ClassWriter;
//import org.objectweb.asm.Type;
//import org.objectweb.asm.tree.*;
//import org.sch.util.CodePrinter;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Iterator;
//
//import static org.objectweb.asm.Opcodes.*;
//
//
//public class main {
//    //컴파일하지 않고 직접 만드는 테스팅보드
//    public static void main(String[] args) throws IOException {
//
//        //------Build--------
//        ClassNode cn = tempMakeClass();
//
//        //------APPLY--------
//        apply(cn);
//
//        //-------Print--------
//        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
//        cn.accept(cw);
//        CodePrinter.printClass(cw.toByteArray(), "TEST", true);
//    }
//
//    public static void apply(ClassNode cn) {
//        Iterator<MethodNode> iterator = cn.methods.iterator();
//        while (iterator.hasNext()) {
//            MethodNode methodNode = iterator.next();
//            InsnList il = new InsnList();
//            // 파라미터 타입 가져오기
//            Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc);
//
//            //Static 메서드 체크
//            boolean isStatic = (methodNode.access & ACC_STATIC) != 0;
//            int localIndex = isStatic ? 0 : 1; //정적 변수의 경우 지역변수 인덱스가 0부터 시작
//
//            for (Type argumentType : argumentTypes) {
//
//
//                //Logging(console)
//                il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream"));
//                int loadOpcode = argumentType.getOpcode(ILOAD);     //파라미터 타입에 따른 로드 명령어 가져오기
//                il.add(new VarInsnNode(loadOpcode, localIndex));    //지역 변수 스택에 로드(저장)
//
//                String descriptor = "(" + argumentType.getDescriptor() + ")V";
//                il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", descriptor, false));
//
//                localIndex += argumentType.getSize(); //다음 파라미터 인덱스 업데이트 (long, Double 등.. 8비트들은 변수 Array를 2칸 차지함
//
//                // Logger 인스턴스 생성
//                il.add(new LdcInsnNode(Type.getType("L" + cn.name + ";")));
//                il.add(new MethodInsnNode(INVOKESTATIC, "org/slf4j/LoggerFactory", "getLogger", "(Ljava/lang/Class;)Lorg/slf4j/Logger;", false));
//                int loggerIndex = methodNode.maxLocals++;
//                il.add(new VarInsnNode(ASTORE, loggerIndex));
//
//                // 로깅
//                il.add(new VarInsnNode(ALOAD, loggerIndex));
//                il.add(new LdcInsnNode("Executing method: " + methodNode.name));
//                il.add(new MethodInsnNode(INVOKEINTERFACE, "org/slf4j/Logger", "info", "(Ljava/lang/String;)V", true));
//
//            }
//
//            methodNode.instructions.insert(il);
//        }
//    }
//
//
//
//    public static ClassNode tempMakeClass() {
//        ClassNode cn = new ClassNode();
//        cn.name = "TestClass";
//        cn.fields.add(new FieldNode(ACC_PUBLIC, "v1", "I", null, null));
//        cn.fields.add(new FieldNode(ACC_STATIC, "v2", "I", null, 3));
//        //M2
//        MethodNode m2 = new MethodNode(ACC_PROTECTED, "m2", "(I)I", null, null);
//        InsnList il = new InsnList();
//        il.add(new VarInsnNode(ILOAD, 1));
//        il.add(new InsnNode(IRETURN));
//        m2.instructions.add(il);
//
//        //M3
//        MethodNode m3 = new MethodNode(ACC_PUBLIC, "m3", "(II)I", null, null);
//        il = new InsnList();
//        il.add(new VarInsnNode(ILOAD, 1));
//        il.add(new VarInsnNode(ILOAD, 2));
//        il.add(new InsnNode(IADD));
//        il.add(new InsnNode(IRETURN));
//        m3.instructions.add(il);
//
//        //M4
//        MethodNode m4 = new MethodNode(ACC_PUBLIC + ACC_STATIC, "m4", "(II)I", null, null);
//        il = new InsnList();
//        il.add(new VarInsnNode(ILOAD, 0));
//        il.add(new VarInsnNode(ILOAD, 1));
//        il.add(new InsnNode(IADD));
//        il.add(new InsnNode(IRETURN));
//        m4.instructions.add(il);
//
//
//        cn.methods.add(new MethodNode(ACC_PUBLIC, "m1", "()V", null, null));
//        cn.methods.add(new MethodNode(ACC_PROTECTED, "m2Copy", "(I)V", null, null));
//        cn.methods.add(m2);
//        cn.methods.add(m3);
//        cn.methods.add(m4);
//        cn.methods.add(new MethodNode(ACC_PUBLIC + ACC_STATIC, "m5", "(Ljava/lang/String;)V", null, null));
//        cn.methods.add(new MethodNode(ACC_PUBLIC + ACC_STATIC, "m6", "(FFF)D", null, null));
//        cn.methods.add(new MethodNode(ACC_PROTECTED, "m7", "(Ljava/lang/String;Lorg/sch/HikariAgent;)V",null,null));
//        cn.methods.add(new MethodNode(ACC_PROTECTED, "m8", "(IIFJD)V",null,null));
//        return cn;//Lorg/sch/HikariAgent;
//    }
//}
