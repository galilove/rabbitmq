package gali.rabbit.direct;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消费者
 *
 * @author gali
 * @date 2021/02/06 18:50
 **/
public class DirectConsumer {
    private final static String QUEUE_NAME_01 = "rabbit:mq03:queue:q01";
    private final static String EXCHANGE_NAME="rabbit:mq03:exchange:e01";
    private final static String ROUTE_KEY_01 = "rabbit:mq03:routing:key:r01";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //TODO：fanout-exchange无意识分发消息模型
        channel.queueDeclare(QUEUE_NAME_01, true, false, false, null);
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueBind(QUEUE_NAME_01,EXCHANGE_NAME,ROUTE_KEY_01);

        System.out.println("consumer1 [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("我是消费者1接受到消息---> "+message);
            }
        };
        channel.basicConsume(QUEUE_NAME_01, true, consumer);
    }
}
