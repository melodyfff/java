package com.xinchen.java.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * 多线程的异常处理
 *
 * pool:
 * 1) 只有通过execute提交的任务，才能将它抛出的异常交给UncaughtExceptionHandler
 * 2) 通过submit提交的任务，无论是抛出的未检测异常还是已检查异常，都将被认为是任务返回状态的一部分
 *
 * @author xinchen
 * @version 1.0
 * @date 20/07/2020 15:41
 */
class MulThreadExceptionCatch {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private Thread.UncaughtExceptionHandler exceptionHandler = (thread, throwable) -> {
        System.out.format("[%s] - %s is error : %s",Thread.currentThread().getName(),thread.getName(),throwable.getMessage());
    };

    private Runnable poolTask = () -> {
        Thread.currentThread().setUncaughtExceptionHandler(exceptionHandler);
        task();
    };

    static void task(){
        throw new RuntimeException("i'am trouble.");
    }

    //----------------------------------------------------------------------------
    // Single Thread
    //----------------------------------------------------------------------------

    @Test
    void threadExceptionWithOutCatch(){
        new Thread(MulThreadExceptionCatch::task,"threadExceptionCatch").start();
    }

    @Test
    void threadExceptionCatch1(){
        final Thread thread = new Thread(MulThreadExceptionCatch::task, "threadExceptionCatch1");
        thread.setUncaughtExceptionHandler(exceptionHandler);
        thread.start();
    }

    @Test
    void threadExceptionCatch2(){
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
        final Thread thread = new Thread(MulThreadExceptionCatch::task, "threadExceptionCatch1");
        thread.start();
    }

    //----------------------------------------------------------------------------
    // Thread Pool - execute
    //----------------------------------------------------------------------------

    @Test
    void threadPoolExceptionExecuteWithOutCatch(){
        Thread thread = new Thread(MulThreadExceptionCatch::task, "threadPoolExceptionWithOutCatch");
        thread.setUncaughtExceptionHandler(exceptionHandler);
        executor.execute(thread);
        executor.shutdown();
    }

    @Test
    void threadPoolExceptionExecuteCatch1(){
        executor.execute(poolTask);
        executor.shutdown();
    }

    //----------------------------------------------------------------------------
    // Thread Pool - submit
    //----------------------------------------------------------------------------

    @Test
    void threadPoolExceptionSubmitWithOutCatch1(){
        executor.submit(poolTask);
        executor.shutdown();
        // nothing
    }

    @Test
    void threadPoolExceptionSubmitWithOutCatch(){
        executor.submit(new Thread(MulThreadExceptionCatch::task, "threadPoolExceptionSubmitWithOutCatch"));
        executor.shutdown();
        // nothing
    }

    @Test
    void threadPoolExceptionSubmitCatch1() {
        final Future<?> submit = executor.submit(poolTask);
        try {
            submit.get();
        } catch (InterruptedException | ExecutionException e) {
            // 其实是在main线程中进行处理
            exceptionHandler.uncaughtException(Thread.currentThread(),e);
        }
        executor.shutdown();
    }

    //----------------------------------------------------------------------------
    // CompletableFuture
    //----------------------------------------------------------------------------

    @Test
    void completableFutureExceptionCatch1() {
        CompletableFuture.runAsync(poolTask, executor)
                .exceptionally((throwable -> {
            exceptionHandler.uncaughtException(Thread.currentThread(), throwable);
            return null;
        }));
    }

    @Test
    void completableFutureExceptionCatch2() {
        CompletableFuture.supplyAsync(() -> {
            task();
            return null;
        }, executor).exceptionally((throwable -> {
            exceptionHandler.uncaughtException(Thread.currentThread(), throwable);
            return null;
        })).join();
    }

}
