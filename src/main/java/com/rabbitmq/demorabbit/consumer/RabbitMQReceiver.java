package com.rabbitmq.demorabbit.consumer;

import com.rabbitmq.demorabbit.dto.MenuOrder;
import com.rabbitmq.demorabbit.exceptions.InvalidOrderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "name_queue", id = "listener", returnExceptions = "true")
public class RabbitMQReceiver {

    private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());

    @RabbitHandler
    public void receiver(MenuOrder order) throws InvalidOrderException {
        if (order.getOrderId() == 0) {
            throw new InvalidOrderException(order);
        } else {
            logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + order.getOrderIdentifier());
        }
    }
}
