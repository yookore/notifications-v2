package com.yookos.ns.services;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.yookos.ns.domain.Preference;
import com.yookos.ns.domain.RedisUser;
import com.yookos.ns.domain.YookoreNotificationItem;
import com.yookos.ns.models.NotificationEvent;
import com.yookos.ns.models.YookoreUser;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisCluster;

import java.util.*;

/**
 * Created by jome on 2016/02/04.
 */

@Component
public class ServiceUtils {

    public static final String CACHE_PREFIX = "notificaitons_";
    public static final String CACHE_DOMAIN_PROFILE_PREFIX = "profile_";
    public static final String UPM_URL = "http://upm.apps.yookosapps.com/api/v1/publish-profile/";
    public final String GROUP_INVITE = "GROUP_INVITE";
    public final String VIDEO_NOTIFICATION = "VIDEO_NOTIFICATION";
    public final String AUDIO_NOTIFICATION = "AUDIO_NOTIFICATION";
    public final String BLOG_POST_CREATED = "BLOG_POST_CREATED";
    public final String STATUS_UPDATE_CREATED = "STATUS_UPDATE_CREATED";
    public final String PHOTO_CREATED = "PHOTO_CREATED";
    public final String COMMENT_NOTIFICATION = "COMMENT_NOTIFICATION";
    public final String USER_VERIFICATION = "String USER_VERIFICATION";
    public final String RESET_PASSWORD = "RESET_PASSWORD";
    public final String CHANGE_PASSWORD = "CHANGE_PASSWORD";
    public final String UPDATE_PRIMARY_EMAIL = "UPDATE_PRIMARY_EMAIL";
    public final String FRIEND_REQUEST = "FRIEND_REQUEST";
    public final String FRIEND_REQUEST_ACCEPTED = "FRIEND_REQUEST_ACCEPTED";
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
    RestTemplate restTemplate;

    @Autowired
    Mapper<YookoreNotificationItem> mapper;

    @Autowired
    Gson gson;

    public void preparePushMessages(NotificationEvent event) {
        getObjectId(event);

        Preference preference = new Preference();
        preference.setPush(true);
        event.getActor().setPreference(preference);

        if (event.getAction().equals("COMMENT_NOTIFICATION")) {
            //We are only sending to those in the target list of users
            logger.info("Processing comment notifications");
            String parentObjectType = event.getTargetUsers().get(0).getObjectType();
            String parentContentUrl = event.getTargetUsers().get(0).getContentUrl();
            event.getExtraInfo().put("parentObjectType", parentObjectType);
            event.getExtraInfo().put("parentContentUrl", parentContentUrl);
            YookoreUser recipient = getRecipient(event.getTargetUsers().get(0).getUsername());
            Preference prefs = getPreferencesForUser(recipient.getUsername());
            recipient.setPreference(prefs);
            event.setRecipient(recipient);
            saveAndPushToQueue(event);

        } else {
            List<YookoreUser> relatedUsers = getRelatedUsers(event.getActor());
            for (YookoreUser recipient : relatedUsers) {
                Preference prefs = getPreferencesForUser(recipient.getUsername());
                recipient.setPreference(prefs);
                event.setRecipient(recipient);
                saveAndPushToQueue(event);
            }
        }

    }

    public void preparePushPCLMessages(NotificationEvent event) {
        getObjectId(event);

        Preference preference = new Preference();
        preference.setPush(true);
        event.getActor().setPreference(preference);
        List<YookoreUser> relatedUsers = getRelatedPCLUsers(event.getActor());
        for (YookoreUser recipient : relatedUsers) {
            Preference prefs = getPreferencesForUser(recipient.getUsername());
            recipient.setPreference(prefs);
            event.setRecipient(recipient);
            saveAndPushToPCLQueue(event);
        }

    }

    private void getObjectId(NotificationEvent event) {
        String url = (String) event.getExtraInfo().get("contentUrl");
        String[] split = url.split("/");
        logger.info("Object id: {}", split[4]);
        event.getExtraInfo().put("objectid", split[4]);
    }


