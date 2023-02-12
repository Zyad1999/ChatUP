package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.Card;
import com.chatup.models.entities.FriendRequest;
import com.chatup.models.entities.User;
import com.chatup.models.enums.UserStatus;
import javafx.collections.ObservableList;

public interface UserLists {

    public ObservableList<Card> getAllUserChats();

    ObservableList<Card> getAllUserGroups();

    ObservableList<Card> getUserFriends(UserStatus status);
    ObservableList<User> getAllUserFriendRequests();
    Boolean updatesUserFriendRequests(FriendRequest friendRequestList);
}
