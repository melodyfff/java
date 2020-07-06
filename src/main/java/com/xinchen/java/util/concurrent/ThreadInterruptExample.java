package com.xinchen.java.util.concurrent;

/**
 *
 * Thread Interrupt
 * 线程中断机制
 *
 * Java的中断是一种协作机制。也就是说调用线程对象的interrupt方法并不一定就中断了正在运行的线程，它只是要求线程自己在合适的时机中断自己。
 * 每个线程都有一个boolean的中断状态（这个状态不在Thread的属性上），interrupt方法仅仅只是将该状态置为true。
 *
 *
 *
 * <pre>
 *     如果一个线程处于了阻塞状态（如线程调用了thread.sleep、thread.join、thread.wait、1.5中的condition.await、以及可中断的通道上的 I/O 操作方法后可进入阻塞状态），
 *     则在线程在检查中断标示时如果发现中断标示为true，则会在这些阻塞方法（sleep、join、wait、1.5中的condition.await及可中断的通道上的 I/O 操作方法）调用处抛出InterruptedException异常，
 *     并且在抛出异常后立即将线程的中断标示位清除，即重新设置为false。抛出异常是为了线程从阻塞状态醒过来，并在结束线程前让程序员有足够的时间来处理中断请求。
 *
 *
 *     一、没有任何语言方面的需求一个被中断的线程应该终止。中断一个线程只是为了引起该线程的注意，被中断线程可以决定如何应对中断。
 *     二、对于处于sleep，join等操作的线程，如果被调用interrupt()后，会抛出InterruptedException，然后线程的中断标志位会由true重置为false，因为线程为了处理异常已经重新处于就绪状态。
 *     三、不可中断的操作，包括进入synchronized段以及Lock.lock()，inputSteam.read()等，调用interrupt()对于这几个问题无效，因为它们都不抛出中断异常。如果拿不到资源，它们会无限期阻塞下去。
 *     四、死锁状态线程无法被中断
 * </pre>
 *
 * see <a href="https://www.cnblogs.com/onlywujun/p/3565082.html">Thread的中断机制(interrupt)</a>
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2020/7/6 23:35
 */
public class ThreadInterruptExample {
    static class ThreadInterruptTask extends Thread{
        @Override
        public void run() {
            synchronized (this){
                while (!Thread.currentThread().isInterrupted()){
                    try {
                        // 线程阻塞，如果线程收到中断操作信号将抛出异常
                        this.wait(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted...");
                        e.printStackTrace();
                        /*
                         * 如果线程在调用 Object.wait()方法，或者该类的 join() 、sleep()方法过程中受阻，则其中断状态将被清除
                         */
                        System.out.println("Thread interrupted status is : "+this.isInterrupted());  // false
                        //中不中断由自己决定，如果需要真真中断线程，则需要重新设置中断位，如果
                        //不需要，则不用调用
                        Thread.currentThread().interrupt();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName()+" done.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final ThreadInterruptTask task = new ThreadInterruptTask();
        task.start();
        Thread.sleep(1000);
        task.interrupt(); // 中断
        task.join();  // 等待线程执行完毕或者抛出InterruptedException
        System.out.println("done");
    }
}
