package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.FriendRequest;
import com.chatup.models.entities.User;

import java.util.List;

public interface FriendsServices {

    public List<User> getUserFriends(int userID);
    public List<User> getUserFriendRequests(int userID);
    public Boolean updateFriendsRequestStatus( FriendRequest updatedRequests);
}
