package com.chatup.models.entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class GroupChat implements Serializable {
    @Serial
    private static final long serialVersionUID = -558553967080513795L;
    private int groupChatID;
    private String groupTitle;
    private byte[] groupImage;
    private LocalDateTime localDateTime;

    public GroupChat(int groupChatID, String groupTitle, byte[] groupImage) {
        this.groupChatID = groupChatID;
        this.groupTitle = groupTitle;
        this.groupImage = groupImage;
    }

    public GroupChat(String groupTitle) {
        this.groupTitle = groupTitle;
        this.localDateTime = LocalDateTime.now();
    }

    public GroupChat(String groupTitle, byte[] groupImage) {
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

    public byte[] getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(byte[] groupImage) {
        this.groupImage = groupImage;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setGroupChatID(int groupChatID) {
        this.groupChatID = groupChatID;
    }

    @Override
    public String toString() {
        return "GroupChat{" + groupChatID + ", " + groupTitle + ", " + groupImage + "}";
    }
}