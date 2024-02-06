package org.agent.util.init;

public class CallThread {
    public static void run() {
        JMX.runtime();
        JMX.classLoading();
        JMX.operationSystem();
        JMX.jitCompilation();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    JMX.thread();
                    JMX.heapMemory();
                    JMX.garbageCollector();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
