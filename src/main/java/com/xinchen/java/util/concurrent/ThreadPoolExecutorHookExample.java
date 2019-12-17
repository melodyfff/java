package com.xinchen.java.util.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义实现{@link ThreadPoolExecutor}中的hook函数 example
 *
 * see https://howtodoinjava.com/java/multi-threading/how-to-use-blockingqueue-and-threadpoolexecutor-in-java/
 *
 * @author xinchen
 * @version 1.0
 * @date 16/12/2019 10:35
 */
public class ThreadPoolExecutorHookExample {


    public static void main(String[] args) throws Exception {
        Runnable task = () -> System.out.println(String.format("%s is running",Thread.currentThread().getName()));
        Callable task1 = () -> {
            System.out.println(String.format("%s is running", Thread.currentThread().getName()));
            return "ok";
        };

        ThreadPoolExecutor executor = new ThreadPoolExecutorHook(3, 3, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3));

        executor.execute(task);
        TimeUnit.SECONDS.sleep(1);

        // ---------分割线----------
        System.out.println();

        Future submit = executor.submit(task1);
        TimeUnit.SECONDS.sleep(1);

        System.out.println(String.format("%s get result [%s]", Thread.currentThread().getName(),submit.get()));


        // 停止executor
        executor.shutdown();

        // 主线程结束
        System.out.println();
        System.out.println(String.format("%s is down", Thread.currentThread().getName()));
    }

    /**
     * ThreadPoolExecutorHook
     * 自定义实现{@link ThreadPoolExecutor}中的hook函数
     */
    static class ThreadPoolExecutorHook extends ThreadPoolExecutor {
        public ThreadPoolExecutorHook(int corePoolSize,
                                      int maximumPoolSize,
                                      long keepAliveTime, TimeUnit unit,
                                      BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        /* Extension hooks */

        /**
         * 在给定线程中执行给定Runnable之前调用的方法.
         * 该方法由线程{@code t}调用,将执行任务{@code r}，并可用于重新初始化ThreadLocals，或执行日志记录。
         *
         * 子类通常应调用此方法中的{@code super.terminated}
         * @param t the thread that will run task {@code r}
         * @param r the task that will be executed
         */
        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);
            System.out.println(String.format(">>>> %s Perform beforeExecute() logic", Thread.currentThread().getName()));
        }

        /**
         * 在给定Runnable执行完成时调用的方法.
         * 此方法由执行任务的线程调用.
         * 如果Throwable是non-null,则可能是未捕获的{@code RuntimeException}或{@code Error}导致执行突然终止
         *
         * 子类通常应调用此方法中的{@code super.terminated}
         * @param r the runnable that has completed
         * @param t the exception that caused termination, or null if execution completed normally
         */
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if (null == t && r instanceof Future){
                try {
                    Object result = ((Future) r).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            if (null != t) {
                System.out.println(String.format(">>>> %s Perform afterExecute() logic , Throwable is : %s", Thread.currentThread().getName(),t));
            }
            System.out.println(String.format(">>>> %s Perform afterExecute() logic", Thread.currentThread().getName()));
        }

        /**
         * 执行程序终止时(线程终止)调用的方法.
         * 默认不执行任何操作.
         * 注:
         * <p>
         * 子类通常应调用此方法中的{@code super.terminated}
         * </p>
         */
        @Override
        protected void terminated() {
            super.terminated();
            System.out.println(String.format("%s is down", Thread.currentThread().getName()));
            System.out.println(String.format(">>>> %s Perform terminated() logic", Thread.currentThread().getName()));
        }
    }

}
