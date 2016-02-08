package com.yookos.ns.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jome on 2016/02/04.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YookoreNotificationEvent {
    private Actor actor;
    private String action;
    //    private ExtraInfo extraInfo;
    private HashMap<String, Object> extraInfo;
    private Actor targetUser;


    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public HashMap<String, Object> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(HashMap<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Actor getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(Actor targetUser) {
        this.targetUser = targetUser;
    }

    @Override
    public String toString() {
        return "YookoreNotificationEvent{" +
                "actor=" + actor +
                ", action='" + action + '\'' +
                ", extraInfo=" + extraInfo +
                ", targetUser=" + targetUser +
                '}';
    }
}
