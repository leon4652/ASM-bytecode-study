package org.agent.util.asm.testcode.chap03;

import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class BasicExample extends ClassVisitor {
    public BasicExample(ClassVisitor classVisitor) {
        super(ASM9, classVisitor);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        return cv.visitField(access, name, descriptor, signature, value);
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

        return cv.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        //클래스 변수 생성하기
        cv.visitField(ACC_PUBLIC + ACC_STATIC, "newCreateStaticField", "I", null, null);
        visitFieldInsn(); //클래스 변수에 값 넣어보기
        printTest();                    //Sysout 출력
        exceptionAndInput();            //예외와 출력 테스트
        visitLocalVariableAnnotation(); //지역 변수 생성 및 어노테이션 적용
        visitAnnotation();              //메서드에 Annotation 주기
        visitParameterAnnotation();     //메서드의 input 파라미터에 어노테이션 추가
        super.visitEnd();
    }

    public void visitFieldInsn() {
//        MethodVisitor mv //
//        //1. 정적 변수(static)에 값 넣기
//        mv.visitInsn(ICONST_5);
//        mv.visitFieldInsn();
//
//        //2. 일반 전역 변수에 값 넣기
    }

    public void visitLocalVariableAnnotation() {
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC + ACC_STATIC, "visitLocalVariableAnnotation", "()V", null, null);
        mv.visitCode();

        // 로컬 변수의 시작과 끝을 나타내는 레이블 생성
        Label start = new Label();
        Label end = new Label();

        //1. 지역 변수 생성
        mv.visitLabel(start); //여기서부터 지역 변수 접근 가능
        mv.visitLocalVariable("myLocalVal", "F", null, start, end, 1); //변수 생성
        mv.visitInsn(FCONST_2); //스택에 상수 넣기
        mv.visitVarInsn(FSTORE, 0); //스택 값을 지역 변수에 적용
        //2. 지역변수에 어노테이션 달기
//        AnnotationVisitor av = mv.visitLocalVariableAnnotation(TypeReference.LOCAL_VARIABLE, null, new Label[] {start}, new Label[] {end}, new int[] {1}, "Ljava/lang/Deprecated;", true);
//        av.visitEnd();
        mv.visitLabel(end); //이제 지역 변수에 접근할 수 없음
        mv.visitMaxs(0, 0);
        mv.visitInsn(RETURN);
    }

    public void visitParameterAnnotation() {
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "visitParameterAnnotation", "(ILjava/lang/String;Ljava/lang/String;)V", null, null);
        mv.visitCode();
        // 첫 번째 파라미터(인덱스 0)에 @NotNull 어노테이션 추가, 예를 들어
        AnnotationVisitor av1 = mv.visitParameterAnnotation(0, "Ljavax/validation/constraints/Size;", true);
        av1.visit("min", 1); // @Size 크기 지정
        av1.visit("max", 10);
        AnnotationVisitor av2 = mv.visitParameterAnnotation(1, "Ljavax/validation/constraints/NotNull;", true);
        av1.visitEnd();
        av2.visitEnd();
        mv.visitMaxs(0, 0);
        mv.visitInsn(RETURN);
    }

    public void visitAnnotation() {
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "visitAnnotation", "(ILjava/lang/String;Ljava/lang/String;)V", null, null);
        mv.visitCode();
        AnnotationVisitor av = mv.visitAnnotation("Ljava/lang/Deprecated;", true); //mv 위에 어노테이션 등록
        av.visitEnd();
        mv.visitMaxs(0, 0);
        mv.visitInsn(RETURN);
    }

    //예외와 입력 파라미터의 다양한 예시
    public void exceptionAndInput() {
        String[] exceptions = {"java/lang/NullPointerException", "java/lang/ClassNotFoundException", "java/lang/ArrayIndexOutOfBoundsException"}; //다양한 예외들 추가 가능
        //여러 타입의 입력 파라미터 존재
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "showInputAndException", "(ZBCSIFJDLjava/lang/String;Ljava/lang/String;[I[[I)V", null, exceptions);
        mv.visitCode(); //방문
        mv.visitMaxs(0, 0);
        mv.visitInsn(RETURN);
    }

    //출력 테스트 = System.out.println();
    public void printTest() {
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "addSysout", "(ILjava/lang/String;Ljava/lang/String;)V", null, null);
        mv.visitCode(); //방문
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        //GETSTATIC : 정적 필드 접근 - System.out 필드에 접근, PrintStream 객체를 operands stack에 push - (1)
        mv.visitLdcInsn("visitLdcInsn을 통해 Operands stack에 이 String을 넣음(push) - (2)");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        //PrintStream의 println(String) 메서드를 호출, 이 과정에서 (1)과 (2) 사용

        mv.visitMaxs(0, 0);
        mv.visitInsn(RETURN);
    }
}