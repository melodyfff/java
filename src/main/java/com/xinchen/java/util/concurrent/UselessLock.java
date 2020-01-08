package com.xinchen.java.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * UselessLock 无用锁
 * <p>
 * 模仿Lock形，实际上直接用synchronized即可
 *
 * 这里简单的认为:
 * <pre>
 *     state = 0 资源可以被争抢
 *     state = 1 已经被上锁，所有在竞争该资源的线程进入等待
 * </pre>
 *
 * @author xinchen
 * @version 1.0
 * @date 08/01/2020 10:11
 */
public class UselessLock implements Lock {

    private static volatile int state = 0;

    @Override
    public void lock() {
        synchronized (UselessLock.class) {
            // 当n != 0 的时候说明已经被其他线程持有
            while (state != 0) {
                try {
                    UselessLock.class.wait(); // CAS自旋也行，这里wait()直接线程直接进入 waiting 等待被唤醒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 上锁
            state = 1;
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        synchronized (UselessLock.class) {
            // 释放锁
            state = 0;
            UselessLock.class.notifyAll(); // 唤醒等待this对象的所有线程
        }
    }
    @Override
    public Condition newCondition() {
        return null;
    }


    public static void main(String[] args) throws InterruptedException {
        // 构造一个不可变地址
        final int[] m = {0};

        // 使用自定义锁
        Lock lock = new UselessLock();

        // 线程组
        Thread[] threads = new Thread[100];


        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                lock.lock(); // 上锁
                try {
                    m[0]++; // m += 1
                } finally {
                    lock.unlock(); // 解锁
                }
            }
        };

        // 顺序启动
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }

        // 等待所有线程完成
        for (int i = 0; i < 100; i++) {
            threads[i].join();
        }

        // 输出最终结果
        System.out.println(m[0]);
    }
}
