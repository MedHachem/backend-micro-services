package com.example.notificationmanager.Configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // -------------------- EMAIL --------------------
    public static final String EMAIL_QUEUE = "user.notifications";
    public static final String EMAIL_EXCHANGE = "userExchange";
    public static final String EMAIL_ROUTING_KEY = "user.registered";

    @Bean
    Queue emailQueue() {
        return QueueBuilder.durable(EMAIL_QUEUE).build();
    }

    @Bean
    DirectExchange emailExchange() {
        return new DirectExchange(EMAIL_EXCHANGE);
    }

    @Bean
    Binding emailBinding(Queue emailQueue, DirectExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with(EMAIL_ROUTING_KEY);
    }

    // -------------------- SMS --------------------
    public static final String SMS_QUEUE = "sms-queue";
    public static final String SMS_EXCHANGE = "smsExchange";
    public static final String SMS_ROUTING_KEY = "sms.send";

    @Bean
    Queue smsQueue() {
        return QueueBuilder.durable(SMS_QUEUE).build();
    }

    @Bean
    DirectExchange smsExchange() {
        return new DirectExchange(SMS_EXCHANGE);
    }

    @Bean
    Binding smsBinding(Queue smsQueue, DirectExchange smsExchange) {
        return BindingBuilder.bind(smsQueue).to(smsExchange).with(SMS_ROUTING_KEY);
    }

    // -------------------- CONVERTEUR JSON --------------------
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
