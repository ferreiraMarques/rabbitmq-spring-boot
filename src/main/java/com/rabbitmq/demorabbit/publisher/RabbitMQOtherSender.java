package com.rabbitmq.demorabbit.publisher;

import com.rabbitmq.demorabbit.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQOtherSender {

    private static Logger logger = LogManager.getLogger(RabbitMQOtherSender.class.toString());

    @Value("${exchange}")
    private String exchange;

    @Value("${otherRouting}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQOtherSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(User user) {
        rabbitTemplate.convertAndSend(this.exchange, this.routingKey, user);
        logger.info("Sending message to queue " + user.toString());
    }
}
