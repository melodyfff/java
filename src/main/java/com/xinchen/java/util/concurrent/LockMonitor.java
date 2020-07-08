package com.xinchen.java.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 *
 * {@link java.util.concurrent.locks.Lock} 状态监控
 *
 * 不断打印 {@link java.util.concurrent.locks.Lock}状态，该线程如果遇到中断可退出
 *
 * @author xinchen
 * @version 1.0
 * @date 08/07/2020 13:52
 */
class LockMonitor extends Thread{
    private final Lock lock;
    private final long interval;

    LockMonitor(Lock lock,long interval) {
        this.lock = lock;
        // 间隔ms
        this.interval = interval;
        // 设置为守护进程
        setDaemon(true);
    }

    @Override
    public void run() {
        monitor();
    }

    private void monitor() {
        while (!Thread.currentThread().isInterrupted()){
            System.out.println(String.format("[Lock - Monitor] %s",lock.toString()));
            try {
                TimeUnit.MILLISECONDS.sleep(interval);
            } catch (InterruptedException e) {
                // 如果遇到中断直接退出监控
                e.printStackTrace();
                System.out.println("[Lock - Monitor] interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }
}
