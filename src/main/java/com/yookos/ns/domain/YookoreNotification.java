package com.yookos.ns.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jome on 2016/01/22.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class YookoreNotification {
    private Actor actor;
    private String action;
    private ExtraInfo extraInfo;

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

    public ExtraInfo getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(ExtraInfo extraInfo) {
        this.extraInfo = extraInfo;
    }

    @Override
    public String toString() {
        return "YookoreNotification{" +
                "actor=" + actor +
                ", action='" + action + '\'' +
                ", extraInfo=" + extraInfo +
                '}';
    }
}
