package com.xing.a_simpleQueue.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xing.a_simpleQueue.util.ConnectUtil;

import java.io.IOException;

/**
 * @description: 生产者
 * @author: DXX
 * @date: 2019/9/5 15:47
 */
public class Producer {

    //队列名称
    private final static String QUEUE_NAME = "q_xing01";

    //生产者发送消息到队列
    public void send(String msg) throws IOException {
        //获取连接以及mq通道
        Connection connection = ConnectUtil.getConnection();
        //从连接中创建信道
        Channel channel = connection.createChannel();
        //声明（创建队列）
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //消息内容
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println("send[]" + msg);
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
