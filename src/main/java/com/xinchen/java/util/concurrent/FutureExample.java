package com.xinchen.java.util.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *
 * {@link java.util.concurrent.Future} example
 *
 * see http://tutorials.jenkov.com/java-util-concurrent/java-future.html
 *
 *     https://www.jb51.net/article/134479.htm
 *
 *
 * @author xinchen
 * @version 1.0
 * @date 12/12/2019 19:30
 */
public class FutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 阻塞队列
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        // 计数器
        AtomicInteger count = new AtomicInteger();

        // 任务: 往队列中塞值,并计数
        Callable task = () -> {
            while (true){
                // 模拟业务执行时间
                TimeUnit.SECONDS.sleep(5);
                // 入队列, 阻塞
                queue.put(1);
                count.getAndIncrement();
            }
        };

        // FutureTask对task包装,可以获取任务的状态或者取消任务
        final FutureTask futureTask = new FutureTask<>(task);

        // 运行任务
        new Thread(futureTask).start();

        System.out.println("挂起");


        while (true){
            if (count.get()==5){
                // 取消任务
                futureTask.cancel(true);
                futureTask.get();
            }
            // 出队列, 阻塞
            queue.take();
            System.out.println("取出");
        }
    }
}
