/**
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
 */
package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Description：通过Runnable实现共享资源
 * Created by ChenXin on 2016/10/31.
 */
class MyThread implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(MyThread.class);

    private  int count = 100;

    @Override
    public void run() {
        for (int i = 1; i < 100; i++) {
            if (this.count > 0) {
//                System.out.println(Thread.currentThread().getName() + "输出---->" + (this.count--));
                log.info(Thread.currentThread().getName() + "输出---->" + (this.count--));
            }
        }
    }
}
public class RunnableShare {
    public static void main(String[] args) {
        MyThread mT1 = new MyThread();
//        System.out.println("当前线程：" + Thread.currentThread().getName());
        new Thread(mT1, "线程1").start();
        new Thread(mT1, "线程2").start();
    }
}