package Design.factory;

/**
 * Created by xinchen on 2017/4/27.
 */
public class SmsSender implements Sender {
    @Override
    public void Send() {
        System.out.println("Sms sender");
    }
}
