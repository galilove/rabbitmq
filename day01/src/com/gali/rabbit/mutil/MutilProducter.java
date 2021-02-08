package gali.rabbit.mutil;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消费者
 *
 * @author gali
 * @date 2021/02/06 19:53
 **/
public class MutilProducter {
    private final static String EXCHANGE_NAME="rabbit:mq02:exchange:e01";
    private final static String QUEUE_NAME_01 = "rabbit:mq02:queue:q01";
    private final static String ROUTE_KEY_1="rabbit:mq02:routing:key:r01";

    public static void main(String[] argv) throws Exception {

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");

            //TODO：点 ConnectionFactory 进去看看 会发现其中的userName跟password默认就是使用我下面两个设置，所以可以不需要设置
            //factory.setUsername("guest");
            //factory.setPassword("guest");

            Connection connection = factory.newConnection();
            //TODO：channel贯穿通信的始终，连接了 "消息发送端-队列-消息接收端"
            Channel channel = connection.createChannel();

            //TODO：声明队列并将消息发送到队列中. 每个方法中的各个参数均可以 点进去 看看就一目了然. -> 此为简单的消息模型
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            channel.queueBind(QUEUE_NAME_01,EXCHANGE_NAME,ROUTE_KEY_1);
            String message = "我的第一条exchange消息!";

            channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY_1, null, message.getBytes("UTF-8"));

            System.out.println("生产者发送消息成功---> ");

            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
