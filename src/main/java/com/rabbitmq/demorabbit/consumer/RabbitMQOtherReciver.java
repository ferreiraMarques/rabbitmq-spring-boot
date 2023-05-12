package com.rabbitmq.demorabbit.consumer;

import com.rabbitmq.demorabbit.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "queue_json", id = "other_listener")
public class RabbitMQOtherReciver {

    private static Logger logger = LogManager.getLogger(RabbitMQOtherReciver.class.toString());

    @RabbitHandler
    public void receiver(User user) {
        logger.info("MenuOrder listener invoked - Consuming Message with MenuOrder Identifier : " + user.getFirstName());
    }

}
