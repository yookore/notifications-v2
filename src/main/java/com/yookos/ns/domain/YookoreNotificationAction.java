package com.yookos.ns.domain;

/**
 * Created by jome on 2016/02/04.
 */
public enum YookoreNotificationAction {
    GROUP_INVITE("GROUP_INVITE"),
    VIDEO_NOTIFICATION("VIDEO_NOTIFICATION"),
    AUDIO_NOTIFICATION("AUDIO_NOTIFICATION"),
    BLOG_POST_CREATED("BLOG_POST_CREATED"),
    STATUS_UPDATE_CREATED("STATUS_UPDATE_CREATED"),
    PHOTO_CREATED("PHOTO_CREATED"),
    COMMENT_NOTIFICATION("COMMENT_NOTIFICATION");

    private String actionType;

    YookoreNotificationAction(String actionType) {
        this.actionType = actionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
