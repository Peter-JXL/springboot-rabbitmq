package com.peterjxl.learnrabbitmq.springbootrabbitmq.comsumer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 发布确认高级-消费者
 */
@Slf4j
@Component
public class ConfirmConsumer {

    @RabbitListener(queues = "confirm.queue")
    public void receiveConfirmMessage(Message msg) {
        log.info("接收时间: {}, 接收内容: {}", new Date(), new String(msg.getBody()));
    }
}
