package gali.rabbit.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * direct交换机生产者
 *
 * @author gali
 * @date 2021/02/06 20:46
 **/
public class DirectProductor {

    private final static String EXCHANGE_NAME="rabbit:mq03:exchange:e01";

    private static final String Queue_Name_01="rabbit:mq03:queue:q01";
    private static final String Queue_Name_02="rabbit:mq03:queue:q02";

    private final static String ROUTE_KEY_01 = "rabbit:mq03:routing:key:r01";
    private final static String ROUTE_KEY_02 = "rabbit:mq03:routing:key:r02";
    private final static String ROUTE_KEY_03 = "rabbit:mq03:routing:key:r03";

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

            //TODO：声明队列并将消息发送到队列中. 每个方法中的各个参数均可以 点进去 看看就一目了然. -> 此为direct-exchange消息模型
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            channel.queueBind(Queue_Name_01,EXCHANGE_NAME,ROUTE_KEY_01);
            channel.queueDeclare(Queue_Name_01, true, false, false, null);

            channel.queueDeclare(Queue_Name_02, true, false, false, null);
            channel.queueBind(Queue_Name_02,EXCHANGE_NAME,ROUTE_KEY_02);
            channel.queueBind(Queue_Name_02,EXCHANGE_NAME,ROUTE_KEY_03);

            String message1 =  "directExchange-publish我的消息-r01";
            String message2 = "directExchange-publish我的消息-r02";
            String message3 = "directExchange-publish我的消息-r03";

            channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY_01, null, message1.getBytes("UTF-8"));
            channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY_02, null, message2.getBytes("UTF-8"));
            channel.basicPublish(EXCHANGE_NAME, ROUTE_KEY_03, null, message3.getBytes("UTF-8"));

            System.out.println("生产者发送消息成功---> ");

            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
