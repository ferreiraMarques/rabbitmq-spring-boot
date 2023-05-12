package com.rabbitmq.demorabbit.exceptions;

import com.rabbitmq.demorabbit.dto.MenuOrder;
import lombok.Getter;

@Getter
public class InvalidOrderException extends Exception {

    private static final long serialVersionUID = -3154618962130084535L;

    private MenuOrder order;

    public InvalidOrderException(MenuOrder order) {
        this.order = order;
    }
    
}
