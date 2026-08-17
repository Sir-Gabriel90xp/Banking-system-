package com.bankingsystem.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RF-07.3: la auditoría se procesa de forma asíncrona mediante RabbitMQ.
 * ADR-011 (decisiones.md): exchange topic + JSON como formato de mensaje
 * (en vez de Java serialization nativa, para no acoplar consumidor y
 * productor a la misma versión de clase Java).
 */
@Configuration
public class RabbitMQConfig {

    public static final String AUDIT_EXCHANGE = "banking.audit.exchange";
    public static final String AUDIT_QUEUE = "banking.audit.queue";
    public static final String AUDIT_ROUTING_KEY = "audit.event";

    @Bean
    public TopicExchange auditExchange() {
        return new TopicExchange(AUDIT_EXCHANGE, true, false);
    }

    @Bean
    public Queue auditQueue() {
        return new Queue(AUDIT_QUEUE, true);
    }

    @Bean
    public Binding auditBinding(
            @Qualifier("auditQueue") Queue auditQueue,
            @Qualifier("auditExchange") TopicExchange auditExchange) {
        return BindingBuilder.bind(auditQueue).to(auditExchange).with(AUDIT_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            @Qualifier("jsonMessageConverter") MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}
