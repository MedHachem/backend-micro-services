package com.example.notificationmanager.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // -------------------- AI GENERATED --------------------
    public static final String AI_GENERATED_QUEUE = "notification.generated";
    public static final String AI_EXCHANGE = "generatedExchange";
    public static final String AI_ROUTING_KEY = "generated.content";

    @Bean
    Queue aiGeneratedQueue() {
        return QueueBuilder.durable(AI_GENERATED_QUEUE).build();
    }

    @Bean
    DirectExchange aiExchange() {
        return new DirectExchange(AI_EXCHANGE);
    }

    @Bean
    Binding aiBinding(Queue aiGeneratedQueue, DirectExchange aiExchange) {
        return BindingBuilder.bind(aiGeneratedQueue).to(aiExchange).with(AI_ROUTING_KEY);
    }

    // -------------------- CONVERTER JSON --------------------
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
        return factory;
    }

}
