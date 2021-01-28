package com.xing.b_workQueue.produce;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.xing.a_simpleQueue.util.ConnectUtil;

/**
 * @description:生产者（向队列中发送100条消息）
 * @author: DXX
 * @date: 2019/9/5 17:15
 */
public class Prod {

    private final static String QUEUE_NAME = "q_xing_work";

    public static void main(String[] argv) throws Exception {
        //获取连接和信道
        Connection connection = ConnectUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        for(int i=1; i<=100; i++){
            String msg = "生产者发出的信息：" + i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("send[]" + msg);

            Thread.sleep(i*10);
        }
        channel.close();
        connection.close();

    }
}
