package thread.lock.demo2;

/**
 * 同步块synchronized (this)
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/5/14 22:06
 */
public class Run2 {
    public static void main(String[] args) {
        ObjectService objectService = new ObjectService();
        ThreadC threadC = new ThreadC(objectService);
        ThreadD threadD = new ThreadD(objectService);
        threadC.start();
        threadD.start();
//        ThreadC begin time=1526307303270
//        ThreadC end time=1526307305271
//        ThreadD begin time=1526307305271
//        ThreadD end time=1526307307272
    }
}

class ObjectService {
    public void serviceMethod() {
        try {
            // 同步块
            // 线程获取的是同步块synchronized (this)括号（）里面的对象实例的对象锁，这里就是ObjectService实例对象的对象锁了。
            // synchronized (){}的{}前后的代码依旧是异步的
            synchronized (this) {
                System.out.println(Thread.currentThread().getName()+" begin time=" + System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+" end time=" + System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ThreadC extends Thread {
    ObjectService objectService;

    public ThreadC(ObjectService objectService) {
        super("ThreadC");
        this.objectService = objectService;
    }

    @Override
    public void run() {
        super.run();
        objectService.serviceMethod();

    }
}

class ThreadD extends Thread {
    ObjectService objectService;

    public ThreadD(ObjectService objectService) {
        super("ThreadD");
        this.objectService = objectService;
    }

    @Override
    public void run() {
        super.run();
        objectService.serviceMethod();

    }
}