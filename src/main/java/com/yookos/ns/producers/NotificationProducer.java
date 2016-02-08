package com.yookos.ns.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jome on 2016/01/19.
 */

@Component
public class NotificationProducer {
    private final AtomicInteger counter = new AtomicInteger();
    @Autowired
    private volatile RabbitTemplate template;

    public void sendMessage() {
        System.out.print("Sending message...");
        template.convertAndSend("Hello World " + counter.incrementAndGet());
    }
}
