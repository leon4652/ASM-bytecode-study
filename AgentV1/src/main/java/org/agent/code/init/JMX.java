package org.agent.code.init;

import java.lang.management.*;
import java.util.List;

/**
 * Java Library (java.lang.management 패키지) 사용
 * MXBean을 사용하여 힙 메모리 사용량과 관련된 다양한 정보를 조회할 수 있다.
 * java.lang.management 패키지는 Java 가상 머신(JVM)의 관리 인터페이스를 제공한다.
 */

public class JMX {


    /**
     * JVM의 메모리 시스템의 관리 인터페이스이다.
     * 힙 메모리 사용량, 초기 메모리 설정, 사용 중인 메모리, 커밋된 메모리 등을 제공한다.
     */
    public static void heapMemory() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        System.out.println("==== Heap Memory ==========");
        System.out.println("init memory : " + "\t" + heapMemoryUsage.getInit() + " bytes");
        System.out.println("max memory : " + "\t" + heapMemoryUsage.getMax() + " bytes");
        System.out.println("Used heap memory: " + "\t" + heapMemoryUsage.getUsed() + " bytes");

        System.out.println("==== Non-Heap Memory ==========");
        System.out.println("init memory : " + "\t" + nonHeapMemoryUsage.getInit() + " bytes");
        System.out.println("max memory : " + "\t" + nonHeapMemoryUsage.getMax() + " bytes");
        System.out.println("Used heap memory: " + "\t" + nonHeapMemoryUsage.getUsed() + " bytes");

        System.out.println();
    }

    /**
     * JVM 내에서 쓰레드의 관리에 사용된다.
     * 쓰레드의 총 수, 데드락에 빠진 쓰레드의 감지, 쓰레드의 CPU 시간과 사용자 시간을 모니터링할 수 있다.
     * ( 쓰레드 정보와 쓰레드 덤프(thread dump)를 제공)
     */
    public static void thread() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadMXBean.getAllThreadIds();
        System.out.println("Active thread count: " + threadIds.length);
        System.out.println();
    }

    /**
     * 가비지 컬렉션을 관리하는 인터페이스.
     * 가비지 컬렉터의 이름, 수집된 객체의 총 수, 가비지 컬렉션에 소요된 총 시간 등의 정보를 제공한다.
     */
    public static void garbageCollector() {
        List<GarbageCollectorMXBean> gcMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcMXBean : gcMXBeans) {
            System.out.println("=== " + gcMXBean.getName() + " === ");
            System.out.println("GC count: " + gcMXBean.getCollectionCount() + "\tGC time: " + gcMXBean.getCollectionTime() + " ms");
        }
        System.out.println();
    }

    /**
     * JIT 컴파일러의 컴파일 작업을 관리한다.
     * 컴파일러의 이름과 컴파일에 소요된 총 시간 등을 조회할 수 있다.
     */
    public static void jitCompilation() {
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
        System.out.println("JIT compiler name: " + compilationMXBean.getName());
        System.out.println("Total compilation time: " + compilationMXBean.getTotalCompilationTime() + " ms");
        System.out.println();
    }

    /**
     * JVM에서 클래스 로딩을 관리한다.
     * 로드된 클래스의 수, 전체 로드된 클래스의 수, 언로드된 클래스의 수 등을 확인할 수 있다.
     */
    public static void classLoading() {
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        System.out.println("Loaded class count: " + classLoadingMXBean.getLoadedClassCount());
        System.out.println("Total loaded class count: " + classLoadingMXBean.getTotalLoadedClassCount());
        System.out.println("Unloaded class count: " + classLoadingMXBean.getUnloadedClassCount());
        System.out.println();
    }


    /**
     * 운영 체제에 대한 정보와, 시스템의 로드 평균, 프로세스 CPU 시간 등의 시스템 레벨 정보를 제공한다.
     */
    public static void operationSystem() {
        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        System.out.println("OS Name: " + osMXBean.getName());
        System.out.println("Load Average: " + osMXBean.getSystemLoadAverage());
        System.out.println();
    }


    /**
     * JVM의 런타임 시스템에 대한 정보를 제공한다.
     * JVM의 이름, 버전, 클래스 패스, 라이브러리 패스, JVM이 시작된 시간 등이 포함된다.
     */
    public static void runtime() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println("JVM name: " + runtimeMXBean.getVmName());
        System.out.println("JVM uptime: " + runtimeMXBean.getUptime() + " ms");
        System.out.println();

    }


}

