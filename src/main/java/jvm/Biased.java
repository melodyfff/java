package jvm;

import java.util.List;
import java.util.Vector;

/**
 * Description：偏向锁
 * 偏向锁是JDK1.6提出来的一种锁优化的机制。
 * 其核心的思想是，如果程序没有竞争，则取消之前已经取得锁的线程同步操作。
 * 也就是说，若某一锁被线程获取后，便进入偏向模式，当线程再次请求这个锁时，就无需再进行相关的同步操作了，从而节约了操作时间，
 * 如果在此之间有其他的线程进行了锁请求，则锁退出偏向模式。
 * 在JVM中使用-XX:+UseBiasedLocking
 *
 * 参数1：-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0 -client -Xmx512m -Xms512m
 *
 * Created by ChenXin on 2016/11/3.
 */
public class Biased {
    public static List<Integer> numberList = new Vector<Integer>();
    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        int count = 0;
        int startnum = 0;
        while(count<10000000){
            numberList.add(startnum);
            startnum+=2;
            count++;
        }
        long end = System.currentTimeMillis();
        System.out.println("time:"+(end-begin));
    }
}
