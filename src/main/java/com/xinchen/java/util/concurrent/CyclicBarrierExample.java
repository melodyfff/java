package com.xinchen.java.util.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link CyclicBarrier}
 * 允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point).
 * 因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier
 *
 * @author xinchen
 * @version 1.0
 * @date 17/04/2020 15:48
 */
public class CyclicBarrierExample {

    private static int SIZE = 5;

    private static CyclicBarrier barrier;


    public static void main(String[] args) {

        // 构建任务
        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + " wait by CyclicBarrier.");
            try {
                // 等待 parties达标
                barrier.await();
                System.out.println(Thread.currentThread().getName()+" run.");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };


        // 初始化Cyclicbarrier
        barrier = new CyclicBarrier(SIZE, () -> System.out.println("CyclicBarrier's parties is: "+barrier.getParties()));


        // 构建线程执行器
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < SIZE; i++) {
            executorService.execute(task);
        }
        executorService.shutdown();


    }
}
