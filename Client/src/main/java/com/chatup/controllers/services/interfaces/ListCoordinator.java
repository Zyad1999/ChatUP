package com.chatup.controllers.services.interfaces;

import com.chatup.controllers.services.implementations.UserListsImpl;
import com.chatup.models.entities.Card;
import com.chatup.models.enums.UserStatus;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

public interface ListCoordinator {

    public ObservableList<Card> getUserChats();

    public ObservableList<Card> getUserGroups();

    public ObservableList<Card> getUserOnlineFriends();

    public ObservableList<Card> getUserOfflineFriends();

    VBox getSingleChatVbox(int chatId);

    VBox getGroupChatVbox(int chatId);
}
