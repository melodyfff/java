package thread.lock;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/5/14 19:33
 */
public class LockMethod {
    public synchronized void busiA(){
        for(int i =0 ;i<1000;i++){
            System.out.println(Thread.currentThread()+" :"+i);
        }
    }
    public synchronized void busiB(){
        for(int i =0 ;i<1000;i++){
            System.out.println(Thread.currentThread()+" :"+i);
        }
    }
}
