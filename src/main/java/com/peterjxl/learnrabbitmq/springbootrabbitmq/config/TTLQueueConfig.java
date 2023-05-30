package com.peterjxl.learnrabbitmq.springbootrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * TTL队列配置
 */
@Configuration
public class TTLQueueConfig {

    //普通交换机
    private static final String X_EXCHANGE = "X";

    //死信交换机
    private static final String Y_DEAD_LETTER_EXCHANGE = "Y";

    //普通队列
    private static final String QUEUE_A = "QA";
    private static final String QUEUE_B = "QB";

    private static final String QUEUE_C = "QC";

    //死信队列
    private static final String DEAD_LETTER_QUEUE = "QD";

    // 声明xExchange
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    // 声明yExchange
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    // 声明普通队列A ttl为10s
    @Bean("queueA")
    public Queue queueA(){
        Map<String, Object> args = new HashMap<>(3);
        // 统一设置队列中的所有消息的过期时间，单位毫秒
        args.put("x-message-ttl", 10000);

        // 统一设置队列的死信交换机
        args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);

        // 统一设置队列的死信routingKey
        args.put("x-dead-letter-routing-key", "YD");

        return QueueBuilder.durable(QUEUE_A).withArguments(args).build();
    }

    // 声明普通队列B ttl为40s
    @Bean("queueB")
    public Queue queueB(){
        Map<String, Object> args = new HashMap<>(3);
        // 统一设置队列中的所有消息的过期时间，单位毫秒
        args.put("x-message-ttl", 40000);

        // 统一设置队列的死信交换机
        args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);

        // 统一设置队列的死信routingKey
        args.put("x-dead-letter-routing-key", "YD");

        return QueueBuilder.durable(QUEUE_B).withArguments(args).build();
    }

    // 声明普通队列C
    @Bean("queueC")
    public Queue queueC(){
        Map<String, Object> args = new HashMap<>(3);

        // 统一设置队列的死信交换机
        args.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);

        // 统一设置队列的死信routingKey
        args.put("x-dead-letter-routing-key", "YD");

        return QueueBuilder.durable(QUEUE_C).withArguments(args).build();
    }

    // 声明死信队列QD
    @Bean("queueD")
    public Queue queueD(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    // 声明QA绑定关系
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA, @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    // 声明QB绑定关系
    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB, @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    // 声明QD绑定关系
    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD, @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }

    // 声明QC绑定关系
    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC, @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }
}
