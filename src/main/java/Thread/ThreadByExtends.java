package Thread;

/**
 * @Description:
 * @author xinchen
 * @date 2016年9月22日 下午10:41:25
 * @version V1.0
 */
class MyTread1 extends Thread {
    private String str;

    // 构造函数
    public MyTread1(String str) {
        this.str = str;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(str + "运行：" + i + "-------当前线程:" + Thread.currentThread().getName());
            try {
                sleep((int) Math.random() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadByExtends {
    public static void main(String[] args) {
        MyTread1 mT1 = new MyTread1("A");
        MyTread1 mT2 = new MyTread1("B");
        System.out.println("当前线程："+Thread.currentThread().getName());
        mT1.start();
        mT2.start();
    }
}