package com.chatup.controllers.reposotories.interfaces;

import com.chatup.models.entities.ChatMessage;



public interface ChatMessageRepo {

    // Create
    int createSingleChatMessage(ChatMessage singleChatMessage);

    // Read
    ChatMessage getSingleChatMessage(int singleChatMessageId);

    // Update
    boolean updateSingleChatMessage(ChatMessage singleChatMessage);

    // Delete
    boolean deleteSingleChatMessage(int singleChatMessageId);
}
