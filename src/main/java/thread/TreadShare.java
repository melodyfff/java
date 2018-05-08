package thread;

/**
 * Description：通过Thread尝试实现资源共享
 * Created by ChenXin on 2016/10/31.
 */
class MyThread3 extends Thread {
    private volatile int conut = 1000;

    @Override
    public void run() {

            for (int i = 1; i < 1000; i++) {
                synchronized (this){
                if (conut > 0) {
                    System.out.println(Thread.currentThread().getName() + "输出---->" + (this.conut--));
                }
            }
        }
    }
}

public class TreadShare {
    public static void main(String[] args) {
        MyThread3 mT1 = new MyThread3();
        System.out.println("当前线程：" + Thread.currentThread().getName());
        new Thread(mT1, "线程1").start();
        new Thread(mT1, "线程2").start();
    }
}