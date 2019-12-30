package com.xinchen.java.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xinchen
 * @version 1.0
 * @date 30/12/2019 11:58
 */
public class MemEat {
    // change size
    static final int SIZE = 1 * 1024 * 1024;

    public static void main(String[] args) {
        List<byte[]> l = new ArrayList<>();
        while (true){
            byte[] b = new byte[SIZE];
            l.add(b);
            System.out.println(new RuntimeAdapt(Runtime.getRuntime()));
        }
    }

    static class RuntimeAdapt{
        // 总内存
        private long totalMemory;
        // 最大内存
        private long maxMemory;
        // 空闲内存
        private long freeMemory;

        public RuntimeAdapt(Runtime rt){
            totalMemory = rt.totalMemory();
            maxMemory = rt.maxMemory();
            freeMemory = rt.freeMemory();
        }

        @Override
        public String toString() {
            return "RuntimeAdapt{" +
                    "totalMemory=" + totalMemory +
                    ", maxMemory=" + maxMemory +
                    ", freeMemory=" + freeMemory +
                    '}';
        }
    }
}
