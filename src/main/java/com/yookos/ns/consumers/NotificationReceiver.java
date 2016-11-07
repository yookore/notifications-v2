package com.yookos.ns.consumers;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.yookos.ns.models.FriendRequestAction;
import com.yookos.ns.models.NotificationEvent;
import com.yookos.ns.models.YookoreUser;
import com.yookos.ns.services.ProcessMessageEvent;
import org.json.JSONObject;
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
        NotificationEvent event;
        try {
            String msg = new String(message.getBody());

            log.info("Message received: {}", msg);

            JSONObject jsonObject = new JSONObject(msg);
            if (jsonObject.has("type")) {
                log.info("Action Type: {}", jsonObject.getString("type"));
                event = new NotificationEvent();

                YookoreUser actor = new YookoreUser();
                actor.setFirstName(jsonObject.getJSONObject("actor").getString("firstname"));
                actor.setLastName(jsonObject.getJSONObject("actor").getString("lastname"));
                actor.setFullNames(actor.getFirstName() + " " + actor.getLastName());
                actor.setUserId(jsonObject.getJSONObject("actor").getString("userid"));
                actor.setType("USER");
                actor.setUsername(jsonObject.getJSONObject("actor").getString("username"));

                YookoreUser recipient = new YookoreUser();
                recipient.setFirstName(jsonObject.getJSONObject("target").getString("firstname"));
                recipient.setLastName(jsonObject.getJSONObject("target").getString("lastname"));
                recipient.setFullNames(recipient.getFirstName() + " " + recipient.getLastName());
                recipient.setUserId(jsonObject.getJSONObject("target").getString("userid"));
                recipient.setType("USER");
                recipient.setUsername(jsonObject.getJSONObject("target").getString("username"));

                event.setAction(jsonObject.getString("type"));
                event.setActor(actor);
                event.setRecipient(recipient);


            } else {
                log.info("Action Type: {}", jsonObject.getString("action"));
                event = gson.fromJson(msg, NotificationEvent.class);
            }


            log.info("Notification Event: {}", event);

            if (!event.getActor().getType().equals("SYSTEM") && event.getActor().getUsername().equals("pastorchrislive")) {
                processMessage.processPclNotificationEvent(event);
            } else {
                log.info("Processing other notification events");
                processMessage.processNotificationEvent(event);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
