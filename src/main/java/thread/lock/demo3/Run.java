package thread.lock.demo3;

/**
 * synchronized (非this对象)
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/5/14 22:36
 */
public class Run {
    public static void main(String[] args) {
        Service service = new Service("test");
        ThreadA threadA = new ThreadA(service);
        ThreadB threadB = new ThreadB(service);
        threadA.start();
        threadB.start();
//        线程名称为：ThreadA在1526309739793进入同步块
//        线程名称为：ThreadA在1526309742794离开同步块
//        线程名称为：ThreadB在1526309742794进入同步块
//        线程名称为：ThreadB在1526309745795离开同步块
    }
}

class Service{
    String anyString;

    public Service(String anyString){
        this.anyString = anyString;
    }

    public void setUsernamePassword(String username,String password){
        try {
            // 这里线程争夺的是anyString的对象锁，两个线程有竞争同一对象锁的关系，出现同步
            // 如果对象实例A，线程1获得了对象A的对象锁，那么其他线程就不能进入需要获得对象实例A的对象锁才能访问的同步代码（包括同步方法和同步块）
            synchronized (anyString){
                System.out.println("线程名称为：" + Thread.currentThread().getName()
                        + "在" + System.currentTimeMillis() + "进入同步块");
                Thread.sleep(3000);
                System.out.println("线程名称为：" + Thread.currentThread().getName()
                        + "在" + System.currentTimeMillis() + "离开同步块");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class ThreadA extends Thread{
    private Service service;

    public ThreadA(Service service){
        super("ThreadA");
        this.service = service;
    }


    @Override
    public void run(){
        service.setUsernamePassword("A","AA");
    }
}
class ThreadB extends Thread{
    private Service service;

    public ThreadB(Service service){
        super("ThreadB");
        this.service = service;
    }


    @Override
    public void run(){
        service.setUsernamePassword("B","BB");
    }
}