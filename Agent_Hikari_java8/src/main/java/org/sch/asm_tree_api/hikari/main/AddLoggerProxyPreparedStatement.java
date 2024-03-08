package org.sch.asm_tree_api.hikari.main;

import org.objectweb.asm.tree.*;
import org.sch.util.LoggerUtil;

import java.util.Iterator;

/**
 * use for modify test, add logging
 * ProxyPreparedStateMent 파일에 로깅 다는 로직
 */
public class AddLoggerProxyPreparedStatement {
    public static void apply(ClassNode cn) {
        //2진수 bit And 검증 : ABSTRACT(1024), 그 외 1023 이하..(PUBLIC = 1)
//        if ((cn.access & Opcodes.ACC_ABSTRACT) != 0) return;

        Iterator<MethodNode> iterator = cn.methods.iterator();
        while (iterator.hasNext()) {
            MethodNode methodNode = iterator.next();
            InsnList il = new InsnList();
            int loggerIndex = LoggerUtil.loggerInstanceInit(cn, methodNode, il);

            String content = "[TRIG<!>][class/method] : " + cn.name + " : " + methodNode.name;
            LoggerUtil.sendLogString(il, loggerIndex, content, LoggerUtil.LogType.warn);

            methodNode.instructions.insert(il); //return보다 앞서서 메서드 맨 위에 지시문 추가.

        }
    }

}
