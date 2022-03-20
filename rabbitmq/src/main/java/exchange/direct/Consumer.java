package exchange.direct;

import com.hc.exchange.MQTool;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由--消费者
 *
 * 消费者通过一个临时队列和交换f器绑定，接收发送到交换器上的消息
 */
public class Consumer {
    private static Runnable receive = new Runnable() {
        public void run() {
            MQTool mqTool = new MQTool();
            //监听对应队列名称
            final String queueName ="direct-query-1";
            try {
                // 4、从链接中创建通道
                Channel channel = mqTool.getChannel("消费者");
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

                System.out.println(queueName + " 开始接收消息");
                System.in.read();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } finally {
                mqTool.close();
            }
        }
    };

    public static void main(String[] args) {
        new Thread(receive).start();
    }

}
