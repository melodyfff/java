package com.xinchen.java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 *
 * {@link AbstractQueuedSynchronizer} 中的使用实例
 *
 * 这是一个不可重入的互斥锁定类，使用值0表示解锁状态，使用值1表示锁定状态
 *
 *
 * @author xinchen
 * @version 1.0
 * @date 08/01/2020 15:31
 */
public class AQSExample implements Lock {

    private final Sync sync = new Sync();

    public void lock()                { sync.acquire(1); }
    public boolean tryLock()          { return sync.tryAcquire(1); }
    public void unlock()              { sync.release(1); }
    public Condition newCondition()   { return sync.newCondition(); }
    public boolean isLocked()         { return sync.isHeldExclusively(); }
    public boolean hasQueuedThreads() { return sync.hasQueuedThreads(); }
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }

    private static class Sync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean isHeldExclusively() {
            // 报告是否处于锁定状态
            return getState() == 1;
        }

        @Override
        protected boolean tryAcquire(int acquires) {
            // 如果状态为0，则获取锁
            assert acquires == 1; // Otherwise unused
            if (compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int release) {
            // 通过将状态设置为0来释放锁定
            assert release == 1; // Otherwise unused
            if (getState() == 0) {
                throw new IllegalStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        Condition newCondition(){
            // 提供条件
            return new ConditionObject();
        }

        private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
            // 可能会反序列化，初始化state状态
            s.defaultReadObject();
            setState(0); // reset to unlocked state
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int[] c = {0};
        Lock lock = new AQSExample();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    lock.lock();
                    c[0]++;
                } finally {
                    lock.unlock();
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            final Thread thread = new Thread(task);
            thread.start();
            thread.join();
        }

        System.out.println(c[0]);
    }
}
