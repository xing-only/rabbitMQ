package com.xing.d_directModel.consume;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.xing.a_simpleQueue.util.ConnectUtil;

import java.io.IOException;

/**
 * @description:消费者一号，绑定一个队列
 * @author: DXX
 * @date: 2019/9/6 11:53
 */
public class Recv1 {

    private static final String QUEUE_NAME = "q_xing_direct_1";

    private static final String EXCHANGE_NAME = "exchange_xing_direct";

    public static void main(String[] args) throws Exception {

        //获取连接和信道
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"add");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"update");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"get");
        //同一时刻，服务器只会发一条信息给消费者
        channel.basicQos(1);
        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //false，表示手动确认
        channel.basicConsume(QUEUE_NAME,false,consumer);

        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费者一号：队列" + QUEUE_NAME + "信息：" + msg);
            //信息消费确认返回
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }
    }
}
