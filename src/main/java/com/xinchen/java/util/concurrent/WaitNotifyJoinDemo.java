package com.xinchen.java.util.concurrent;

/**
 *
 * wait() notify() join()
 *
 * @author xinchen
 * @version 1.0
 * @date 03/07/2020 14:45
 */
public class WaitNotifyJoinDemo {
    static final Object monitor = new Object();
    public static void main(String[] args) throws InterruptedException {



        final Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " Run...");
            System.out.println(Thread.currentThread().getName() + " waiting...");
            synchronized (monitor){
                try {
                    // wail release synchronized
                    monitor.wait(3000);
                    System.out.println(Thread.currentThread().getName() + " waite back....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " done ...");
        });


        final Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " Run...");
            synchronized (monitor){
                // notify
                monitor.notify();
            }
            System.out.println(Thread.currentThread().getName() + " done ...");
        });

        thread.start();
        thread2.start();


        // 这里的join实际上是调用了当前线程对象的wait(0),当该线程执行完毕或者InterruptedException时自动唤醒
        // see: https://www.cnblogs.com/xudilei/p/6867045.html
        thread.join();
        thread2.join();


        System.out.println("ok");
    }
}
