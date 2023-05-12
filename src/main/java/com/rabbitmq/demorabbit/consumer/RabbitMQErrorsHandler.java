package com.rabbitmq.demorabbit.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "deadletter", id = "deadletter_listeners")
public class RabbitMQErrorsHandler {

    private static Logger logger = LogManager.getLogger(RabbitMQErrorsHandler.class.toString());

    @RabbitHandler
    public void receiver(Object object)  {
        logger.info("error listener invoked - Consuming Message with MenuOrder Identifier : " + object.toString());
    }
}
