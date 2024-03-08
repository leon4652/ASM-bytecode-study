package org.sch.asm_tree_api.hikari.main;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.sch.util.LoggerUtil;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

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
            int loggerIndex = LoggerUtil.loggerInstanceInit(cn, methodNode, il); //로거 인스턴스 생성
            LoggerUtil.sendLogString(il, loggerIndex, "[class/method] : " + cn.name + " : " + methodNode.name, LoggerUtil.LogType.valueOf("info"));

            LoggerUtil.sendLogString(il, loggerIndex, "------진입 트리거 ----- " + cn.name + " : " + methodNode.name + " : " + methodNode.desc, LoggerUtil.LogType.warn);
            LoggerUtil.sendLogString(il, loggerIndex, "------사이즈 체크 ----- " + argumentTypes.length , LoggerUtil.LogType.warn);
            for (Type argumentType : argumentTypes) {
                LoggerUtil.sendLogString(il, loggerIndex, "@@@@@@@@@@", LoggerUtil.LogType.warn);
                String content = "argumentType : " + argumentType + " localIdx : " + localIndex;
                LoggerUtil.sendLogString(il, loggerIndex, content, LoggerUtil.LogType.warn);
                LoggerUtil.sendLogString(il, loggerIndex, "TEST LINE", LoggerUtil.LogType.warn);
                localIndex += argumentType.getSize(); //지역 변수 인덱스
            }

            methodNode.instructions.insert(il);
        }
    }
}
