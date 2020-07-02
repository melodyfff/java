package com.xinchen.java.fun;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 *
 * ABA问题： 一个线程把数据A变为了B，然后又重新变成了A。此时另外一个线程读取的时候，发现A没有变化，就误以为是原来的那个A。
 *
 * @author xinchen
 * @version 1.0
 * @date 02/07/2020 16:16
 */
public class ABAQuestion {

    private static AtomicInteger index = new AtomicInteger(1);

    /** 带上stamp 戳 */
    private static AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(1, 1);

    /** 带上mark */
    private static AtomicMarkableReference<Integer> markableReference = new AtomicMarkableReference<Integer>(100,false);

    public static void main(String[] args) throws InterruptedException {
//        ABA();

        System.out.println();

        solveABA();
    }

    static void ABA() throws InterruptedException {
        Runnable task1 = () -> {
            System.out.println(Thread.currentThread().getName()+ " "+index.compareAndSet(1,2));
            System.out.println(Thread.currentThread().getName()+ " "+index.compareAndSet(2,1));
        };
        Runnable task2 = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + index.compareAndSet(1, 2));

        };
        run(task1,task2);
    }

    static void solveABA() throws InterruptedException {
        Runnable task1 = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+ " "+stampedReference.compareAndSet(1, 2, stampedReference.getStamp(), stampedReference.getStamp() + 1));
            System.out.println(Thread.currentThread().getName()+ " "+stampedReference.compareAndSet(2, 1, stampedReference.getStamp(), stampedReference.getStamp() + 1));
        };
        Runnable task2 = () -> {
            final int stamp = stampedReference.getStamp();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + stampedReference.compareAndSet(1, 2, stamp, stamp + 1));
        };
        run(task1,task2);
    }

    static void run(Runnable task1,Runnable task2) throws InterruptedException {

        final ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(task1);
        executorService.execute(task2);
        executorService.shutdown();
    }

}
