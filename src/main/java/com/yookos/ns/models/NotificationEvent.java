package com.yookos.ns.models;

import java.util.List;
import java.util.Map;

/**
 * Created by jome on 2016/02/08.
 */
public class NotificationEvent {
    private YookoreUser actor; //The originator of the event
    private YookoreUser recipient;
    private String action;
    private List<TargetUser> targetUsers;
    private Map<String, Object> extraInfo;

    public YookoreUser getActor() {
        return actor;
    }

    public void setActor(YookoreUser actor) {
        this.actor = actor;
    }

    public YookoreUser getRecipient() {
        return recipient;
    }

    public void setRecipient(YookoreUser recipient) {
        this.recipient = recipient;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, Object> getExtraInfo() {
        return extraInfo;
    }

    public List<TargetUser> getTargetUsers() {
        return targetUsers;
    }

    public void setTargetUsers(List<TargetUser> targetUsers) {
        this.targetUsers = targetUsers;
    }

    public void setExtraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
    }

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "actor=" + actor +
                ", recipient=" + recipient +
                ", action='" + action + '\'' +
                ", targetUsers=" + targetUsers +
                ", extraInfo=" + extraInfo +
                '}';
    }
}
