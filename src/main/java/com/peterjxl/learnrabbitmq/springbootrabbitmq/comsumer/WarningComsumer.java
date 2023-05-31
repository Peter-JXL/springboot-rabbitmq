package com.peterjxl.learnrabbitmq.springbootrabbitmq.comsumer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarningComsumer {

    @RabbitListener(queues = "warning.queue")
    public void receiveWarning(Message msg) {
        log.error("发现不可路由消息：{}", new String(msg.getBody()));
    }

}
