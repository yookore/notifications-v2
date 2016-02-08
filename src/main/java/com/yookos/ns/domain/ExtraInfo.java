package com.yookos.ns.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by jome on 2016/01/22.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtraInfo {
    private String actorImgurl;
    private String actorProfileurl;
    private String objectType;
    private String verb;
    private String title;
    private String contentUrl;

    public String getActorImgurl() {
        return actorImgurl;
    }

    public void setActorImgurl(String actorImgurl) {
        this.actorImgurl = actorImgurl;
    }

    public String getActorProfileurl() {
        return actorProfileurl;
    }

    public void setActorProfileurl(String actorProfileurl) {
        this.actorProfileurl = actorProfileurl;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    @Override
    public String toString() {
        return "ExtraInfo{" +
                "actorImgurl='" + actorImgurl + '\'' +
                ", actorProfileurl='" + actorProfileurl + '\'' +
                ", objectType='" + objectType + '\'' +
                ", verb='" + verb + '\'' +
                ", title='" + title + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                '}';
    }
}
