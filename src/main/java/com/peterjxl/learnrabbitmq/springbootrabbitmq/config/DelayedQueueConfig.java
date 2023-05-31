package com.peterjxl.learnrabbitmq.springbootrabbitmq.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayedQueueConfig {


    private static final String DELAYED_QUEUE_NAME = "delayed.queue";

    private static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";

    private static final String DELAYED_ROUTING_KEY = "delayed.routingkey";


    //声明队列
    @Bean
    public Queue delayedQueue(){
        return new Queue(DELAYED_QUEUE_NAME);
    }

    //声明交换机
    @Bean
    public CustomExchange delayedExchange(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");   //延迟类型为direct
        return new CustomExchange(
                DELAYED_EXCHANGE_NAME,  //交换机名称
                "x-delayed-message",    //交换机类型
                true,                   //是否持久化
                false,                  //是否自动删除
                args                    //参数
        );
    }

    //绑定
    @Bean
    public Binding delayedQueueBindingDelayedExchange(
            @Qualifier("delayedQueue") Queue delayedQueue,
            @Qualifier("delayedExchange") CustomExchange delayedExchange){
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }
}
