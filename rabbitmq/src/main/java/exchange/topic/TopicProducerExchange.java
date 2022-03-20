package exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicProducerExchange {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2、设置连接属性
        factory.setHost("113.125.182.229");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        //2、通过工厂获取一个连接

        Connection connection = factory.newConnection("生产者");


        //3、通过connection 创建 Channel
        Channel channel=connection.createChannel();

        //4、通过Channel发送数据
        String message="Hello RabbitMQ Topic Exchange Message";
        //指定交换机和路由键
        String exchangeName="topic-test";
        String routeKey1="user.save";
        String routeKey2="user.update";
        String routeKey3="user.info.detail";
        channel.basicPublish(exchangeName,routeKey1,null,message.getBytes());
        channel.basicPublish(exchangeName,routeKey2,null,message.getBytes());
        channel.basicPublish(exchangeName,routeKey3,null,message.getBytes());

        //5、关闭连接
        channel.close();
        connection.close();
    }
}