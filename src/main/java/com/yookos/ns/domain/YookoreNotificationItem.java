package com.yookos.ns.domain;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jome on 2016/02/04.
 */

@Table(keyspace = "notifications", name = "notifications")
public class  YookoreNotificationItem {
    @PartitionKey(value = 0)
    private UUID target_user_id;
    private Date timesent;
    private UUID notification_id;
    private String content;
    private String content_type;
    private String notification_type;
    private String content_id;

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    private boolean read = false;

    public UUID getTarget_user_id() {
        return target_user_id;
    }

    public void setTarget_user_id(UUID target_user_id) {
        this.target_user_id = target_user_id;
    }

    public Date getTimesent() {
        return timesent;
    }

    public void setTimesent(Date timesent) {
        this.timesent = timesent;
    }

    public UUID getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(UUID notification_id) {
        this.notification_id = notification_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "YookoreNotificationItem{" +
                "target_user_id=" + target_user_id +
                ", timesent=" + timesent +
                ", notification_id=" + notification_id +
                ", content='" + content + '\'' +
                ", contentId='" + content_id + '\'' +
                ", content_type='" + content_type + '\'' +
                ", notification_type='" + notification_type + '\'' +
                ", read=" + read +
                '}';
    }
}
