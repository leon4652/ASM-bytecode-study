package org.sch.asm_tree_api.code.hikari;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

public class AddLoggerMethodNameAndInputParam {
    public static void apply(ClassNode cn) {
        Iterator<MethodNode> iterator = cn.methods.iterator();
        while (iterator.hasNext()) {
            MethodNode methodNode = iterator.next();
            InsnList il = new InsnList();
            Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc); // 파라미터 타입 가져오기
            boolean isStatic = (methodNode.access & ACC_STATIC) != 0; //Static 메서드 체크
            int localIndex = isStatic ? 0 : 1; //정적 변수의 경우 지역변수 인덱스가 0부터 시작

            if (!methodNode.name.contains("execute")) continue; //execute 부분만 변조
            
            //클래스 메서드명
            il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
            il.add(new LdcInsnNode("[class/method] : " + cn.name + " : " + methodNode.name ));
            il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false));


            for (Type argumentType : argumentTypes) {
                // Logger 인스턴스 생성
                il.add(new LdcInsnNode(Type.getType("L" + cn.name + ";")));
                il.add(new MethodInsnNode(INVOKESTATIC, "org/slf4j/LoggerFactory", "getLogger", "(Ljava/lang/Class;)Lorg/slf4j/Logger;", false));
                int loggerIndex = methodNode.maxLocals++;
                il.add(new VarInsnNode(ASTORE, loggerIndex));

                // 로깅
                il.add(new VarInsnNode(ALOAD, loggerIndex));
                il.add(new LdcInsnNode("Executing method: " + methodNode.name));
                il.add(new MethodInsnNode(INVOKEINTERFACE, "org/slf4j/Logger", "info", "(Ljava/lang/String;)V", true));

            }
//            for (Type argumentType : argumentTypes) {
//                //Logging(console)
//                il.add(new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream"));
//                int loadOpcode = argumentType.getOpcode(ILOAD);     //파라미터 타입에 따른 로드 명령어 가져오기
//                il.add(new VarInsnNode(loadOpcode, localIndex));    //지역 변수 스택에 로드(저장)
//
//                String descriptor = "(" + argumentType.getDescriptor() + ")V";
//                il.add(new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", descriptor, false));
//
//                localIndex += argumentType.getSize(); //다음 파라미터 인덱스 값 업데이트 (long, Double 등.. 8비트들은 변수 Array를 2칸 차지함
//            }
            methodNode.instructions.insert(il);
        }
    }
}
