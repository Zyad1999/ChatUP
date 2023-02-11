package com.chatup.controllers.services.implementations;

import com.chatup.controllers.services.interfaces.ListCoordinator;
import com.chatup.models.entities.Card;
import com.chatup.models.enums.UserStatus;
import javafx.collections.ObservableList;

public class ListCoordinatorImpl implements ListCoordinator {

    private static ListCoordinatorImpl listCoordinatorImpl;
    private static ObservableList<Card> userChats;
    private static ObservableList<Card> userGroups;
    private static ObservableList<Card> userOnlineFriends;
    private static ObservableList<Card> userOfflineFriends;

    private ListCoordinatorImpl(){}

    public static ListCoordinatorImpl getListCoordinator(){
        if(listCoordinatorImpl == null)
            listCoordinatorImpl = new ListCoordinatorImpl();
        return listCoordinatorImpl;
    }

    @Override
    public ObservableList<Card> getUserChats() {
        if(userChats==null)
            userChats = UserListsImpl.getUserLists().getAllUserChats();
        return userChats;
    }

    @Override
    public ObservableList<Card> getUserGroups() {
        if(userGroups==null)
            userGroups = UserListsImpl.getUserLists().getAllUserGroups();
        return userGroups;
    }

    @Override
    public ObservableList<Card> getUserOnlineFriends() {
        if(userOnlineFriends==null)
            userOnlineFriends = UserListsImpl.getUserLists().getUserFriends(UserStatus.ONLINE);
        return userOnlineFriends;
    }

    @Override
    public ObservableList<Card> getUserOfflineFriends() {
        if(userOfflineFriends==null)
            userOfflineFriends = UserListsImpl.getUserLists().getUserFriends(UserStatus.OFFLINE);
        return userOfflineFriends;
    }
}