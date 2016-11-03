package com.yookos.ns.models;


public class FriendRequestAction {
    YookorePerson actor;
    YookorePerson target;
    String type;

    public YookorePerson getActor() {
        return actor;
    }

    public void setActor(YookorePerson actor) {
        this.actor = actor;
    }

    public YookorePerson getTarget() {
        return target;
    }

    public void setTarget(YookorePerson target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
