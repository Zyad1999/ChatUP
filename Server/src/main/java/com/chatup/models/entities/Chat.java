package com.chatup.models.entities;

import java.io.Serial;
import java.io.Serializable;

public class Chat implements Serializable {
    @Serial
    private static final long serialVersionUID = -558553967080513790L;

    private int id;
    private int firstUserId;
    private int secondUserId;

    public Chat(int userOneId, int userTwoId) {
        this.firstUserId = userOneId;
        this.secondUserId = userTwoId;
    }

    public Chat(int id, int userOneId, int userTwoId) {
        this.id = id;
        this.firstUserId = userOneId;
        this.secondUserId = userTwoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(int firstUserId) {
        this.firstUserId = firstUserId;
    }

    public int getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(int secondUserId) {
        this.secondUserId = secondUserId;
    }

}
