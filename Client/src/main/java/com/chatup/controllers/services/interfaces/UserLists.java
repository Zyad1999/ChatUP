package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.Card;
import com.chatup.models.enums.UserStatus;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;

public interface UserLists {

    public ObservableList<Card> getAllUserChats();

    ObservableList<Card> getAllUserGroups();

    ObservableList<Card> getUserFriends(UserStatus status);
}
