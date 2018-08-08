package rabbitmq.hello;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息接收
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/8/8 23:11
 */
public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.201.132");
        factory.setUsername("xinchen");
        factory.setPassword("123");
        factory.setPort(5672);

        // 创建连接
        Connection connection = factory.newConnection();
        // 创建管道
        Channel channel = connection.createChannel();
        // 设置队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


        // 消费者
        Consumer consumer = new DefaultConsumer(channel) {
            // 交货
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };

        // 进行接收消息
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
