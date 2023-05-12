package com.rabbitmq.demorabbit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.demorabbit.exceptions.CustomExceptionStrategy;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.util.ErrorHandler;

@Configuration
public class RabbitMQConfig {

    @Value("${queue}")
    private String queue;

    @Value("${otherQueue}")
    private String otherQueue;

    @Value("${exchange}")
    private String exchange;

    @Value("${routing}")
    private String routingKey;

    @Value("${otherRouting}")
    private String otherRoutingKey;

    @Value("${deadLetterQueue}")
    private String deadLetterQueue;

    @Value("${deadLetterExchange}")
    private String deadLetterExchange;

    @Value("${deadLetterRouting}")
    private String deadLetterRouting;

    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    @Bean
    public Queue otherJsonQueue() {
        return new Queue(otherQueue);
    }

    @Bean
    public Queue dlq() {
        return new Queue(deadLetterQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchange, true, false);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public Binding jsonBinding() {
        return BindingBuilder
                .bind(otherJsonQueue())
                .to(exchange())
                .with(otherRoutingKey);
    }

    @Bean
    public Binding DLQbinding() {
        return BindingBuilder
                .bind(dlq())
                .to(deadLetterExchange())
                .with(deadLetterRouting);
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(customExceptionStrategy());
    }

    @Bean
    public FatalExceptionStrategy customExceptionStrategy() {
        return new CustomExceptionStrategy();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean("rabbitListenerContainerFactory")
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setErrorHandler(errorHandler());
        factory.setDefaultRequeueRejected(false);
        return factory;
    }

}
