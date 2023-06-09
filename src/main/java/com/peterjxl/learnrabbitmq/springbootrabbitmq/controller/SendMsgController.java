package com.peterjxl.learnrabbitmq.springbootrabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 发送延迟消息
 */

@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 开始发消息
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message){
        log.info("当前时间：{}, 发送一条信息给两个TTL队列：{}" , new Date(), message);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自ttl为10s的队列：" + message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自ttl为40s的队列：" + message);
    }

    // 开始发消息, 带过期时间
    @GetMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttlTime){
        log.info("当前时间：{}, 发送一条时长{}毫秒TTL信息给队列QC：{}" , new Date(), ttlTime, message);

        rabbitTemplate.convertAndSend("X", "XC", "消息来自ttl为" + ttlTime + "毫秒的队列：" + message, msg -> {
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    // 开始发消息, 基于插件的延迟消息
    @GetMapping("/sendDelayMsg/{message}/{delayTime}")
    public void sendDelayMsg(@PathVariable String message, @PathVariable Integer delayTime){
        log.info("当前时间：{}, 发送一条时长{}毫秒延迟信息给延迟队列delayed.queue：{}" , new Date(), delayTime, message);

        rabbitTemplate.convertAndSend("delayed.exchange", "delayed.routingkey", "消息来自delayed.exchange交换机的延迟队列：" + message, msg -> {
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        });
    }
}
