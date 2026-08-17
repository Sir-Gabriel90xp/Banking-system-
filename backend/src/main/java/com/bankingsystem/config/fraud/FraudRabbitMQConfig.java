package com.bankingsystem.config.fraud;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Topología RabbitMQ para el flujo de detección de fraude (RF-08.4, CU-11).
 *
 * Se define en un archivo propio, separado del RabbitMQConfig.java ya
 * existente del módulo de Auditoría, para no editar a ciegas un archivo
 * cuyo contenido actual no tengo. Si tu proyecto centraliza toda la
 * topología de RabbitMQ en un único @Configuration, estos @Bean se pueden
 * mover ahí sin cambiar su comportamiento.
 */
@Configuration
@EnableConfigurationProperties(FraudRulesProperties.class)
public class FraudRabbitMQConfig {

    public static final String FRAUD_EXCHANGE = "banking.fraud.exchange";
    public static final String FRAUD_QUEUE = "banking.fraud.check.queue";
    public static final String FRAUD_ROUTING_KEY = "fraud.check";

    @Bean
    public TopicExchange fraudExchange() {
        return new TopicExchange(FRAUD_EXCHANGE);
    }

    @Bean
    public Queue fraudCheckQueue() {
        return new Queue(FRAUD_QUEUE, true);
    }

    @Bean
    public Binding fraudCheckBinding(
            @Qualifier("fraudCheckQueue") Queue fraudCheckQueue,
            @Qualifier("fraudExchange") TopicExchange fraudExchange) {
        return BindingBuilder.bind(fraudCheckQueue).to(fraudExchange).with(FRAUD_ROUTING_KEY);
    }
}
