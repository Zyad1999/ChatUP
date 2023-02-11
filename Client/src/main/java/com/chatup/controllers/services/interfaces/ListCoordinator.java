package com.chatup.controllers.services.interfaces;

import com.chatup.controllers.services.implementations.UserListsImpl;
import com.chatup.models.entities.Card;
import com.chatup.models.enums.UserStatus;
import javafx.collections.ObservableList;

public interface ListCoordinator {

    public ObservableList<Card> getUserChats();

    public ObservableList<Card> getUserGroups();

    public ObservableList<Card> getUserOnlineFriends();

    public ObservableList<Card> getUserOfflineFriends();
}
