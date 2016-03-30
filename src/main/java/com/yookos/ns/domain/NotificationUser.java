package com.yookos.ns.domain;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jome on 2016/02/12.
 */
@Table(keyspace = "notifications", name = "users")
public class NotificationUser {
    @PartitionKey
    private UUID id;
    private boolean accept_friend_request_notification;
    private boolean all_notification;
    private Set<UUID> block_list;
    private String cell_phone;
    private boolean create_content_notification;
    private boolean email_notification;
    private String firstname;
    private boolean friend_create_content_notification;
    private boolean friend_tag_notification;
    private String lastname;
    private boolean message_notification;
    private String primary_email;
    private boolean receive_friend_request_notification;
    private List<String> registration_ids;
    private boolean sms_notification;
    private String username;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isAccept_friend_request_notification() {
        return accept_friend_request_notification;
    }

    public void setAccept_friend_request_notification(boolean accept_friend_request_notification) {
        this.accept_friend_request_notification = accept_friend_request_notification;
    }

    public boolean isAll_notification() {
        return all_notification;
    }

    public void setAll_notification(boolean all_notification) {
        this.all_notification = all_notification;
    }

    public Set<UUID> getBlock_list() {
        return block_list;
    }

    public void setBlock_list(Set<UUID> block_list) {
        this.block_list = block_list;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public boolean isCreate_content_notification() {
        return create_content_notification;
    }

    public void setCreate_content_notification(boolean create_content_notification) {
        this.create_content_notification = create_content_notification;
    }

    public boolean isEmail_notification() {
        return email_notification;
    }

    public void setEmail_notification(boolean email_notification) {
        this.email_notification = email_notification;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public boolean isFriend_create_content_notification() {
        return friend_create_content_notification;
    }

    public void setFriend_create_content_notification(boolean friend_create_content_notification) {
        this.friend_create_content_notification = friend_create_content_notification;
    }

    public boolean isFriend_tag_notification() {
        return friend_tag_notification;
    }

    public void setFriend_tag_notification(boolean friend_tag_notification) {
        this.friend_tag_notification = friend_tag_notification;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isMessage_notification() {
        return message_notification;
    }

    public void setMessage_notification(boolean message_notification) {
        this.message_notification = message_notification;
    }

    public String getPrimary_email() {
        return primary_email;
    }

    public void setPrimary_email(String primary_email) {
        this.primary_email = primary_email;
    }

    public boolean isReceive_friend_request_notification() {
        return receive_friend_request_notification;
    }

    public void setReceive_friend_request_notification(boolean receive_friend_request_notification) {
        this.receive_friend_request_notification = receive_friend_request_notification;
    }

    public List<String> getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(List<String> registration_ids) {
        this.registration_ids = registration_ids;
    }

    public boolean isSms_notification() {
        return sms_notification;
    }

    public void setSms_notification(boolean sms_notification) {
        this.sms_notification = sms_notification;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
