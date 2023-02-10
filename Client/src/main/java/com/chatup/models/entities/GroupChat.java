package com.chatup.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GroupChat implements Serializable {
    private int groupChatID;
    private String groupTitle;
    private String groupImage;
    private LocalDateTime localDateTime;

    public GroupChat(int groupChatID, String groupTitle, String groupImage) {
        this.groupChatID = groupChatID;
        this.groupTitle = groupTitle;
        this.groupImage = groupImage;
    }

    public GroupChat(String groupTitle) {
        this.groupTitle = groupTitle;
        this.localDateTime = LocalDateTime.now();
    }

    public GroupChat(String groupTitle, String groupImage) {
        this.groupTitle = groupTitle;
        this.groupImage = groupImage;
        this.localDateTime = LocalDateTime.now();
    }

    public int getGroupChatID() {
        return groupChatID;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "GroupChat{" + groupChatID + ", " + groupTitle + ", " + groupImage + "}";
    }
}