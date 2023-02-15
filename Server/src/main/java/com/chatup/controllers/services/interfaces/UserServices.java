package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.Chat;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.User;

import java.util.List;
import java.util.Map;

public interface UserServices {
    public User getUserInfo(int userId);

    public User getUserInfo(String phone);

    public Map<Chat, ChatMessage> getUserchats(int userId);

    public List<ChatMessage> getChatMsg(int chatId);

    int getNumberAllUsers();
    int getNumberAllMaleUsers();
    int getNumberAllOnlineUsers();
    int getNumberAllCountryOfUsers(String country);
    boolean updateUserInfo (User user);
    boolean updateUserImage (int userID, String phone, byte[] img);
    boolean updateUserPassword (int userID, String password);
}
