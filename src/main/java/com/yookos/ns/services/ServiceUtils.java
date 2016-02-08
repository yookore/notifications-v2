package com.yookos.ns.services;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.yookos.ns.domain.*;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.*;

/**
 * Created by jome on 2016/02/04.
 */

@Component
public class ServiceUtils {
    public static final String CACHE_PREFIX = "notificaitons_";
    public static final String CACHE_DOMAIN_PROFILE_PREFIX = "profile_";
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    JedisCluster jedisCluster;

    @Autowired
    Cluster cassandraCluster;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MongoClient client;

    @Autowired
    Session session;

    @Autowired
    Mapper<YookoreNotificationItem> mapper;

    @Autowired
    Gson gson;

    public void preparePushMessages(YookoreNotificationEvent event) {
        String url = (String) event.getExtraInfo().get("contentUrl");
        String[] split = url.split("/");
        logger.info("Object id: {}", split[4]);
        event.getExtraInfo().put("objectid", split[4]);

        Preference preference = new Preference();
        preference.setPush(true);
        event.getActor().setPreference(preference);
        logger.info("Notification Event: {}", event);
        List<Actor> relatedUsers = getRelatedUsers(event.getActor());
//        logger.info("Related users:{}", relatedUsers.toString());
        for (Actor targetUser : relatedUsers) {
            saveAndPushToQueue(event, targetUser);
        }

    }

    private void saveAndPushToQueue(YookoreNotificationEvent event, Actor targetUser) {
        Map<String, Object> data = new HashMap<>();
        //Lets save the data first...
        YookoreNotificationItem notificationItem = new YookoreNotificationItem();
        notificationItem.setTimesent(Calendar.getInstance().getTime());
        notificationItem.setNotification_id(UUID.randomUUID());
        notificationItem.setContent_type("notification");

        notificationItem.setRead(false);
        notificationItem.setTarget_user_id(UUID.fromString(targetUser.getUserId()));
        notificationItem.setNotification_type(event.getAction());

        data.put("notificationType", null);
        data.put("actor", event.getActor().getUserId());
        if (event.getActor().getFullNames().trim().length() > 0) {
            data.put("fullNames", event.getActor().getFullNames());
        }else{
            data.put("fullNames", event.getActor().getFirstName() + " " + event.getActor().getLastName());
        }
        data.put("type", event.getAction());
        String payload = gson.toJson(event);
        data.put("payload", payload);

        String dataString = gson.toJson(data);
        notificationItem.setContent(dataString);
        try{
//            logger.info("Ready for saving: {}", notificationItem);
            mapper.save(notificationItem);

            sendToPushQueue(event);

        }catch (Exception e){
            logger.error(e.getMessage());
        }

    }

    private void sendToPushQueue(YookoreNotificationEvent event) {
        //TODO:
        logger.info(rabbitTemplate.getMessageConverter().toString());
        String payload = gson.toJson(event);
        rabbitTemplate.convertAndSend("myexchange", "myqueue", event);

    }

    private List<Actor> getRelatedUsers(Actor actor) {
        return getUsers(actor);
    }

    private List<Actor> getUsers(Actor actor) {
        List<Actor> users = new ArrayList<>();
        MongoCollection<Document> yookoreDb = client.getDatabase("yookore").getCollection("aes_relationships");


        FindIterable<Document> relatedusers = yookoreDb.find(new BasicDBObject("relateduser", actor.getUsername()));

        for (Document relateduser : relatedusers) {
            String username = relateduser.getString("user");
            Actor recipient = new Actor();

            String actorString = jedisCluster.get(CACHE_PREFIX + CACHE_DOMAIN_PROFILE_PREFIX + username);

            if (actorString != null) {
                RedisUser redisRecipient = gson.fromJson(actorString, RedisUser.class);
                recipient.setUsername(redisRecipient.getUsername());
                recipient.setFirstName(redisRecipient.getFirstname());
                recipient.setLastName(redisRecipient.getLastname());
                recipient.setType("USER");
                recipient.setUserId(redisRecipient.getUserid());
                recipient.setFullNames(redisRecipient.getFirstname() + " " + redisRecipient.getLastname());
                Preference preference = new Preference();
                preference.setPush(true);
                recipient.setPreference(preference);
                users.add(recipient);
            } else {
                logger.info("No actor was found... Will retrieve data from the user profile service");
            }

        }
        return users;
    }

    public void preparePushPCLMessages(YookoreNotificationEvent event) {
        String url = (String) event.getExtraInfo().get("contentUrl");
        String[] split = url.split("/");
        logger.info("Object id: {}", split[4]);
        event.getExtraInfo().put("objectid", split[4]);

        Preference preference = new Preference();
        preference.setPush(true);
        event.getActor().setPreference(preference);
        logger.info("Notification Event: {}", event);
        List<Actor> relatedUsers = getRelatedUsers(event.getActor());
//        logger.info("Related users:{}", relatedUsers.toString());
        for (Actor targetUser : relatedUsers) {
            saveAndPushToPCLQueue(event, targetUser);
        }

    }

    private void saveAndPushToPCLQueue(YookoreNotificationEvent event, Actor targetUser) {
        Map<String, Object> data = new HashMap<>();
        //Lets save the data first...
        YookoreNotificationItem notificationItem = new YookoreNotificationItem();
        notificationItem.setTimesent(Calendar.getInstance().getTime());
        notificationItem.setNotification_id(UUID.randomUUID());
        notificationItem.setContent_type("notification");

        notificationItem.setRead(false);
        notificationItem.setTarget_user_id(UUID.fromString(targetUser.getUserId()));
        notificationItem.setNotification_type(event.getAction());

        data.put("actor", event.getActor().getUserId());
        data.put("fullNames", event.getActor().getFullNames());
        data.put("type", event.getAction());
        String payload = gson.toJson(event);
        data.put("payload", payload);

        notificationItem.setContent(data.toString());
        logger.info("Ready for saving: {}", notificationItem);
    }
}
