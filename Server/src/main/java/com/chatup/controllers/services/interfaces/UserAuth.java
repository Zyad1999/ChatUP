package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.User;

public interface UserAuth {
    User sign_In(String phone_Num, String pass);

    boolean logout(int userID);

    int sign_Up(User user);
}
