package com.xing.a_simpleQueue.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.xing.a_simpleQueue.util.ConnectUtil;

import java.io.IOException;

/**
 * @description:消费者
 * @author: DXX
 * @date: 2019/9/5 16:23
 */
public class Consumer {

    //队列名称
    private final static String QUEUE_NAME = "q_xing01";

    public void recv() throws IOException, InterruptedException {
        //获取连接以及信道
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("获取的消息队列的信息：" + msg);
        }
    }
}
