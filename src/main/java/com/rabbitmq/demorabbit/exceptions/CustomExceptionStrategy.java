package com.rabbitmq.demorabbit.exceptions;

import com.rabbitmq.demorabbit.dto.MenuOrder;
import com.rabbitmq.demorabbit.publisher.RabbitMQSender;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class CustomExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

    @Autowired
    private RabbitMQSender mQSender;

    @Override
    public boolean isFatal(Throwable throwable) {
        if (throwable.getCause() instanceof InvalidOrderException) {
            InvalidOrderException orderException = (InvalidOrderException) throwable.getCause();
            log.info("Exception has occurred : {} ", throwable.getMessage());
            MenuOrder order = orderException.getOrder();
            order.setOrderId(1);
            this.mQSender.send(order);
            return true;
        }
        return false;
    }
}
