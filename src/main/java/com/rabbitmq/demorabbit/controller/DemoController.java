package com.rabbitmq.demorabbit.controller;

import com.rabbitmq.demorabbit.dto.MenuOrder;
import com.rabbitmq.demorabbit.dto.User;
import com.rabbitmq.demorabbit.publisher.RabbitMQOtherSender;
import com.rabbitmq.demorabbit.publisher.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    private RabbitMQSender mQSender;

    private RabbitMQOtherSender mQOtherSender;

    @Autowired
    public DemoController(RabbitMQSender mQSender, RabbitMQOtherSender mQOtherSender) {
        this.mQSender = mQSender;
        this.mQOtherSender = mQOtherSender;
    }

    @PostMapping(value = "/sender")
    public String producer(@RequestBody MenuOrder order) {
        mQSender.send(order);
        return "send message";
    }

    @PostMapping(value = "/user")
    public String sendUser(@RequestBody User user) {
        mQOtherSender.send(user);
        return "send message";
    }
}
