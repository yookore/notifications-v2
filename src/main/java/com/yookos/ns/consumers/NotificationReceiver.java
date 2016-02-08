package com.yookos.ns.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.yookos.ns.domain.YookoreNotificationEvent;
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
            log.info("Incoming message: {}", msg);

//            //The message body has a consistent structure that maps to YookoreNotificationEvent
//            YookoreNotificationEvent yookoreNotificationEvent = gson.fromJson(msg, YookoreNotificationEvent.class);
//
//            //Send the notification for processing
//            if(!yookoreNotificationEvent.getActor().getType().equals("SYSTEM") && yookoreNotificationEvent.getActor().getUsername().equals("pastorchrislive")){
//                processMessage.processPclNotificationEvent(yookoreNotificationEvent);
//            }else{
//                processMessage.processNotificationEvent(yookoreNotificationEvent);
//            }
//
//            message.getMessageProperties().getDeliveryTag();
//            Long deliveryTag = message.getMessageProperties().getDeliveryTag();
//            log.info("Delivery tag: {}", deliveryTag);
//            channel.basicNack(deliveryTag, true, true);
            Thread.sleep(60000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
