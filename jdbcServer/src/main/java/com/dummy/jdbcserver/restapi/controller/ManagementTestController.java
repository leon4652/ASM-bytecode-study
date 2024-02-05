package com.dummy.jdbcserver.restapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.*;
import java.util.List;

@RestController
@RequestMapping("/jmx")
public class ManagementTestController {

    @GetMapping("/memory")
    public void jmxMemory() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();

        long usedHeapMemory = heapMemoryUsage.getUsed();
        System.out.println("Used heap memory: " + usedHeapMemory + " bytes");

    }

    @GetMapping("/thread")
    public void jmxThread() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadMXBean.getAllThreadIds();
        System.out.println("Active thread count: " + threadIds.length);

    }

    @GetMapping("/gc")
    public void jmxGc() {
        List<GarbageCollectorMXBean> gcMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcMXBean : gcMXBeans) {
            System.out.println("GC name: " + gcMXBean.getName());
            System.out.println("GC count: " + gcMXBean.getCollectionCount());
            System.out.println("GC time: " + gcMXBean.getCollectionTime() + " ms");
        }


    }

    @GetMapping("/compliation")
    public void jmxCompliation() {
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
        System.out.println("JIT compiler name: " + compilationMXBean.getName());
        System.out.println("Total compilation time: " + compilationMXBean.getTotalCompilationTime() + " ms");

    }

    @GetMapping("/classLoad")
    public void jmxClassLoad() {
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        System.out.println("Loaded class count: " + classLoadingMXBean.getLoadedClassCount());
        System.out.println("Total loaded class count: " + classLoadingMXBean.getTotalLoadedClassCount());
        System.out.println("Unloaded class count: " + classLoadingMXBean.getUnloadedClassCount());

    }

    @GetMapping("/os")
    public void jmxOs() {
        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        System.out.println("OS Name: " + osMXBean.getName());
        System.out.println("Load Average: " + osMXBean.getSystemLoadAverage());
    }


}
