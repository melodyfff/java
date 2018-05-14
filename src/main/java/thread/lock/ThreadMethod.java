package thread.lock;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/5/14 19:37
 */
public class ThreadMethod {
    public static void main(String[] args) {
        LockMethod lockMethod = new LockMethod();
        BusiA busiA = new BusiA();
        BusiB busiB = new BusiB();
        busiA.deal(lockMethod);
        busiB.deal(lockMethod);
        busiA.start();
        busiB.start();
    }
}
