package com.xinchen.java.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@link Condition} 中的demo
 *
 * @see java.util.concurrent.ArrayBlockingQueue
 *
 * @author xinchen
 * @version 1.0
 * @date 09/01/2020 11:37
 */
public class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr,takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            // 当队列容量达到上限时
            while (count == items.length){
                // Condition notFull不成立, park当前线程
                // 直到notFull.signal() 释放信号唤醒在此条件下进入wait-sets的线程
                notFull.await();
            }

            items[putptr] = x;

            // 循环放值,当达到最大上限,回归至初始位置
            if (++putptr == items.length){
                putptr = 0;
            }

            // 计数
            ++count;

            // Condition notEmpty条件成立(至少刚刚放入了一个),
            // 发送信号唤醒因为notEmpty进入wait-sets的线程
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            // 当队列中为空,并且尝试取值
            while (count == 0){
                // Condition notEmpty不成立, 直接park线程,
                // 直至notEmpty.signal() 释放信号唤醒此条件下进入wait-sets的线程
                notEmpty.await();
            }

            // 取值
            Object x = items[takeptr];

            // 循环取值,当达到最大长度的时候,回归至初始位置
            if (++takeptr == items.length){
                takeptr = 0;
            }

            // 记录当前容纳的数目
            --count;

            // 发送信号, Condition notFull成立(因为最少刚刚消费了一个)
            // 唤醒因为notFull条件进入wait-sets的线程
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final BoundedBuffer boundedBuffer = new BoundedBuffer();
        Runnable taks = () -> {
            try {
                boundedBuffer.put("ok");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // 塞满队列,当101次塞入时,进入等待
        for (int i = 0; i < 101; i++) {
            new Thread(taks).start();
        }

        TimeUnit.SECONDS.sleep(3);

        // 消耗一次,唤醒之前塞入队列时进入等待的线程
        boundedBuffer.take();
    }
}
