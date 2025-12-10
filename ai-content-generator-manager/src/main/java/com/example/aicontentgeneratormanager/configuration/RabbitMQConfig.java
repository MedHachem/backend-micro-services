package com.example.aicontentgeneratormanager.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class RabbitMQConfig {
    public static final String USER_QUEUE = "user.notifications";
    public static final String USER_EXCHANGE = "userExchange";
    public static final String USER_ROUTING_KEY = "user.registered";
    @Bean
    Queue userQueue() {
        return QueueBuilder.durable(USER_QUEUE).build();
    }

    @Bean
    DirectExchange userExchange() {
        return new DirectExchange(USER_EXCHANGE);
    }

    @Bean
    Binding userBinding(Queue userQueue, DirectExchange userExchange) {
        return BindingBuilder.bind(userQueue).to(userExchange).with(USER_ROUTING_KEY);
    }

    public static final String AI_GENERATED_EXCHANGE = "generatedExchange";

    @Bean
    DirectExchange aiGeneratedExchange() {
        return new DirectExchange(AI_GENERATED_EXCHANGE);
    }

    @Bean
    public org.springframework.amqp.support.converter.MessageConverter jsonMessageConverter() {
        return new org.springframework.amqp.support.converter.Jackson2JsonMessageConverter();
    }


    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setAdviceChain(RetryInterceptorBuilder.stateless()
                .maxAttempts(5)                  // nombre max de tentatives
                .backOffOptions(5000, 2.0, 30000) // initial 5s, multiplier 2x, max 30s
                .recoverer(new RejectAndDontRequeueRecoverer()) // après échec, le message va dans DLQ
                .build());
        return factory;
    }
}
