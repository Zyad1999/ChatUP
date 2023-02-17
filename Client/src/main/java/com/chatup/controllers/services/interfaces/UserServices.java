package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.FriendRequest;
import com.chatup.models.entities.User;

import java.util.List;

public interface UserServices {

    User getUser(int userID);
    User getUser(String phoneNumber);
    User getFiendData(String userPhone);
     Boolean createFriendRequests(List<FriendRequest> friendRequestList);
     Boolean UpdateUser(User userinfo);
     Boolean UpdateUserImage(int userID, String phone, byte[] img);
     Boolean UpdateUserPassword(int userID, String password);

    }
