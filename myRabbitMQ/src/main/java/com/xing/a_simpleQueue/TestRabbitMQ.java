package com.xing.a_simpleQueue;

import com.xing.a_simpleQueue.consumer.Consumer;
import com.xing.a_simpleQueue.producer.Producer;

import java.io.IOException;

/**
 * @description:
 * @author: DXX
 * @date: 2019/9/5 15:53
 */
public class TestRabbitMQ {

    public static void main(String[] args) throws Exception {
        simpleQueue();
    }

    //简单队列
    public static void simpleQueue() throws IOException, InterruptedException {
        Producer pro = new Producer();
        //生产者发送信息
        pro.send("hello 你好啊");

        Consumer consumer = new Consumer();
        //消费者获取信息
        consumer.recv();
    }
}
