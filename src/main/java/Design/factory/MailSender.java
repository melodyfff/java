package Design.factory;

/**
 * Created by xinchen on 2017/4/27.
 */
public class MailSender implements Sender {
    @Override
    public void Send() {
        System.out.println("mail sender");
    }
}
