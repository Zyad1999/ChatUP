package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.FriendRequest;
import com.chatup.models.entities.User;

import java.util.List;

public interface UserServices {

    public User getUser(int userID);

    User getUser(String phoneNumber);
    User getFiendData(String userPhone);
     Boolean createFriendRequests(List<FriendRequest> friendRequestList);
}
