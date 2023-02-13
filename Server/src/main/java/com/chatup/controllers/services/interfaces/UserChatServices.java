package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.Chat;
import com.chatup.models.entities.ChatMessage;

public interface UserChatServices {
    int sendChatMessage(ChatMessage message);

    Chat getChat(int chatID);

    int createChat(Chat chat);
}