    private Preference getPreferencesForUser(String username) {
        Preference preference = new Preference();
        preference.setPush(true); //for now, let everyone get a push notification. We will deal with blocked users later
        //TODO: Insert code here to get the actual preferences for the user.
        return preference;
    }


    private void processContentUrl(NotificationEvent event) {
        String objectType = (String) event.getExtraInfo().get("objectType");
        String newUrl = (String) event.getExtraInfo().get("contentUrl");
        logger.info("Object Type: ", objectType);
        if (objectType.equals("blogpost") && newUrl.startsWith("/api")) {
            newUrl = "blogpost.apps.yookosapps.com" + newUrl;
            newUrl = newUrl.replace("/?from=aes", "");
            event.getExtraInfo().put("contentUrl", newUrl);
        }
        if (objectType.equals("statusupdate") && newUrl.startsWith("/api")) {
            newUrl = "statusupdate.apps.yookosapps.com" + newUrl;
            newUrl = newUrl.replace("/?from=aes", "");
            event.getExtraInfo().put("contentUrl", newUrl);
        }
        if (objectType.equals("photo") && newUrl.startsWith("/api")) {
            newUrl = "photos.apps.yookosapps.com" + newUrl;
            newUrl = newUrl.replace("/?from=aes", "");
            event.getExtraInfo().put("contentUrl", newUrl);
        }

        if (objectType.equals("video") && newUrl.startsWith("/api")) {
            newUrl = "videos.apps.yookosapps.com" + newUrl;
            newUrl = newUrl.replace("/?from=aes", "");
            event.getExtraInfo().put("contentUrl", newUrl);
        }
        if (objectType.equals("share") && newUrl.startsWith("/api")) {
            newUrl = "share.apps.yookosapps.com" + newUrl;
            newUrl = newUrl.replace("/?from=aes", "");
            event.getExtraInfo().put("contentUrl", newUrl);
        }

        if (objectType.equals("post") && newUrl.startsWith("/api")) {
            newUrl = "post.apps.yookosapps.com" + newUrl;
            newUrl = newUrl.replace("/?from=aes", "");
            event.getExtraInfo().put("contentUrl", newUrl);
        }

        if (objectType.equals("audio") && newUrl.startsWith("/api")) {
            newUrl = "audio.apps.yookosapps.com" + newUrl;
            newUrl = newUrl.replace("/?from=aes", "");
            event.getExtraInfo().put("contentUrl", newUrl);
        }

    }


    private List<YookoreUser> getRelatedUsers(YookoreUser yookoreUser) {
        return getUsers(yookoreUser, false);
    }

    private List<YookoreUser> getRelatedPCLUsers(YookoreUser yookoreUser) {
        return getUsers(yookoreUser, true);
    }

    private List<YookoreUser> getUsers(YookoreUser yookoreUser, boolean publicFigure) {
        List<YookoreUser> users = new ArrayList<>();
        MongoCollection<Document> yookoreDb = client.getDatabase("yookore").getCollection("aes_relationships");
        FindIterable<Document> relatedusers;

        if (publicFigure) {
            relatedusers = yookoreDb.find(new BasicDBObject("relateduser", yookoreUser.getUsername()).append("has_device", true));
        } else {
            relatedusers = yookoreDb.find(new BasicDBObject("relateduser", yookoreUser.getUsername()).append("has_device", true));
        }


        for (Document relateduser : relatedusers) {
            String username = relateduser.getString("user");
            try {
                YookoreUser recipient = getRecipient(username);

                if (recipient != null) {
                    users.add(recipient);
                }

            } catch (HttpClientErrorException hcee) {
                logger.error(hcee.toString());
            }
        }
        logger.info("Size of recipients list: {}", users.size());
        return users;
    }

