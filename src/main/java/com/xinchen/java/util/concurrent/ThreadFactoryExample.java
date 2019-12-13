package com.xinchen.java.util.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadFactoryExample: how to create a thread by factory design pattern
 * <p>
 * implements {@link java.util.concurrent.ThreadFactory}
 * <p>
 * see https://howtodoinjava.com/java/multi-threading/creating-threads-using-java-util-concurrent-threadfactory/
 *
 * @author xinchen
 * @version 1.0
 * @date 13/12/2019 09:11
 */
public class ThreadFactoryExample {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> System.out.println(String.format(
                "Group[%s]-Name[%s]-Id[%s] is running...",
                Thread.currentThread().getThreadGroup(),
                Thread.currentThread().getName()
                , Thread.currentThread().getId()));


        System.out.println("-------------------SimpleThreadFactory------------------");
        SimpleThreadFactory simpleThreadFactory = new SimpleThreadFactory();
        simpleThreadFactory.newThread(task).start();
        simpleThreadFactory.newThread(task).start();
        simpleThreadFactory.newThread(task).start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("-----------------------------------------------------\n");


        System.out.println("-------------------DefaultThreadFactory------------------");
        DefaultThreadFactory defaultThreadFactory = new DefaultThreadFactory();
        ThreadPoolExecutor executorService =
                new ThreadPoolExecutor(3, 3, 10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(3), defaultThreadFactory, new LogPolicy());
        // execute 不期望有返回值,submit则期望有返回值
        executorService.execute(task);
        executorService.execute(task);
        // 立即关闭,观察拒绝策略
        executorService.shutdownNow();
        executorService.execute(task);
        System.out.println("-----------------------------------------------------\n");
    }


    /**
     * 创建简单的线程工厂
     *
     * 直接创建一个新的线程new Thread()
     */
    static class SimpleThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            // 直接返回一个新建的线程
            return new Thread(r);
        }
    }

    /**
     * 默认线程工厂,{@link Executors#defaultThreadFactory}
     *
     * 自定义线程名
     *
     */
    static class DefaultThreadFactory implements ThreadFactory {
        // 池编号
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        // 线程编号
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        // 线程组
        private final ThreadGroup group;
        // 线程名前缀
        private final String namePrefix;

        DefaultThreadFactory(){
            // 初始化线程组
            SecurityManager securityManager = System.getSecurityManager();
            group = null != securityManager ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            // 初始化线程名前缀
            namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            // 线程组, 任务task, 线程名, stackSize为0表示忽略
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()){
                // 关于守护进程
                // 1. 优先级较低
                // 2. 如果没有用户进程,都是守护/后台进程的话,jvm将结束
                t.setDaemon(false);
            }
            if (t.getPriority()!= Thread.NORM_PRIORITY){
                // 设置优先级为 Thread.NORM_PRIORITY = 5
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }


    /**
     * 自定义线程池拒绝策略
     *
     * 只是打印记录,不抛出异常
     */
    static class LogPolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("Task " + r.toString() + " rejected from " + executor.toString());
        }
    }
}
