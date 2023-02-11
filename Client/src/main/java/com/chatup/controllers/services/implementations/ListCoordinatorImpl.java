package com.chatup.controllers.services.implementations;

import com.chatup.controllers.services.interfaces.ListCoordinator;
import com.chatup.models.entities.Card;
import com.chatup.models.enums.UserStatus;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class ListCoordinatorImpl implements ListCoordinator {

    private static ListCoordinatorImpl listCoordinatorImpl;
    private static ObservableList<Card> userChats;
    private static ObservableList<Card> userGroups;
    private static ObservableList<Card> userOnlineFriends;
    private static ObservableList<Card> userOfflineFriends;

    private static HashMap<Integer, VBox> singleChatMap = new HashMap<>();

    private static HashMap<Integer, VBox> groupChatMap = new HashMap<>();


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
    @Override
    public VBox getSingleChatVbox(int chatId){
        if(singleChatMap.containsKey(chatId)){
            return singleChatMap.get(chatId);
        }
        else{
            VBox box = ChatServicesImpl.getChatService().getSingleChatVbox(chatId);
            singleChatMap.put(chatId,box);
            return box;
        }
    }
    @Override
    public VBox getGroupChatVbox(int chatId){
        if(groupChatMap.containsKey(chatId)){
            return groupChatMap.get(chatId);
        }
        else{
            VBox box = ChatServicesImpl.getChatService().getGroupChatVbox(chatId);
            groupChatMap.put(chatId,box);
            return box;
        }
    }
}