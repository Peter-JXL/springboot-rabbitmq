package com.peterjxl.learnrabbitmq.springbootrabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    // 开始发消息, 测试确认
    @GetMapping("/sendMessage/{message}")
    public void sendConfirmMsg(@PathVariable String message){
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend("confirm.exchange", "key1", message + "key1", correlationData);
        log.info("发送时间: {}, 发送内容: {}, routing: key1", new Date(), message);

        CorrelationData correlationData2 = new CorrelationData("2");
        rabbitTemplate.convertAndSend("confirm.exchange", "key123", message+ "key12", correlationData2);
        log.info("发送时间: {}, 发送内容: {}, routing: key2", new Date(), message);
    }
}
