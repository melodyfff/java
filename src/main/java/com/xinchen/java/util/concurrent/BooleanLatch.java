package com.xinchen.java.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 *
 *  类{@link java.util.concurrent.CountDownLatch}，只是它只需要signal触发一个即可。
 *  因为锁存器是非排他性的，所以它使用了shared 获取和释放方法
 *
 * @author xinchen
 * @version 1.0
 * @date 08/01/2020 15:55
 */
public class BooleanLatch {
    private static class Sync extends AbstractQueuedSynchronizer{

        boolean isSignalled() { return getState() != 0; }

        @Override
        protected int tryAcquireShared(int acquires) {
            // 单纯判断
            return isSignalled() ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int ignore) {
            // 由于这里只是针对一个，所以释放的时候直接设置state为1
            setState(1);
            return true;
        }
    }

    private final Sync sync = new Sync();
    public boolean isSignalled() { return sync.isSignalled(); }
    public void signal()         { sync.releaseShared(1); }
    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }


    public static void main(String[] args) throws InterruptedException {
        BooleanLatch latch = new BooleanLatch();

        final Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.signal();
        });
        thread.start();

        latch.await();
        System.out.println("ok");
    }
}
