package thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/5/8 23:04
 */
public class ThreadScopeShareData {
    private static int data = 0;
    private static Map<Thread, Integer> threadMap = new HashMap<>();
    public static void main(String[] args) {
        for (int i = 0;i<3;i++){
            new Thread(()-> {
                int data = new Random().nextInt();
                System.out.println(Thread.currentThread().getName() + " has put data : " + data);
                threadMap.put(Thread.currentThread(), data);

                new A().get();
                new B().get();
            }).start();
        }
    }

    static class A{
        public void get(){
            System.out.println("A from"+ Thread.currentThread().getName() +" get data:"+threadMap.get(Thread.currentThread()));
        }
    }
    static class B{
        public void get(){
            System.out.println("B from"+ Thread.currentThread().getName() +" get data:"+threadMap.get(Thread.currentThread()));
        }
    }
}
