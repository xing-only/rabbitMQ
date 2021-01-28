package com.xing.c_fanoutModel.produce;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xing.a_simpleQueue.util.ConnectUtil;

/**
 * @description:消息的生产者，将消息发送给交换机（广播模式：测试一条信息多个消费者接收）
 * @author: DXX
 * @date: 2019/9/6 10:51
 */
public class Prod {

    private static final String EXCHANGE_NAME = "exchange_xing_fanout";

    public static void main(String[] args) throws Exception {
        //获取连接和信道
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        //发送消息
        String msg = "发送的广播信息。。。";
        //第一个参数，交换机名称
        //第二个参数，消息key
        //第三个参数，
        //第四个参数，信息
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        System.out.println("send[]：" + msg);

        /**
         * 注意：消息发送到没有队列绑定的交换机时，消息将丢失，因为，交换机没有存储消息的能力，消息只能存在在队列中。
         * */

        channel.close();
        connection.close();
    }
}
