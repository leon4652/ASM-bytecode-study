package org.sch.asm_tree_api.code.hikari;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.sch.asm_tree_api.LoggerUtil;

import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;

/**
 * ProxyConnection 객체의 'statement'가 들어가는 객체를 ProxyFactory에 넘겨주는 메서드들 로깅
 */
public class AddLoggerHikariProxyConnection {
    public static void apply(ClassNode cn) {
        for (MethodNode mn : cn.methods) {
            System.out.println(mn.name);
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


    //메서드 내부에 입력 파라미터가 있다면 로그로 뽑아내기
    public static void findInputParameter(ClassNode cn, MethodNode mn, InsnList il) {
        Type[] argumentTypes = Type.getArgumentTypes(mn.desc); // 파라미터 타입 가져오기
        if(argumentTypes.length == 0) return; //입력 파라미터가 없다면 스킵

        boolean isStatic = (mn.access & ACC_STATIC) != 0; //Static 메서드 체크
        int localIndex = isStatic ? 0 : 1; //정적 변수의 경우 지역변수 인덱스가 0부터 시작

        //클래스 메서드명
        int loggerIndex = LoggerUtil.loggerInstanceInit(cn, mn, il); //로거 인스턴스 생성

        System.out.println("있음!!!, 메서드 이름 : " + mn.name + " : " + mn.desc);
        for (Type argumentType : argumentTypes) {
            String content = "argumentType : " + argumentType + " localIdx : " + localIndex;
            System.out.println("argumentType.getClassName() : " + argumentType.getClassName());
            System.out.println("argumentType.toString() : " + argumentType.toString());
            LoggerUtil.sendLogString(il, loggerIndex, content, LoggerUtil.LogType.warn);

            if(argumentType.toString().equals("Ljava/lang/String;")) {
                System.out.println("문자열만 일단 로깅 테스트");
                il.add(new VarInsnNode(Opcodes.ALOAD, loggerIndex));    // 로거 객체 스택에 저장
                il.add(new VarInsnNode(Opcodes.ALOAD, localIndex));     //내 지역 변수 인덱스에서 값을 받아서 스택에 저장
                il.add(new MethodInsnNode(INVOKEINTERFACE, "org/slf4j/Logger", "warn", "(Ljava/lang/String;)V", true)); // 로그 메서드 호출
            }


            localIndex += argumentType.getSize(); //지역 변수 인덱스
        }

    }
}


