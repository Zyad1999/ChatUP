package com.chatup.models.entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class GroupMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = -558553967080513797L;
    private int groupMessageId;
    private int senderId;
    private String content;
    private LocalDateTime messageDate;
    private int groupChatId;
    private int attachmentID;

    public GroupMessage(int groupMessageId, String content, LocalDateTime messageDate) {
        this.groupMessageId = groupMessageId;
        this.content = content;
        this.messageDate = messageDate;
    }

    public GroupMessage(int groupMessageId, int senderId, String content, LocalDateTime messageDate, int groupChatId, int attachmentID) {
        this.groupMessageId = groupMessageId;
        this.senderId = senderId;
        this.content = content;
        this.messageDate = messageDate;
        this.groupChatId = groupChatId;
        this.attachmentID = attachmentID;
    }

    public GroupMessage(int senderId, String content, LocalDateTime messageDate, int groupChatId, int attachmentID) {
        this.senderId = senderId;
        this.content = content;
        this.messageDate = messageDate;
        this.groupChatId = groupChatId;
        this.attachmentID = attachmentID;
    }

    public int getGroupMessageId() {
        return groupMessageId;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(LocalDateTime messageDate) {
        this.messageDate = messageDate;
    }

    public int getGroupChatId() {
        return groupChatId;
    }

    public int getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(int attachmentID) {
        this.attachmentID = attachmentID;
    }

    @Override
    public String toString() {
        return "GroupMessage{" + groupMessageId + ", " + senderId + ", " + content + ", " + messageDate + ", " + attachmentID + "}";
    }
}