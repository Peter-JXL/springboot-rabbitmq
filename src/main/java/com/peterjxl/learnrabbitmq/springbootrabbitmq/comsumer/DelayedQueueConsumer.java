package com.peterjxl.learnrabbitmq.springbootrabbitmq.comsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * 队列消费者，基于插件的延迟队列
 */


@Slf4j
@Component
public class DelayedQueueConsumer {

     @RabbitListener(queues = "delayed.queue")
     public void receiveDelayedQueue(Message message){
         String msg = new String(message.getBody());
         log.info("当前时间：{},收到延迟队列的消息：{}", new Date(), msg);
     }
}
