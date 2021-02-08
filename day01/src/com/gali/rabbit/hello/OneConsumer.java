package gali.rabbit.hello;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消费者
 *
 * @author gali
 * @date 2021/02/06 18:50
 **/
public class OneConsumer {
    private final static String QUEUE_NAME = "rabbit:mq01:queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("消费端接受到消息---> "+message);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
