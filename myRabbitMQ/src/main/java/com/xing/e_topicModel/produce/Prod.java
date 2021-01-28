package com.xing.e_topicModel.produce;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xing.a_simpleQueue.util.ConnectUtil;

import java.io.IOException;

/**
 * @description:生产者，声明交换机，以及发送的信息，消息key的设置
 * @author: DXX
 * @date: 2019/9/6 14:25
 */
public class Prod {

    private static final String EXCHANGE_NAME = "exchange_xing_topic";

    public static void main(String[] args) throws Exception {
        //获取连接和信道
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        //消息内容
        String msg = "来一个路由模式。。。";
        channel.basicPublish(EXCHANGE_NAME,"topic.toptop",null,msg.getBytes());
        System.out.println("send[]：" + msg);

        channel.close();
        connection.close();
    }
}
