package com.hc.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQTool {

    public static  ConnectionFactory factory =  new ConnectionFactory();

    public Connection connection;

    public Channel channel;

    private static  ConnectionFactory getConnectionFactory (){
        // 1、创建连接工厂
        factory = new ConnectionFactory();
        // 2、设置连接属性
        factory.setHost("113.125.182.229");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        return  factory;
    }


    private Connection getConnection(String name) throws IOException, TimeoutException {
        connection   = getConnectionFactory().newConnection(name);
        return  connection;
    }


    public  Channel getChannel(String name) throws IOException, TimeoutException {
        Channel channel = getConnection(name).createChannel();
        return  channel;
    }

    public  void close(){
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
