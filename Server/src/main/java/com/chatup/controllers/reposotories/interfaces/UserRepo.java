package com.chatup.controllers.reposotories.interfaces;

import com.chatup.models.entities.User;

import java.util.List;

public interface UserRepo {
    public int createUser(User user);

    public boolean deleteUser(int userID);

    public boolean updateUser(User user);

    boolean updateUserPassword(int userID, String password);

    boolean updateUserImg(int userID, String phone, byte[] img);

    public User getUser(int userID);

    public User getUser(String userPhone);

    public List<User> getAllUsers();
}