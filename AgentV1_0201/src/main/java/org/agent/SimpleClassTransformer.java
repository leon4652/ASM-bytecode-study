package org.agent;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**

 ClassFileTransformer 인터페이스는 Java의 Instrumentation API의 일부로,
 Java 가상 머신(JVM)이 클래스를 로드할 때 그 클래스의 바이트코드를 변형할 수 있는 기능을 제공한다.

 1. '바이트코드 변형'
 더 쉽게 이야기하면, .class파일이 JVM에 실행되기 전에 class 파일의 내용을 수정하거나, 추가하는 것이다.

 2. 오버라이드
 인터페이스를 구현하기 위해 transform 메서드를 오버라이드 해야한다.
 이 과정에서 parameter를 받는데, 각 인수의 의미는 다음과 같다.

 - ClassLoader loader: 클래스를 로드하는 클래스 로더.
 - String className: 현재 변형하고 있는 클래스의 이름.
 - Class<?> classBeingRedefined: 이미 정의된 경우, 재정의되고 있는 클래스.
 - ProtectionDomain protectionDomain: 클래스의 보호 도메인.
 - byte[] classfileBuffer: 원본 클래스 파일의 바이트코드.

 3. 바이트코드 적용
 transform 메서드의 반환값을 통해 바이트코드가 반환되면
 JVM은 이 코드를 사용하여 Class를(바이트코드에 의해 변경된 클래스를) 정의한다.

 */

public class SimpleClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        // 여기에서 클래스 바이트코드를 조작하는 로직을 구현할 수 있다.
        // 나의 경우는 ASM과 연동하여 'classfileBuffer'에 대해 바이트코드 조작을 시행할 것이다.
        return classfileBuffer;
    }
}
