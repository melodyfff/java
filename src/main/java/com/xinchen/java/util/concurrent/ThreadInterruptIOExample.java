package com.xinchen.java.util.concurrent;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * Thread Interrupt IO
 * 线程中断机制
 *
 * see {@link ThreadInterruptExample}
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2020/7/7 0:01
 */
public class ThreadInterruptIOExample {
    static class ThreadInterruptIOTask extends Thread{
        volatile ServerSocket socket;

        @Override
        public void run() {
            try {
                socket = new ServerSocket(8888);
            } catch (IOException e) {
                System.out.println("Create socket err...");
                e.printStackTrace();
            }

            while (!Thread.currentThread().isInterrupted()){
                System.out.println("Waiting for connection...");
                try {
                    socket.accept(); // curl http://localhost:8888
                } catch (IOException e) {
                    System.out.println("accept fail...");
                    e.printStackTrace();
                    //中不中断由自己决定，如果需要真真中断线程，则需要重新设置中断位，如果
                    //不需要，则不用调用
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println("exit connection...");
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        ThreadInterruptIOTask ioTask = new ThreadInterruptIOTask();
        ioTask.start();
        Thread.sleep(1000);
        Thread.currentThread().interrupt();  // 终止main线程，如果不加，单纯抛出java.net.SocketException，并打印 “done.”
        ioTask.socket.close();              // 如果不关闭，ioTask.join()只会抛出java.lang.InterruptedException，并不会真正中断
        ioTask.join();                      // main线程中断标示为true，这里会抛出java.lang.InterruptedException ，下面还包含java.net.SocketException
        System.out.println("done.");        // main线程已经中断，这段并不会执行
    }
}
