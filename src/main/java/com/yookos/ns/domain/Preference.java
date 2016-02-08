package com.yookos.ns.domain;

import java.io.Serializable;

/**
 * Created by jome on 2016/02/04.
 */
public class Preference implements Serializable{
    Boolean email = false;
    Boolean push;
    Boolean sms = false;

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

    public Boolean getPush() {
        return push;
    }

    public void setPush(Boolean push) {
        this.push = push;
    }

    public Boolean getSms() {
        return sms;
    }

    public void setSms(Boolean sms) {
        this.sms = sms;
    }

    @Override
    public String toString() {
        return "Preference{" +
                "email=" + email +
                ", push=" + push +
                ", sms=" + sms +
                '}';
    }
}
