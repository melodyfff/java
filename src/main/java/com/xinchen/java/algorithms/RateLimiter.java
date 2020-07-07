package com.xinchen.java.algorithms;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 限流算法
 * <p>
 * 漏桶算法思想：以固定速率消费请求，漏桶容量固定，每次用户请求都得放入桶中，桶满则拒绝请求或等待。达到平滑网络请求的效果。
 * <p>
 * see <a href="https://www.jianshu.com/p/362d261115e7">超详细的Guava RateLimiter限流原理解析</a>
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2020/7/8 0:13
 */
public class RateLimiter {
    public static void main(String[] args) {
        LeakyBucketLimiter1 leakyBucketLimiter1 = new LeakyBucketLimiter1(10,4);
        leakyBucketLimiter1.acquire();
    }

    /**
     * 漏桶算法实例1
     */
    static class LeakyBucketLimiter1 {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        int capacity = 10;  // 桶的容量
        int water = 0;      // 当前水量
        int rate = 4;       // 速率
        long lastTime = System.currentTimeMillis();

        LeakyBucketLimiter1(int capacity, int rate) {
            this.capacity = capacity;
            this.rate = rate;
        }

        void acquire() {
            scheduledExecutorService
                    .scheduleWithFixedDelay(
                            // command the task to execute
                            this::doAcquire,
                            // initialDelay the time to delay first execution
                            0,
                            // delay the delay between the termination of one execution and the commencement of the next
                            500,
                            // unit the time unit of the initialDelay and delay parameters
                            TimeUnit.MILLISECONDS);
        }

        private void doAcquire() {
            long now = System.currentTimeMillis();
            // 计算当前水量
            water = Math.max(0, (int) (water - (now - lastTime) * rate / 1000));
            // 随机生成许可,模拟请求
            int permits = (int) (Math.random() * 8) + 1;
            System.out.println("请求数：" + permits + "，当前桶余量：" + (capacity - water));
            lastTime = now;
            if (capacity - water < permits) {
                System.out.println("限流了");
            } else {
                water += permits;
                System.out.println("剩余容量=" + (capacity - water));
            }
        }
    }
}
