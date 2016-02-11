package com.yookos.ns.consumers;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.yookos.ns.models.NotificationEvent;
import com.yookos.ns.services.ProcessMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jome on 2016/01/19.
 */

@Component
public class NotificationReceiver implements ChannelAwareMessageListener {
    @Autowired
    ProcessMessageEvent processMessage;

    @Autowired
    Gson gson;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            String msg = new String(message.getBody());


            NotificationEvent event = gson.fromJson(msg, NotificationEvent.class);
            log.info("Notification Event: {}", event);

            if (!event.getActor().getType().equals("SYSTEM") && event.getActor().getUsername().equals("pastorchrislive")) {
                processMessage.processPclNotificationEvent(event);
            } else {
                processMessage.processNotificationEvent(event);
            }

            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
