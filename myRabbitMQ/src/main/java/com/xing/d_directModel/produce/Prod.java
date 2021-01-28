package com.xing.d_directModel.produce;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xing.a_simpleQueue.util.ConnectUtil;

import java.io.IOException;

/**
 * @description: 路由模式，（生产者发送的消息，通过交换机，路由到指定的队列中）
 * @author: DXX
 * @date: 2019/9/6 11:48
 */
public class Prod {

    private static final String EXCHANGE_NAME = "exchange_xing_direct";

    public static void main(String[] args) throws Exception {
        //获取连接和信道
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        //消息内容
        String msg = "发送的是路由信息，到指定的队列。。。";
        channel.basicPublish(EXCHANGE_NAME,"get",null,msg.getBytes());
        System.out.println("send[]：" + msg);

        channel.close();
        connection.close();
    }
}
