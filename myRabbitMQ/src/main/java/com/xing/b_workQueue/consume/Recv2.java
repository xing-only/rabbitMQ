package com.xing.b_workQueue.consume;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.xing.a_simpleQueue.util.ConnectUtil;

/**
 * @description:消费者2号
 * @author: DXX
 * @date: 2019/9/5 17:06
 */
public class Recv2 {

    //队列名称
    private final static String QUEUE_NAME = "q_xing_work";

    public static void main(String[] argv) throws Exception {
        //获取连接以及信道
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //同一时刻服务器只会发一条信息给消费者
        channel.basicQos(1);
        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(QUEUE_NAME,false,consumer);
        //获取消息
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费者2号--获取的消息队列的信息：" + msg);
            //睡眠1秒
            Thread.sleep(1000);
            //返回确认状态，注释掉表示自动确认模式
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }
    }
}
