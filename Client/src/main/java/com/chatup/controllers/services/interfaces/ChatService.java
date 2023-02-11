package com.chatup.controllers.services.interfaces;

import javafx.scene.layout.VBox;

public interface ChatService {

    public VBox getSingleChatVbox(int chatId);

    public VBox getGroupChatVbox(int chatId);
}
