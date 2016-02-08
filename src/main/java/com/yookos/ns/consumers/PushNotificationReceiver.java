package com.yookos.ns.consumers;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.yookos.ns.domain.YookoreNotificationEvent;
import com.yookos.ns.domain.YookoreNotificationItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jome on 2016/02/08.
 */
public class PushNotificationReceiver implements ChannelAwareMessageListener {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Gson gson;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String msg = new String(message.getBody());
        log.info("Incoming message: {}", msg);
        YookoreNotificationEvent item = gson.fromJson(msg, YookoreNotificationEvent.class);
        log.info(item.toString());
    }
}
