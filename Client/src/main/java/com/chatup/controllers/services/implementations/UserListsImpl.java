package com.chatup.controllers.services.implementations;

import com.chatup.controllers.services.interfaces.UserLists;
import com.chatup.models.entities.*;
import com.chatup.models.enums.UserStatus;
import com.chatup.network.ServerConnection;
import com.chatup.network.interfaces.Server;
import com.chatup.utils.CardMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class UserListsImpl implements UserLists {

    private static UserLists userLists;

    private UserListsImpl(){}

    public static UserLists getUserLists(){
        if (userLists==null)
            userLists = new UserListsImpl();
        return userLists;
    }

    @Override
    public ObservableList<Card> getAllUserChats(){
        try {
            Server server = ServerConnection.getServer();
            Map<Chat, ChatMessage> userChats = null;
            userChats = server.getUserChats(3);
            ObservableList<Card> cardList = FXCollections.<Card>observableArrayList();
            Card card = null;
            for (Map.Entry<Chat, ChatMessage> set : userChats.entrySet()) {
                card = CardMapper.getCard(set.getKey(), set.getValue());
                cardList.add(card);
            }
            return  cardList;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ObservableList<Card> getAllUserGroups(){
        try {
            Server server = ServerConnection.getServer();
            Map<GroupChat, GroupMessage> userGroups = null;
            userGroups = server.getUserGroups(3);
            ObservableList<Card> cardList = FXCollections.<Card>observableArrayList();
            Card card = null;
            for (Map.Entry<GroupChat, GroupMessage> set : userGroups.entrySet()) {
                card = CardMapper.getCard(set.getKey(), set.getValue());
                cardList.add(card);

            }
            return  cardList;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ObservableList<Card> getUserFriends(UserStatus status){
        try {
            ObservableList<Card> cardList = FXCollections.<Card>observableArrayList();
            List<User> friends = ServerConnection.getServer().getUserFriends(3);
            for(User friend:friends){
                if(friend.getStatus()== status){
                    cardList.add(CardMapper.getCard(friend));
                }
            }
            return cardList;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

}
