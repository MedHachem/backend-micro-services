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
    public static final String USER_ROUTING_KEY = "user.#";

    // --- DEAD LETTER CONFIG ---
    public static final String DLX_EXCHANGE = USER_EXCHANGE + ".dlx";
    public static final String DLQ = USER_QUEUE + ".dlq";
    public static final String DLQ_ROUTING_KEY = USER_QUEUE + ".dlq";

    @Bean
    public Queue userQueue() {
        return QueueBuilder.durable(USER_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(USER_EXCHANGE);   // Topic pour routing key générique
    }

    @Bean
    public Binding userBinding() {
        return BindingBuilder
                .bind(userQueue())
                .to(userExchange())
                .with(USER_ROUTING_KEY);
    }

    // --- DEAD LETTER EXCHANGE ---
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    // --- DEAD LETTER QUEUE ---
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    // --- BIND DLQ ---
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DLQ_ROUTING_KEY);
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
