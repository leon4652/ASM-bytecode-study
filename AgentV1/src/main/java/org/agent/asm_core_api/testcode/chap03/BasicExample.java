package org.agent.asm_core_api.testcode.chap03;

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
        visitFieldInsn();               //일반 전역 변수 및 static 변수 생성자 생성 및 조작
        printTest();                    //Sysout 출력
        exceptionAndInput();            //예외와 출력 테스트
        visitLocalVariableAnnotation(); //지역 변수 생성 및 어노테이션 적용
        visitAnnotation();              //메서드에 Annotation 주기
        visitParameterAnnotation();     //메서드의 input 파라미터에 어노테이션 추가
        super.visitEnd();
    }

    public void visitFieldInsn() {
        //1. 일반 필드 추가 및 값 넣기 (변수가 있다면 visitField 수행, 아니라면 새로 생성)
        FieldVisitor fv = cv.visitField(ACC_PUBLIC, "makeNewField", "I", null, null);
        if (fv != null) {
            //1-1. 커스텀 생성자 만들기

            //1-2. 기본 생성자 예시
//            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null); //생성자 만들기
            //하지만 위처럼 실행하면 ClassFormatErr 발생, 이미 기본 생성자가 java에서는 알아서 생성되기 때문(똑같은 생성자 만들면 중복 에러)

            //1-3. 커스텀 생성자
            //그렇기에 매개변수를 다르게 하여, 다른 생성자를 추가하겠음. : 이제 Int형 변수를 받는다.
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "<init>", "(I)V", null, null); //생성자 만들기
            mv.visitCode(); //방문 시작

            //1-4. Object(상위)의 기본 생성자 호출 : java에서는 하위 클래스 생성자 호출 시 상위도 같이 호출하기 때문.
            // 이는 우리가 암묵적으로 사용하던 부모 클래스 생성자 호출 super()와 동일하다고 이해하면 된다.
            mv.visitVarInsn(ALOAD, 0); // this 참조
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);

            //1-5. 커스텀 생성자를 통한 매개변수 값 할당
            mv.visitVarInsn(ALOAD, 0); //this(자기자신 - 커스텀 생성자) stack에 넣기
            mv.visitLdcInsn(1000); //stack에 값 넣기
            mv.visitFieldInsn(PUTFIELD, "com/dummy/jdbcserver/example_asm/chap3/BasicExample", "makeNewField", "I");
            mv.visitInsn(RETURN);
            mv.visitMaxs(0,0);
            mv.visitEnd();
            fv.visitEnd();
        }

        //3. 정적 변수(static)에 값 넣기
        //먼저 Java 클래스의 정적 초기화 블록을 호출한다.
        MethodVisitor mv = cv.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
        mv.visitCode();
        mv.visitLdcInsn(123); //스택에 값 넣기
        mv.visitFieldInsn(PUTSTATIC, "com/dummy/jdbcserver/example_asm/chap3/BasicExample" , "staticVal", "I");
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
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