package com.xinchen.java.util;

import java.util.concurrent.Semaphore;

/**
 * SemaphoreExample
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2019/7/22 22:42
 */
public class SemaphoreExample {

    private static final int MAX_AVAILABLE = 5;

    private final Semaphore available = new Semaphore(MAX_AVAILABLE);

    public Object getItem() throws InterruptedException {
        // 获取许可
        available.acquire();
        return getNextAvailableItem();
    }

    public void putItem(Object x) {
        if (markAsUnUsed(x)){
            // 释放许可
            available.release();
        }
    }


    // Not a particularly efficient data structure; just for demo

    protected Object[] items = of(1, 2, 3, 4, 5);
    protected boolean[] used = new boolean[MAX_AVAILABLE];


    protected synchronized Object getNextAvailableItem() {
        for (int i = 0; i < MAX_AVAILABLE; ++i) {
            // 如果未被使用，设置为true表示已经被使用
            if (!used[i]) {
                used[i] = true;
                return items[i];
            }
        }
        // not reached
        return null;
    }

    protected synchronized boolean markAsUnUsed(Object item){
        for (int i = 0; i < MAX_AVAILABLE; ++i) {
            if (item == items[i]){
                // 如果已经被使用,设置为false表示释放
                if (used[i]){
                    used[i] = false;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static <T> T[] of(T... values) {
        return values;
    }


    public static void main(String[] args) throws InterruptedException {
        int N = 10;

        SemaphoreExample semaphoreExample = new SemaphoreExample();

        // 模拟使用线程池
        for (int i = 0; i < N; i++) {
            // 由于permit大小设置为5,acquire()到第5个时
            // 如果未释放，线程将被阻塞
            if (i == 5){
                Thread.sleep(5000);
                System.out.println("Semaphore达到限制，无可用permit,需要release\n");

                // 释放permit ,这里可设置为 j > 0 ，少释放一个观察线程被阻塞的情况
                for (int j = i-1; j > -1; j--) {
                    semaphoreExample.putItem(semaphoreExample.items[j]);
                    System.out.println("release: " + semaphoreExample.items[j]);
                }

                System.out.println();
            }


            System.out.println("acquire : " + semaphoreExample.getItem());
        }
    }
}
