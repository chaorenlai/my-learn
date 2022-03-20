package exchange.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class ConsumerTopicExchange {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1、创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2、设置连接属性
        connectionFactory.setHost("113.125.182.229");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");

        //2、通过工厂获取一个连接
        Connection connection = connectionFactory.newConnection("消费者");

        //3、通过connection 创建 Channel
        Channel channel=connection.createChannel();

        //4、声明 交换机  队列 路由键
        String exchangeName="topic-test";//和生产者一致
        // 交换机类型
        String exchangeType="topic";
        String queueName="topic-query-1";
//        String routeKey="user.*"; // 只能匹配 user.abc user.123
        String routeKey="user.#";//能匹配到 user.abc  user.123 user.abc.123
        // 4.1 交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);
        // 4.2 队列
        channel.queueDeclare(queueName,true,false,
                true,null);
        // 4.3 绑定 交换机 队列 和 路由键
        channel.queueBind(queueName,exchangeName,routeKey);

        // 定义消息接收回调对象
        DeliverCallback callback = new DeliverCallback() {
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println(queueName + " 收到消息：" + new String(message.getBody(), "UTF-8"));
            }
        };
        // 监听队列
        channel.basicConsume(queueName, true, callback, new CancelCallback() {
            public void handle(String consumerTag) throws IOException {
            }
        });

    }
}