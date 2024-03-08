package org.sch.asm_tree_api.hikari.util;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.sch.util.LoggerUtil;

import static org.objectweb.asm.Opcodes.*;

/**
 * Method 내부의 입력 파라미터 관련 로직
 */
public class GetInputParamInMethods {

    //메서드 내부에 입력 파라미터가 있다면 로그로 뽑아내기
    public static void findInputParameter(ClassNode cn, MethodNode mn, InsnList il) {
        Type[] argumentTypes = Type.getArgumentTypes(mn.desc); // 파라미터 타입 가져오기
        if (argumentTypes.length == 0) return; //입력 파라미터가 없다면 스킵

        boolean isStatic = (mn.access & ACC_STATIC) != 0; //Static 메서드 체크
        int localIndex = isStatic ? 0 : 1; //정적 변수의 경우 지역변수 인덱스가 0부터 시작
        int loggerIndex = LoggerUtil.loggerInstanceInit(cn, mn, il); //로깅 인스턴스 생성

        for (Type argumentType : argumentTypes) {
            //디버그용
//            String content = "입력 파라미터 타입(argumentType) : " + argumentType + " 지역 변수 인덱스(localIdx) : " + localIndex;
//            LoggerUtil.sendLogString(il, loggerIndex, content, LoggerUtil.LogType.warn);

            switch (argumentType.toString()) {
                case "Ljava/lang/String;" : {
                    il.add(new VarInsnNode(Opcodes.ALOAD, loggerIndex));    // 로거 객체 스택에 저장
                    il.add(new VarInsnNode(Opcodes.ALOAD, localIndex));     //내 지역 변수 인덱스에서 값을 받아서 스택에 저장
                    il.add(new MethodInsnNode(INVOKEINTERFACE, "org/slf4j/Logger", "warn", "(Ljava/lang/String;)V", true)); // 로그 메서드 호출
                    break;
                }
//                case "I" -> {
//                    il.add(new VarInsnNode(Opcodes.ALOAD, loggerIndex));    // 로거 객체 스택에 저장
//                    il.add(new VarInsnNode(Opcodes.ILOAD, localIndex));     //내 지역 변수 인덱스에서 값을 받아서 스택에 저장
//                    //String.valueOf() 호출
//                    il.add(new MethodInsnNode(INVOKESTATIC, "java/lang/String", "valueOf", "(I)Ljava/lang/String;", false));
//                    il.add(new MethodInsnNode(INVOKEINTERFACE, "org/slf4j/Logger", "warn", "(Ljava/lang/String;)V", true)); // 로그 메서드 호출
//                }
//                case "[I", "[Ljava/lang/String;" -> {
//                    il.add(new VarInsnNode(Opcodes.ALOAD, loggerIndex));    // 로거 객체 스택에 저장
//                    il.add(new VarInsnNode(Opcodes.ALOAD, localIndex));     //내 지역 변수 인덱스에서 값을 받아서 스택에 저장
//                    //Arrays.toString() 호출
//                    il.add(new MethodInsnNode(INVOKESTATIC, "java/util/Arrays", "toString", "([I)Ljava/lang/String;", false));
//                    il.add(new MethodInsnNode(INVOKEINTERFACE, "org/slf4j/Logger", "warn", "(Ljava/lang/String;)V", true)); // 로그 메서드 호출
//                }
//                default -> {
//                    log.warn("[INFO][{}]{} Class {} Method 내부 지역변수 argumentType이 다음과 같아 무시됨 : {}",
//                            GetInputParamInMethods.class.getName(), cn.name, mn.name, argumentType.toString());
//                }
            }

            localIndex += argumentType.getSize(); //지역 변수 인덱스
        }
    }
}
