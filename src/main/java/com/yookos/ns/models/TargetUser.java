package com.yookos.ns.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jome on 2016/02/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TargetUser {
    private String type;
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String actorImgurl;
    private String objectType;
    private String contentUrl;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getActorImgurl() {
        return actorImgurl;
    }

    public void setActorImgurl(String actorImgurl) {
        this.actorImgurl = actorImgurl;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    @Override
    public String toString() {
        return "TargetUser{" +
                "type='" + type + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", actorImgurl='" + actorImgurl + '\'' +
                ", objectType='" + objectType + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                '}';
    }
}
