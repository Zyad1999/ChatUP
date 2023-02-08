package com.chatup.controllers.services.implementations;

import com.chatup.controllers.reposotories.implementations.ChatRepoImpl;
import com.chatup.controllers.services.interfaces.UserServices;
import com.chatup.models.entities.Chat;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.User;

import java.util.List;

public class UserServicesImpl implements UserServices {

    @Override
    public User getUserInfo(int userId) {
        return null;
    }

    @Override
    public List<Chat> getUserchats(int userId) {
        return ChatRepoImpl.getInstance().getAllUserChats(userId);
    }

    @Override
    public List<ChatMessage> getChatMsg(int chatId) {
        return null;
    }
}
