package com.yookos.ns.models;

/**
 * Created by jome on 2016/11/03.
 */
public class YookorePerson {
    private String imgurl;
    private String profileurl;
    private String firstname;
    private String userid;
    private String email;
    private String username;
    private String lastname;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "YookorePerson{" +
                "imgurl='" + imgurl + '\'' +
                ", profileurl='" + profileurl + '\'' +
                ", firstname='" + firstname + '\'' +
                ", userid='" + userid + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
