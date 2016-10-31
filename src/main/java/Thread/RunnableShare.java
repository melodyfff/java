package Thread;

/**
 * Description：通过Runnable实现共享资源
 * Created by ChenXin on 2016/10/31.
 */
class MyThread implements Runnable {
    private int conut =5;
    public void run(){
        for(int i =1;i<5;i++){
            if(this.conut>0){
                System.out.println(Thread.currentThread().getName()+"输出---->"+(this.conut--));
            }
        }
    }
}
public class RunnableShare {
    public static void main(String[] args) {
        MyThread mT1 = new MyThread();
        System.out.println("当前线程：" + Thread.currentThread().getName());
        new Thread(mT1,"命名：线程1").start();
        new Thread(mT1,"命名：线程2").start();
    }
}