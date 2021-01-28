package com.xing.e_topicModel.consume;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.xing.a_simpleQueue.util.ConnectUtil;

/**
 * @description:消费者二号，绑定一个队列，当前队列与交换机绑定由路由处理
 * @author: DXX
 * @date: 2019/9/6 14:25
 */
public class Recv2 {

    private static final String QUEUE_NAME = "q_xing_topic_2";

    private static final String EXCHANGE_NAME = "exchange_xing_topic";

    public static void main(String[] args) throws Exception {
        //获取连接和信道
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"*.*");
        //同一时刻，服务器只会发一条信息给消费者
        channel.basicQos(1);
        //定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列，手动返回完成
        channel.basicConsume(QUEUE_NAME,false,consumer);
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费者一号：队列" + QUEUE_NAME + "接收信息：" + msg);
            //确认返回
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
