package com.chatup.controllers.reposotories.interfaces;


import com.chatup.models.entities.Chat;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.User;

import java.util.List;

public interface ChatRepo {


    int createSingleChat(Chat singleChat);

    // Read
    Chat getSingleChat(int singleChatId);

    Chat getSingleChat(int firstUserId, int secondUserId);

    List<User> getSingleChatUsers(int singleChatId);

    List<ChatMessage> getSingleChatMessages(int singleChatId);

    // Update
    boolean updateSingleChat(Chat singleChat);

    // Delete
    boolean deleteSingleChat(int singleChatId);

    public List<Chat> getAllUserChats (int userId);

    public ChatMessage getLastMessage(int cahtId);
}
