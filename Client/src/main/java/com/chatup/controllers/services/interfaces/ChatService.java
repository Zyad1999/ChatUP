package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.Chat;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.GroupMessage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public interface ChatService {

    public VBox getSingleChatVbox(int chatId);

    public VBox getGroupChatVbox(int chatId);

    HBox sendGroupMessage(GroupMessage message);

    HBox recGroupMessage(GroupMessage message);

    HBox sendChatMessage(ChatMessage message);

    HBox recChatMessage(ChatMessage message);

    void updateChatList(int chatID, String content);

    void updateGroupChatList(int groupChatID, String content);

    int createChat(Chat chat);

    Chat getChat(int chatID);
}
