package org.sch.asm_tree_api;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;

public class LoggerUtil {

    /**
     * Logger 인스턴스 생성, maxLocal 값 조정 및 ASTORE 진행
     * return : loggerIndex
     * @param cn
     * @param mn
     * @param il
     */
    public static int loggerInit(ClassNode cn, MethodNode mn, InsnList il) {
        // Logger 인스턴스 생성
        il.add(new LdcInsnNode(Type.getType("L" + cn.name + ";")));
        il.add(new MethodInsnNode(INVOKESTATIC, "org/slf4j/LoggerFactory", "getLogger", "(Ljava/lang/Class;)Lorg/slf4j/Logger;", false));
        int loggerIndex = mn.maxLocals++;
        il.add(new VarInsnNode(ASTORE, loggerIndex));
        return loggerIndex; //지역 변수 배열의 최대 인덱스
    }

    /**
     * 실제 로깅 진행 메서드
     */
    public static void sendLogString(InsnList il, int loggerIndex, String content, LogType type) {
        il.add(new VarInsnNode(ALOAD, loggerIndex));
        il.add(new LdcInsnNode(content));
        il.add(new MethodInsnNode(INVOKEINTERFACE, "org/slf4j/Logger", type.name(), "(Ljava/lang/String;)V", true));
    }

    /**
     * Logger 출력 타입
     */
    public enum LogType {
        info,
        debug,
        warn
    }
}
