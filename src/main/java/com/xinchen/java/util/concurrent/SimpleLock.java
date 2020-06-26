package com.xinchen.java.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 *
 * 一个基于AQS的简单锁的实现
 *
 * {@link Syn}通过实现{@link AbstractQueuedSynchronizer#tryAcquire(int)} 和 {@link AbstractQueuedSynchronizer#tryRelease(int)} (int)}
 *
 *
 *
 * @author xinchen
 * @version 1.0
 * @date 08/01/2020 10:11
 */
public class SimpleLock implements Lock {


    private final Syn syn = new Syn();

    @Override
    public void lock() {
        syn.acquire(1);
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
        syn.release(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    static class Syn extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, arg)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getExclusiveOwnerThread() != Thread.currentThread()){
                throw new IllegalStateException();
            }
            int c = getState() - arg;
            boolean flag = false;
            if (c==0){
                flag = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);

            return flag;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getExclusiveOwnerThread() == Thread.currentThread();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        final int[] m = {0};

        Lock lock = new SimpleLock();

        Thread[] threads = new Thread[100];

        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                lock.lock();
                try {
                    m[0]++;
                } finally {
                    lock.unlock();
                }
            }
        };

        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }

        for (int i = 0; i < 100; i++) {
            threads[i].join();
        }

        System.out.println(m[0]);

    }
}
