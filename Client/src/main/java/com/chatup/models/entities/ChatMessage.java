package com.chatup.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = -2990434115553845364L;
    private int id;
    private int chatId;
    private int senderId;
    private String content;
    private LocalDateTime messageDateTime;

    private int attachment_Id;

    public ChatMessage(int id, int chatId, int senderId, String content, LocalDateTime messageDateTime, int attachment_Id) {
        this.id = id;
        this.chatId = chatId;
        this.senderId = senderId;
        this.content = content;
        this.messageDateTime = messageDateTime;
        this.attachment_Id = attachment_Id;
    }

    public ChatMessage(int id, int chatId, int senderId, String content, LocalDateTime messageDateTime) {
        this.id = id;
        this.chatId = chatId;
        this.senderId = senderId;
        this.content = content;
        this.messageDateTime = messageDateTime;
    }

    public ChatMessage(int chatId, int senderId, String content, LocalDateTime messageDateTime, int attachment_Id) {
        this.chatId = chatId;
        this.senderId = senderId;
        this.content = content;
        this.messageDateTime = messageDateTime;
    }


    public int getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getMessageDateTime() {
        return messageDateTime;
    }

    public void setMessageDateTime(LocalDateTime messageDateTime) {
        this.messageDateTime = messageDateTime;
    }

    public int getAttachment_Id() {
        return attachment_Id;
    }

    public void setAttachment_Id(int attachment_Id) {
        this.attachment_Id = attachment_Id;
    }

    @Override
    public String toString() {
        return "SingleChatMessage{" +
                "singleChatMessageId=" + id +
                ", userId=" + senderId +
                ", content='" + content + '\'' +
                ", messageTime=" + messageDateTime +
                '}';
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
