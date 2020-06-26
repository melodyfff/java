package com.xinchen.java.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 参考：
 * Java和Docker限制的那些事儿 http://www.dockone.io/article/5932
 *
 * https://royvanrijn.com/blog/2018/05/java-and-docker-memory-limits/
 *
 * @author xinchen
 * @version 1.0
 * @date 30/12/2019 11:58
 */
public class MemEat {
    // change size
    static final int SIZE = 5 * 1024 * 1024;

    public static void main(String[] args) throws InterruptedException {

        System.out.println(new RuntimeAdapt(Runtime.getRuntime()));
        List<byte[]> l = new ArrayList<>();
        while (true) {
            byte[] b = new byte[SIZE];
            l.add(b);
            System.out.println(new RuntimeAdapt(Runtime.getRuntime()));
        }
    }

    static class RuntimeAdapt {
        // 总内存
        private long totalMemory;
        // 最大内存
        private long maxMemory;
        // 空闲内存
        private long freeMemory;

        public RuntimeAdapt(Runtime rt) {
            totalMemory = rt.totalMemory();
            maxMemory = rt.maxMemory();
            freeMemory = rt.freeMemory();
        }

        @Override
        public String toString() {
            return "RuntimeAdapt{" +
                    "totalMemory=" + totalMemory  / 1024.0 / 1024.0 + " M [" + totalMemory + "]" +
                    ", maxMemory=" + maxMemory  / 1024.0 / 1024.0 + " M [" + maxMemory + "]" +
                    ", freeMemory=" + freeMemory  / 1024.0 / 1024.0 + " M [" + freeMemory + "]" +
                    '}';
        }
    }
}
