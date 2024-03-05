package org.sch.asm_tree_api.code.hikari;

import org.objectweb.asm.tree.*;
import org.sch.asm_tree_api.LoggerUtil;

import static org.sch.asm_tree_api.code.util.GetInputParamInMethods.findInputParameter;

/**
 * ProxyConnection 객체 내부 탐색 후 , prepareStatement / prepareCall /createStatement 구문에 로깅 진행
 * 이 클래스들은 PSTMT 생성 및 SQL 쿼리에 관련된다.
 */
public class AddLoggerHikariProxyConnection {
    public static void apply(ClassNode cn) {
        for (MethodNode mn : cn.methods) {
            switch (mn.name) { //특정 method에 변조 작업 진행
                //현재는 동일하게 동작하나, 추후 세분화에 따라 모듈화 필요 가능성이 있어 분리하였음.
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


