package com.chatup.controllers.services.implementations;

import com.chatup.controllers.services.interfaces.CurrentUser;
import com.chatup.models.entities.User;

public class CurrentUserImp implements CurrentUser {
    private static User currentUser;

    private CurrentUserImp() {
    }

    public static User getCurrentUser() {
        return currentUser;
    }


    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
