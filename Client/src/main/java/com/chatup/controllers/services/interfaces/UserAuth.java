package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.User;

import java.time.LocalDate;

public interface UserAuth {
    User loginAuth(String phoneNumber, String password);
    int signUpAuth(User user);
}
