package com.yookos.ns.domain;

/**
 * Created by jome on 2016/02/05.
 */
public class RedisUser {
    private String userid;
    private String username;
    private String firstname;
    private String lastname;
    private String fullname;
    private String avatarurl;
    private String email;
    private boolean onMobile;

    public boolean isOnMobile() {
        return onMobile;
    }

    public void setOnMobile(boolean onMobile) {
        this.onMobile = onMobile;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "RedisUser{" +
                "userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", fullname='" + fullname + '\'' +
                ", avatarurl='" + avatarurl + '\'' +
                '}';
    }
}
