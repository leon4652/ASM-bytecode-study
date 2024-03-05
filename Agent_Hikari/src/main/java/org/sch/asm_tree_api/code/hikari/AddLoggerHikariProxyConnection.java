package org.sch.asm_tree_api.code.hikari;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.sch.asm_tree_api.LoggerUtil;

import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.sch.asm_tree_api.code.util.GetInputParamInMethods.findInputParameter;

/**
 * ProxyConnection 객체의 'statement'가 들어가는 객체를 ProxyFactory에 넘겨주는 메서드들 로깅
 */
public class AddLoggerHikariProxyConnection {
    public static void apply(ClassNode cn) {
        for (MethodNode mn : cn.methods) {
            switch (mn.name) { //특정 method에 변조 작업 진행
                case "prepareStatement" -> {setPrepareStatement(cn, mn);}
                case "prepareCall" -> {setPrepareCall(cn, mn);}
                case "createStatement" -> {setCreateStatement(cn,mn);}
                default -> {} //pass
            }
        }
    }

    public static void setPrepareStatement(ClassNode cn, MethodNode mn){
            InsnList il = new InsnList();
            int loggerIndex = LoggerUtil.loggerInstanceInit(cn, mn, il);
            LoggerUtil.sendLogString(il, loggerIndex, "setPrepareStatement 동작 : " + mn.name, LoggerUtil.LogType.info);
            findInputParameter(cn, mn, il); //입력 변수 로깅
            mn.instructions.insert(il);
    }

    public static void setPrepareCall(ClassNode cn, MethodNode mn){
        InsnList il = new InsnList();
        int loggerIndex = LoggerUtil.loggerInstanceInit(cn, mn, il);
        LoggerUtil.sendLogString(il, loggerIndex, "setPrepareCall 동작 : " + mn.name, LoggerUtil.LogType.info);
        findInputParameter(cn, mn, il); //입력 변수 로깅
        mn.instructions.insert(il);
    }

    public static void setCreateStatement(ClassNode cn, MethodNode mn){
        InsnList il = new InsnList();
        int loggerIndex = LoggerUtil.loggerInstanceInit(cn, mn, il);
        LoggerUtil.sendLogString(il, loggerIndex, "setCreateStatement 동작 : " + mn.name, LoggerUtil.LogType.info);
        findInputParameter(cn, mn, il); //입력 변수 로깅
        mn.instructions.insert(il);
    }




}


