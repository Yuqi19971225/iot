package com.iot.center.manager.config;

import com.iot.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：FYQ
 * @description：TODO
 * @date ：2022/10/25 20:47
 */
@Slf4j
@Configuration
public class TopicRabbitConfig {

    @Resource
    private ConnectionFactory connectionFactory;

    @Bean
    RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback((message -> {
            log.error("Send message({}) to exchange({}), routingKey({}) failed: {}", message.getMessage(), message.getExchange(), message.getRoutingKey(), message.getReplyText());
        }));
        return rabbitTemplate;
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    TopicExchange eventExchange() {
        return new TopicExchange(CommonConstant.Rabbit.TOPIC_EXCHANGE_EVENT, true, false);
    }

    @Bean
    Queue driverEventQueue() {
        Map<String, Object> arguments = new HashMap<>();
        // 15秒：15 * 1000 = 15000L
        arguments.put(CommonConstant.Rabbit.MESSAGE_TTL, 15000L);
        return new Queue(CommonConstant.Rabbit.QUEUE_DRIVER_EVENT, true, false, false, arguments);
    }

    @Bean
    Queue deviceEventQueue() {
        Map<String, Object> arguments = new HashMap<>();
        // 15秒：15 * 1000 = 15000L
        arguments.put(CommonConstant.Rabbit.MESSAGE_TTL, 15000L);
        return new Queue(CommonConstant.Rabbit.QUEUE_DEVICE_EVENT, true, false, false, arguments);
    }

    @Bean
    Binding deviceEventBinding(TopicExchange eventExchange, Queue deviceEventQueue) {
        return BindingBuilder
                .bind(deviceEventQueue)
                .to(eventExchange)
                .with(CommonConstant.Rabbit.ROUTING_DEVICE_EVENT_PREFIX + CommonConstant.Symbol.ASTERISK);
    }
}
