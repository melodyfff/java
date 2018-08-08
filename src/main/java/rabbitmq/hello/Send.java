package rabbitmq.hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息发送
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/8/8 22:44
 */
public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置ip
        factory.setHost("192.168.201.132");
        factory.setUsername("xinchen");
        factory.setPassword("123");
        factory.setPort(5672);

        // 创建连接
        Connection connection = factory.newConnection();

        // 创建管道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String message = "Hello World!";

        // publish 消息
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());

        System.out.println(" [x] Sent '" + message + "'");

        // 关闭连接
        channel.close();
        connection.close();
    }
}
