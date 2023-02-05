package com.chatup.models.entities;


import java.io.Serializable;
import java.time.LocalDateTime;

public class GroupMembership implements Serializable {
    private int id;
    private int groupChatId;
    private int userId;
    private LocalDateTime joinDate;

    public GroupMembership() {
    }

    public GroupMembership(int id, int group_chat_id, int user_id, LocalDateTime join_date) {
        this.id = id;
        this.groupChatId = group_chat_id;
        this.userId = user_id;
        this.joinDate = join_date;
    }
    public GroupMembership(int group_chat_id, int user_id, LocalDateTime join_date) {

        this.groupChatId = group_chat_id;
        this.userId = user_id;
        this.joinDate = join_date;
    }


    public int getId() {
        return id;
    }

  

    public int getGroupChatId() {
        return groupChatId;
    }

    public void setGroupChatId(int groupChatId) {
        this.groupChatId = groupChatId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
}
