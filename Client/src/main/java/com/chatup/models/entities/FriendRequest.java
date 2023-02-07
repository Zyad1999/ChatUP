package com.chatup.models.entities;

import com.chatup.models.enums.FriendRequestStatus;

import java.io.Serializable;

public class FriendRequest implements Serializable {

    private int requestID;
    private int senderID;
    private int receiverID;
    private FriendRequestStatus requestStatus;

    public FriendRequest(int requestID, int senderID, int receiverID, FriendRequestStatus requestStatus) {
        this.requestID = requestID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.requestStatus = requestStatus;
    }

    public FriendRequest(int senderID, int receiverID, FriendRequestStatus requestStatus) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.requestStatus = requestStatus;
    }

    public int getRequestID() {
        return requestID;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public FriendRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(FriendRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}