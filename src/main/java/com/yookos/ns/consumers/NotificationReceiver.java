package com.yookos.ns.consumers;

import com.yookos.ns.services.ProcessMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jome on 2016/01/19.
 */

@Component
public class NotificationReceiver implements MessageListener {
    @Autowired
    ProcessMessages processMessage;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onMessage(Message message) {
        log.info(new String(message.getBody()));
    }

}
