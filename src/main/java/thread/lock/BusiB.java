package thread.lock;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/5/14 19:34
 */
public class BusiB extends Thread{
    LockMethod lockMethod;
    void deal(LockMethod lockMethod){
        this.lockMethod = lockMethod;
    }

    @Override
    public void run(){
//        super.run();
        System.out.println("lockMethod hashcode:"+lockMethod.hashCode());
        lockMethod.busiB();
    }
}
