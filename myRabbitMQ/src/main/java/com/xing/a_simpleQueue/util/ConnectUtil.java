package com.xing.a_simpleQueue.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @description:获取mq的连接
 * @author: DXX
 * @date: 2019/9/5 15:40
 */
public class ConnectUtil {

    public static Connection getConnection() throws IOException {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("127.0.0.1");
        //设置端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("hostxing");
        factory.setUsername("guest");
        factory.setPassword("guest");
        //通过工厂获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
}
