package thread;

import java.util.Random;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/5/8 23:02
 */
public class ThreadScopeShareDataError {
    private static int data = 0;
    public static void main(String[] args) {
        for (int i = 0;i<10;i++){
            new Thread(()-> {
                // 在此处断点，发现取到不同的值
                data = new Random().nextInt();
                System.out.println(Thread.currentThread().getName() + " has put data : " + data);
                new A().get();
                new B().get();
            }).start();
        }
    }

    static class A{
        public void get(){
            System.out.println("A from"+ Thread.currentThread().getName() +" get data:"+data);
        }
    }
    static class B{
        public void get(){
            System.out.println("B from"+ Thread.currentThread().getName() +" get data:"+data);
        }
    }
}
