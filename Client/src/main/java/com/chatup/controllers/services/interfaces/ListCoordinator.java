package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.Card;
import com.chatup.models.entities.User;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

public interface ListCoordinator {

    public ObservableList<Card> getUserChats();

    public ObservableList<Card> getUserGroups();

    public ObservableList<Card> getUserOnlineFriends();

    public ObservableList<Card> getUserOfflineFriends();
    public void updateOnlineFriends();
    public void updateFriendRequests();
    VBox getSingleChatVbox(int chatId);

    VBox getGroupChatVbox(int chatId);
     ObservableList<User> getGroupMembers(int groupId);
}
