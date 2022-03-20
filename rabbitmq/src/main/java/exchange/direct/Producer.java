package exchange.direct;

import com.hc.exchange.MQTool;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 简单队列生产者
 * 使用RabbitMQ的默认交换器发送消息
 */
public class Producer {

    public static void main(String[] args) {
        MQTool mqTool = new MQTool();
        try {
            // 4、从链接中创建通道
            Channel  channel = mqTool.getChannel("生产者");
            // 内存、磁盘预警时用
            System.out.println("按回车继续");
            System.in.read();
            // 消息内容
            String message = "Hello B";
            // 发送持久化消息到routing_test交换器上
            channel.basicPublish("direct-test", "888", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("消息 " + message + " 已发送！");

            // 内存、池畔预警时用
            System.out.println("按回车结束");
            System.in.read();

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            mqTool.close();
        }
    }
}
