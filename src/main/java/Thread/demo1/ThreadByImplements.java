package Thread.demo1;

/**
 * Description：
 * Created by ChenXin on 2016/10/31.
 */
class MyTread2 implements Runnable {
    private String str;

    // 构造函数
    public MyTread2(String str) {
        this.str = str;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(str + "运行：" + i + "-------当前线程:" + Thread.currentThread().getName());
            try {
                Thread.sleep((int) Math.random() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
public class ThreadByImplements {

    public static void main(String[] args) {
        MyTread2 mT1 = new MyTread2("A");
        MyTread2 mT2 = new MyTread2("B");
        Thread t1 = new Thread(mT1);
        Thread t2 = new Thread(mT2);
        System.out.println("当前线程："+Thread.currentThread().getName());
        t1.start();
        t2.start();
    }
}