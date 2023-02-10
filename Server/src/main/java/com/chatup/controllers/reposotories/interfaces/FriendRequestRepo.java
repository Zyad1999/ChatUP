package com.chatup.controllers.reposotories.interfaces;

import com.chatup.models.entities.FriendRequest;
import com.chatup.models.enums.FriendRequestStatus;

import java.util.List;

public interface FriendRequestRepo {

    public int createFriendRequest(FriendRequest request);

    public boolean deleteFriendRequest(int id);

    public boolean deleteFriendRequest(int senderID, int receiverID);

    public boolean updateFriendRequestStatus(int id, FriendRequestStatus newStatus);

    public boolean updateFriendRequestStatus(int senderID, int receiverID, FriendRequestStatus newStatus);

    public FriendRequest getFriendRequest(int id);

    public FriendRequest getFriendRequest(int senderID, int receiverID);

    public List<FriendRequest> getAllUserFriendRequests(int userID, FriendRequestStatus status);

    public List<FriendRequest> getUserFriendRequests(int userID, FriendRequestStatus status);
    public List<FriendRequest> getUserSentFriendRequests(int userID, FriendRequestStatus status);
}