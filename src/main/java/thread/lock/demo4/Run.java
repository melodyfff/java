package thread.lock.demo4;

/**
 * 静态synchronized同步方法
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/5/14 22:52
 */
public class Run {
    public static void main(String[] args) {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();
        threadA.setName("A");
        threadB.setName("B");
        threadA.start();
        threadB.start();
//        线程名称为：B在1526309711329进入printB
//        线程名称为：B在1526309711329离开printB
//        线程名称为：A在1526309711329进入printA
//        线程名称为：A在1526309714330离开printA
    }
}

class Service {
//    对于同一个类A，线程1争夺A对象实例的对象锁，线程2争夺类A的类锁，这两者不存在竞争关系。也就说对象锁和类锁互补干预内政
    // 两个线程在争夺同一个类锁，因此同步
    synchronized public static void printA() {
        // 或者直接使用类锁
//        synchronized (Service.class){
            try {
                System.out.println("线程名称为：" + Thread.currentThread().getName()
                        + "在" + System.currentTimeMillis() + "进入printA");
                Thread.sleep(3000);
                System.out.println("线程名称为：" + Thread.currentThread().getName()
                        + "在" + System.currentTimeMillis() + "离开printA");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }
    }

    synchronized public static void printB() {
//        synchronized (Service.class){
            System.out.println("线程名称为：" + Thread.currentThread().getName() + "在"
                    + System.currentTimeMillis() + "进入printB");
            System.out.println("线程名称为：" + Thread.currentThread().getName() + "在"
                    + System.currentTimeMillis() + "离开printB");
//        }
    }
}
class ThreadA extends Thread {
    @Override
    public void run() {
        Service.printA();
    }

}


class ThreadB extends Thread {
    @Override
    public void run() {
        Service.printB();
    }
}