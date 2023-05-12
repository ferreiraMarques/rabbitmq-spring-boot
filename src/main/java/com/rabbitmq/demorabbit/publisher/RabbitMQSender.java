package com.rabbitmq.demorabbit.publisher;

import com.rabbitmq.demorabbit.dto.MenuOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private static Logger logger = LogManager.getLogger(RabbitMQSender.class.toString());

    @Value("${exchange}")
    private String exchange;

    @Value("${routing}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(MenuOrder order) {
        rabbitTemplate.convertAndSend(this.exchange, this.routingKey, order);
        logger.info("Sending message to queue " + order.toString());
    }
}
