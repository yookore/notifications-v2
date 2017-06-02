package com.yookos.ns.services;

import com.yookos.ns.domain.RedisUser;
import com.yookos.ns.models.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jome on 2016/01/19.
 */


@Component
public class ProcessMessageEvent {
    public static final String GROUP_INVITE = "GROUP_INVITE";
    public static final String VIDEO_NOTIFICATION = "VIDEO_NOTIFICATION";
    public static final String AUDIO_NOTIFICATION = "AUDIO_NOTIFICATION";
    public static final String BLOG_POST_CREATED = "BLOG_POST_CREATED";
    public static final String POST_CREATED = "POST_CREATED";
    public static final String STATUS_UPDATE_CREATED = "STATUS_UPDATE_CREATED";
    public static final String PHOTO_CREATED = "PHOTO_CREATED";
    public static final String PRAYER_CREATED = "PRAYER_CREATED";
    public static final String PRAYER_EDITED = "PRAYER_EDITED";
    public static final String PRAYER_LIKED = "PRAYER_LIKED";
    public static final String PRAYERREQUEST_TESTIFIED = "PRAYERREQUEST_TESTIFIED";
    public static final String TESTIMONY_CREATED = "TESTIMONY_CREATED";
    public static final String TESTIMONY_LIKED = "TESTIMONY_LIKED";
    public static final String COMMENT_NOTIFICATION = "COMMENT_NOTIFICATION";
    public static final String USER_VERIFICATION = "String USER_VERIFICATION";
    public static final String RESET_PASSWORD = "RESET_PASSWORD";
    public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";
    public static final String UPDATE_PRIMARY_EMAIL = "UPDATE_PRIMARY_EMAIL";
    public static final String FRIEND_REQUEST = "FRIEND_REQUEST";
    public static final String FRIEND = "FRIEND";
    public static final String FRIEND_REQUEST_ACCEPTED = "FRIEND_REQUEST_ACCEPTED";
    public static final String MESSAGE_SENT = "MESSAGE_SENT";

    @Autowired
    ServiceUtils serviceUtils;

    Logger log = LoggerFactory.getLogger(this.getClass());


    public void processNotificationEvent(NotificationEvent event) {

        //Determine the action so as to decide the course of messaging actions to take
        String action = event.getAction();

        if (action.equals(BLOG_POST_CREATED)
                || action.equals(STATUS_UPDATE_CREATED)
                || action.equals(PHOTO_CREATED)
                || action.equals(PRAYER_CREATED)
                || action.equals(PRAYER_LIKED)
                || action.equals(PRAYER_EDITED)
                || action.equals(PRAYERREQUEST_TESTIFIED)
                || action.equals(TESTIMONY_CREATED)
                || action.equals(POST_CREATED)
                || action.equals(COMMENT_NOTIFICATION)
                || action.equals(AUDIO_NOTIFICATION)
                || action.equals(MESSAGE_SENT)
                || action.equals(VIDEO_NOTIFICATION)) {
            log.info("Processing push messaging event: {}", event);
            serviceUtils.preparePushMessages(event);
            //We will for now assume that only push events will be processed here...

        }

        if (action.equals(USER_VERIFICATION)
                || action.equals(RESET_PASSWORD)
                || action.equals(CHANGE_PASSWORD)
                || action.equals(UPDATE_PRIMARY_EMAIL)) {
            log.info("Processing email messaging event: {}", event);

        }

        if (action.equals(FRIEND_REQUEST) || action.equals(FRIEND_REQUEST_ACCEPTED)||action.equals(FRIEND)) {
//            RedisUser redisUser = serviceUtils.getRedisUser(event.getActor().getUsername());
//            Map<String, Object> info = new HashMap<>();
//
//            if(redisUser != null){
//                info.put("actorImgurl", redisUser.getAvatarurl());
//            }



//            event.setExtraInfo(info);
            if (event.getAction().equals(FRIEND))
                    event.setAction(FRIEND_REQUEST_ACCEPTED);
            log.info("Processing friend request messaging event: {}", event);
            serviceUtils.prepareSinglePushMessage(event);
        }
    }

    public void processPclNotificationEvent(NotificationEvent event) {
        log.info("processing pcl event message: {}", event);
        serviceUtils.preparePushPCLMessages(event);
    }
}
