package Design.factory;

/**
 *
 * Created by xinchen on 2017/4/27.
 */
public class FactoryTest {
    public static void main(String[] args) {
        SendFactory factory = new SendFactory();
        Sender sender = factory.produce("mail");
        sender.Send();
        Sender sender1 = factory.produce("sms");
        sender1.Send();
    }
}
