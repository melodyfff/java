package com.xinchen.java.fun;

import sun.misc.Contended;

/**
 *
 * 缓存伪共享
 *
 * 当多线程修改互相独立的变量时，如果这些变量共享同一个缓存行，就会无意中影响彼此的性能，这就是伪共享
 *
 * 无法充分使用缓存行特性的现象，称为伪共享
 *
 * 一个Java的long类型变量是8字节，因此在一个缓存行（通常是64字节）中可以存8个long类型的变量
 *
 * 对于伪共享，一般的解决方案是，增大数组元素的间隔（Padding）使得由不同线程存取的元素位于不同的缓存行上，以空间换时间
 *
 * JDK8后使用 @sun.misc.Contended来避免 , 在运行时需要设置JVM启动参数-XX:-RestrictContended
 * 原理是在使用此注解的对象或字段的前后各增加128字节大小的padding，使用2倍于大多数硬件缓存行的大小来避免相邻扇区预取导致的伪共享冲突
 * 原文参考： http://mail.openjdk.java.net/pipermail/hotspot-dev/2012-November/007309.html
 *
 *
 * 参考阅读：
 * https://tech.meituan.com/2016/11/18/disruptor.html
 * https://www.jianshu.com/p/c3c108c3dcfd
 *
 * @author xinchen
 * @version 1.0
 * @date 22/06/2020 11:15
 */
class MemFalseSharing implements Runnable{
    /** 重复次数 **/
    private final static long ITERATIONS = 500L * 1000L * 100L;

    /** 模拟要修改的数组的index，这里保持不变 **/
    private int arrayIndex = 0;

    private static Value[] longs;
    private MemFalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    private static void runPaddingTest(int NUM_THREADS) throws InterruptedException {
        // 使用了共享机制
        Thread[] threads = new Thread[NUM_THREADS];
        longs = new ValuePadding[NUM_THREADS];
        for (int i = 0; i < longs.length; i++) {
            // 这里采用了共享机制
            longs[i] = new ValuePadding();
        }

        for (int i = 0; i < threads.length; i++) {
            // 创建多个线程去执行
            threads[i] = new Thread(new MemFalseSharing(i));
        }

        // 启动线程，并等待线程执行完毕
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    private static void runPaddingFor8Test(int NUM_THREADS) throws InterruptedException {
        // 使用了共享机制
        Thread[] threads = new Thread[NUM_THREADS];
        longs = new ValuePaddingFor8[NUM_THREADS];
        for (int i = 0; i < longs.length; i++) {
            // 这里采用了共享机制
            longs[i] = new ValuePaddingFor8();
        }

        for (int i = 0; i < threads.length; i++) {
            // 创建多个线程去执行
            threads[i] = new Thread(new MemFalseSharing(i));
        }

        // 启动线程，并等待线程执行完毕
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    private static void runNoPaddingTest(int NUM_THREADS) throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        // 未使用共享机制
        longs = new ValueNoPadding[NUM_THREADS];
        for (int i = 0; i < longs.length; i++) {
            // 这里采用了共享机制
            longs[i] = new ValueNoPadding();
        }

        for (int i = 0; i < threads.length; i++) {
            // 创建多个线程去执行
            threads[i] = new Thread(new MemFalseSharing(i));
        }

        // 启动线程，并等待线程执行完毕
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            // 多个线程，不断去修改这个值
            longs[arrayIndex].setValue(0L);
        }
    }

    interface Value{
        void setValue(long value);
    }

    public final static class ValuePadding implements Value{
        // 使用共享机制 ，通常缓存行为64字节 ，一个Long在java中为8字节
        // 这里主要是填充间隙使数据分布在不同的缓存行
        protected long p1, p2, p3, p4, p5, p6, p7;
        // 这里的volatile 标记，当值变更后，使其他线程内存中的值失效
        protected volatile long value = 0L;
        protected long p9, p10, p11, p12, p13, p14, p15;

        @Override
        public void setValue(long value) {
            this.value = value;
        }
    }

    public final static class ValueNoPadding implements Value{
        // 未使用共享机制
        // protected long p1, p2, p3, p4, p5, p6, p7;
        protected volatile long value = 0L;
        // protected long p9, p10, p11, p12, p13, p14, p15;

        @Override
        public void setValue(long value) {
            this.value = value;
        }
    }


    public final static class ValuePaddingFor8 implements Value{
        @Contended
        // JDK8后使用 @sun.misc.Contended来避免
        // 启动参数需要添加 -XX:-RestrictContended
        protected volatile long value = 0L;
        @Override
        public void setValue(long value) {
            this.value = value;
        }
    }

    public static void main(final String[] args) throws Exception {
        System.out.println("使用共享机制：");
        for(int i=1;i<10;i++){
            System.gc();
            final long start = System.currentTimeMillis();
            // i个
            runPaddingTest(i);
            System.out.println("Thread num "+i+" duration = " + (System.currentTimeMillis() - start));
        }

        System.out.println("\n未使用共享机制：");
        for(int i=1;i<10;i++){
            System.gc();
            final long start = System.currentTimeMillis();
            // i个
            runNoPaddingTest(i);
            System.out.println("Thread num "+i+" duration = " + (System.currentTimeMillis() - start));
        }

        System.out.println("\n使用共享机制JDK8：");
        for(int i=1;i<10;i++){
            System.gc();
            final long start = System.currentTimeMillis();
            // i个
            runPaddingFor8Test(i);
            System.out.println("Thread num "+i+" duration = " + (System.currentTimeMillis() - start));
        }

        System.out.println();

    }
}
