package com.yookos.ns.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yookos.ns.domain.Preference;

import java.util.List;

/**
 * Created by jome on 2016/01/22.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class YookoreUser {
    private String type;
    private String userId;
    private String username;
    private String fullNames;
    private String firstName;
    private String lastName;
    private Preference preference;

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

    public String getFullNames() {
        if(fullNames != null && fullNames.trim().length() == 0){
            String fullname =firstName + " " + lastName;
            return fullname.trim();
        }else{
            return fullNames;
        }
    }

    public void setFullNames(String fullNames) {
        this.fullNames = fullNames;
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

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    @Override
    public String toString() {
        return "YookoreUser{" +
                "type='" + type + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", fullNames='" + fullNames + '\'' +
                ", firstName='" + getFullNames() + '\'' +
                ", lastName='" + lastName + '\'' +
                ", preference=" + preference +
                '}';
    }
}
