package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.Chat;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.User;

import java.util.List;

public interface UserServices {
    public User getUserInfo(int userId);
    public List<Chat> getUserchats(int userId);
    public List<ChatMessage> getChatMsg(int chatId);
}
