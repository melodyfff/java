package thread.lock.demo1;

/**
 * 《Java多线程编程核心技术》
 * 非线程安全实例
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/5/14 21:44
 */
public class Run {
    public static void main(String[] args) {
        HasSelfPrivateNum numRef = new HasSelfPrivateNum();

        ThreadA threadA = new ThreadA(numRef);
        ThreadB threadB = new ThreadB(numRef);
        threadA.start();
        threadB.start();
//        结果同步
//        a set over!
//        a num = 100
//        b set over!
//        b num = 200
    }
}

class HasSelfPrivateNum {
    private int num = 0;

    // 两个线程访问同一个对象中的同步方法是一定是线程安全的
    // 这里线程获取的是HasSelfPrivateNum的对象实例的锁——对象锁。
    // 如果不加 synchronized 结果为非同步
    //    b set over!
    //    a set over!
    //    b num = 100
    //    a num = 100
    synchronized public void addI(String username) {

        try {
            if ("a".equals(username)) {
                num = 100;
                System.out.println("a set over!");
                Thread.sleep(2000);
            } else {
                num = 200;
                System.out.println("b set over!");
            }
            System.out.println(username + " num = " + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class ThreadA extends Thread {

    private HasSelfPrivateNum numRef;

    public ThreadA(HasSelfPrivateNum numRef) {
        // 设置线程名
        super("ThreadA");
        this.numRef = numRef;
    }

    @Override
    public void run(){
        super.run();
        numRef.addI("a");
    }

}

class ThreadB extends Thread {

    private HasSelfPrivateNum numRef;

    public ThreadB(HasSelfPrivateNum numRef) {
        // 设置线程名
        super("ThreadB");
        this.numRef = numRef;
    }
    @Override
    public void run(){
        super.run();
        numRef.addI("b");
    }
}
