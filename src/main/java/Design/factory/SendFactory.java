package Design.factory;

/**
 * Created by xinchen on 2017/4/27.
 */
public class SendFactory {
    public Sender produce(String type){
        if("mail".equals(type)){
            return new MailSender();
        }else if ("sms".equals(type)){
            return new SmsSender();
        }else {
            System.out.println("type error!!!");
            return null;
        }
    }
}
