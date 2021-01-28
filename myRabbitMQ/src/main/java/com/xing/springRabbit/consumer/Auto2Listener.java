package com.xing.springRabbit.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @description: 整合spring的rabbitmq的消息队列
 * @author: DXX
 * @date: 2019/9/6 15:57
 */
@Component("auto2Listener")
public class Auto2Listener implements ChannelAwareMessageListener{

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            //msg就是rabbitmq传来的消息，需要的同学自己打印看一眼
            // 使用jackson解析
            JsonNode jsonData = MAPPER.readTree(message.getBody());
            System.out.println("消费者二号  绑定的 q_spring_xing_2 队列 获取的信息：" + jsonData.toString());
            System.out.println("消费者二号绑定的队列" + message.getMessageProperties().getConsumerQueue());
            boolean mqFlag=true;//业务处理
            //还有一个点就是如何获取mq消息的报文部分message？
            if(mqFlag){
                basicACK(message,channel);//处理正常--ack
            }else{
                basicNACK(message,channel);//处理异常--nack
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //正常消费掉后通知mq服务器移除此条mq
    private void basicACK(Message message,Channel channel){
        try{
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch(IOException e){
            System.out.println("通知服务器移除mq时异常，异常信息："+e);
        }
    }
    //处理异常，mq重回队列
    private void basicNACK(Message message,Channel channel){
        try{
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }catch(IOException e){
            System.out.println("mq重新进入服务器时出现异常，异常信息："+e);
        }
    }
}