    private YookoreUser getRecipient(String username) {
        String actorString = jedisCluster.get(CACHE_PREFIX + CACHE_DOMAIN_PROFILE_PREFIX + username);
        YookoreUser recipient = null;
        if (actorString != null) {
            RedisUser redisRecipient = gson.fromJson(actorString, RedisUser.class);
            recipient = new YookoreUser();
            recipient.setUsername(redisRecipient.getUsername());
            recipient.setFirstName(redisRecipient.getFirstname());
            recipient.setLastName(redisRecipient.getLastname());
            recipient.setType("USER");
            recipient.setUserId(redisRecipient.getUserid());
            recipient.setFullNames(redisRecipient.getFirstname() + " " + redisRecipient.getLastname());
            Preference preference = new Preference();
            preference.setPush(true);
            recipient.setPreference(preference);
            logger.info("Pulled user from redis: {}", redisRecipient);
        } else {

            String request = UPM_URL + username;
            RedisUser redisRecipient = restTemplate.getForObject(request, RedisUser.class);

            if (redisRecipient != null) {
                recipient = new YookoreUser();
                recipient.setUsername(redisRecipient.getUsername());
                recipient.setFirstName(redisRecipient.getFirstname());
                recipient.setLastName(redisRecipient.getLastname());
                recipient.setType("USER");
                recipient.setUserId(redisRecipient.getUserid());
                recipient.setFullNames(redisRecipient.getFirstname() + " " + redisRecipient.getLastname());
                Preference preference = new Preference();
                preference.setPush(true);
                recipient.setPreference(preference);
                String cachedUserKey = CACHE_PREFIX + CACHE_DOMAIN_PROFILE_PREFIX + username;
                jedisCluster.set(cachedUserKey, gson.toJson(recipient));
                logger.info("Pulled user from upm: {}", redisRecipient);
            }
        }
        return recipient;
    }


    private YookoreNotificationItem getYookoreNotificationItem(NotificationEvent event) {
        try {
            Map<String, Object> data = new HashMap<>();
            Map<String, Object> payloadData = new HashMap<>();
            //Lets save the data first...
            YookoreNotificationItem notificationItem = new YookoreNotificationItem();
            notificationItem.setTimesent(Calendar.getInstance().getTime());
            notificationItem.setNotification_id(UUID.randomUUID());
            notificationItem.setContent_type("notification");

            notificationItem.setRead(false);
            notificationItem.setTarget_user_id(UUID.fromString(event.getRecipient().getUserId()));
            notificationItem.setNotification_type(event.getAction());

            data.put("notificationType", null);
            data.put("actor", event.getActor().getUserId());
            if (event.getActor().getFullNames().trim().length() > 0) {
                data.put("fullNames", event.getActor().getFullNames());
            } else {
                data.put("fullNames", event.getActor().getFirstName() + " " + event.getActor().getLastName());
            }
            data.put("type", event.getAction());

            payloadData.put("actor", event.getActor());
            payloadData.put("notificationType", null);
            payloadData.put("action", event.getAction());
            payloadData.put("targetUsers", null);
            payloadData.put("deliveryTag", 1);
            payloadData.put("extraInfo", event.getExtraInfo());


            String payload = gson.toJson(event);
            logger.info("Payload: {}", payloadData);
            data.put("payload", payloadData);

            String dataString = gson.toJson(data);
            notificationItem.setContent(dataString);
            logger.info("Ready for saving: {}", notificationItem);

            return notificationItem;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendToPCLPushQueue(NotificationEvent event) {
        //TODO:
        rabbitTemplate.convertAndSend("myexchange", "pcl_queue", event);
    }

    private void sendToPushQueue(NotificationEvent event) {
        //TODO:
        rabbitTemplate.convertAndSend("myexchange", "myqueue", event);
    }

    private void saveAndPushToQueue(NotificationEvent event) {
        try {
                processContentUrl(event);
                YookoreNotificationItem notificationItem = getYookoreNotificationItem(event);
                if (notificationItem != null) {
                    logger.info(notificationItem.toString());
                    mapper.save(notificationItem);
                    sendToPushQueue(event);
                }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    private void saveAndPushToPCLQueue(NotificationEvent event) {
        try {
            processContentUrl(event);
            logger.info("Content URL: {}", event.getExtraInfo().get("ContentUrl"));
            YookoreNotificationItem notificationItem = getYookoreNotificationItem(event);
            if (notificationItem != null) {
                mapper.save(notificationItem);
                sendToPCLPushQueue(event);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
