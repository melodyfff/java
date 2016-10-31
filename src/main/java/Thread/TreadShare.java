package Thread;

/**
 * Description：通过Thread尝试实现资源共享
 * Created by ChenXin on 2016/10/31.
 */
class MyThread3 extends Thread {
    private int conut =5;
    public void run(){
        for(int i =1;i<10;i++){
            if(this.conut>0){
                System.out.println(Thread.currentThread().getName()+"输出---->"+(this.conut--));
            }
        }
    }
}
public class TreadShare {
    public static void main(String[] args) {
        MyThread3 mT1 = new MyThread3();
        MyThread3 mT2 = new MyThread3();
        System.out.println("当前线程：" + Thread.currentThread().getName());
        mT1.start();
        mT2.start();
    }
}