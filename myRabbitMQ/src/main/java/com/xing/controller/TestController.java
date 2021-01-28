package com.xing.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * @description:
 * @author: DXX
 * @date: 2019/9/6 15:11
 */
@Controller
public class TestController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/test")
    public void test(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", "1");
        map.put("name", "pig");
        //根据key发送到对应的队列
        rabbitTemplate.convertAndSend("xing",map);
        map.put("id", "2");
        map.put("name", "dog");
        rabbitTemplate.convertAndSend("kong",map);
    }
}
