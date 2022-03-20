package exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 简单队列生产者
 * 全播
 */
public class FanoutProducer {

    public static void main(String[] args) {
        // 1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2、设置连接属性
        factory.setHost("113.125.182.229");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        Connection connection = null;
        Channel channel = null;

        try {


            connection = factory.newConnection("生产者");

            // 4、从链接中创建通道
            channel = connection.createChannel();

            // 内存、磁盘预警时用
            System.out.println("按回车继续");
            System.in.read();

            // 消息内容
            String message = "fanout-query-1";
            // 发送持久化消息到routing_test交换器上
            channel.basicPublish("fanout-test", "fanout-query-1", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("消息 " + message + " 已发送！");


            // 内存、池畔预警时用
            System.out.println("按回车结束");
            System.in.read();

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            // 7、关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }

            // 8、关闭连接
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
