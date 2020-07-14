package com.xinchen.java.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author xinchen
 * @version 1.0
 * @date 14/07/2020 13:58
 */
class ShutdownAbleThreadTest {

    /**
     * 测试优雅关闭
     * */
    @Test
    void testGracefulShutdown() throws InterruptedException {
        ShutdownAbleThread thread = new ShutdownAbleThread("graceful") {
            @Override
            public void execute() {
                while (getRunning()){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e){
                        // ignore
                    }
                }
            }
        };
        thread.start();
        Thread.sleep(10);
        assertTrue(thread.gracefulShutdown(1000, TimeUnit.MILLISECONDS));
    }

    /**
     * 测试立即中断线程
     */
    @Test
    void testForcibleShutdown() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        ShutdownAbleThread thread = new ShutdownAbleThread("forcible") {
            @Override
            public void execute() {
                try {
                    latch.countDown();
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        };
        thread.start();
        latch.await();
        thread.forceShutdown();  // 立即停止
        thread.join();  // 等待线程生命周期结束
        assertFalse(thread.isAlive());
    }

}