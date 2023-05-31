package com.peterjxl.learnrabbitmq.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 发布确认高级
 */
@Configuration
public class ConfirmConfig {

    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    public static final String CONFIRM_ROUTING_KEY = "key1";

    //备份交换机
    public static final String BACKUP_EXCHANGE_NAME = "backup.exchange";
    //备份队列
    public static final String BACKUP_QUEUE_NAME = "backup.queue";
    //报警队列
    public static final String WARNING_QUEUE_NAME = "warning.queue";


    // 声明交换机
    @Bean("confirmExchange")
    public DirectExchange confirmExchange() {
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME)
                // 是否持久化
                .durable(true)
                // 是否自动删除
                .autoDelete()
                // 备份交换机
                .withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME)
                .build();
    }

    @Bean("confirmQueue")
    public Queue confirmQueue() {
        return new Queue(CONFIRM_QUEUE_NAME);
    }

    // 绑定
    @Bean
    public Binding bindingConfirmQueue(
            @Qualifier("confirmQueue") Queue confirmQueue,
            @Qualifier("confirmExchange") DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

    // 备份交换机
    @Bean("backupExchange")
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    // 备份队列
    @Bean("backupQueue")
    public Queue backupQueue() {
        return new Queue(BACKUP_QUEUE_NAME);
    }

    // 报警队列
    @Bean("warningQueue")
    public Queue warningQueue() {
        return new Queue(WARNING_QUEUE_NAME);
    }

    // 绑定
    @Bean
    public Binding bindingBackupQueue(
            @Qualifier("backupQueue") Queue backupQueue,
            @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    // 绑定
    @Bean
    public Binding bindingWarningQueue(
            @Qualifier("warningQueue") Queue warningQueue,
            @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }


}
