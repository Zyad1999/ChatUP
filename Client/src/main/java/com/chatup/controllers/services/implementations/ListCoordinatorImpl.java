package com.chatup.controllers.services.implementations;

import com.chatup.controllers.services.interfaces.ListCoordinator;
import com.chatup.models.entities.Card;
import com.chatup.models.entities.FriendRequest;
import com.chatup.models.entities.User;
import com.chatup.models.enums.CardType;
import com.chatup.models.enums.UserStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListCoordinatorImpl implements ListCoordinator {

    private static ListCoordinatorImpl listCoordinatorImpl;
    private static ObservableList<Card> userChats;
    private static ObservableList<Card> userGroups;
    private static ObservableList<Card> userOnlineFriends;
    private static ObservableList<Card> userOfflineFriends;

    private static ObservableList<User> userFriendRequests;
    private static ObservableList<User> groupMembers;

    private static HashMap<Integer, VBox> singleChatMap = new HashMap<>();

    private static HashMap<Integer, VBox> groupChatMap = new HashMap<>();

    private static List<Boolean> requests = new ArrayList<>();

    public static CardType currentList;

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
    public ObservableList<User> getGroupMembers(int groupId) {

          return  groupMembers = FXCollections.<User>observableArrayList( UserListsImpl.getUserLists().getAllgroupMembers(groupId));

    }
    @Override
    public ObservableList<Card> getUserGroups() {
        if(userGroups==null)
            userGroups = UserListsImpl.getUserLists().getAllUserGroups();
        return userGroups;
    }
    public ObservableList<User> getAllUserFriendRequests() {
        if(userFriendRequests==null)
            userFriendRequests = UserListsImpl.getUserLists().getAllUserFriendRequests();
        return userFriendRequests;
    }
    public Boolean updatesUserFriendRequests(FriendRequest friendRequests) {
        return UserListsImpl.getUserLists().updatesUserFriendRequests(friendRequests);

    }

    public void updatesUserGroups() {
        userGroups = UserListsImpl.getUserLists().getAllUserGroups();
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
    public void updateOnlineFriends() {
            userOnlineFriends= UserListsImpl.getUserLists().getUserFriends(UserStatus.ONLINE);
    }

    @Override
    public void updateOfflineFriends() {
        userOfflineFriends= UserListsImpl.getUserLists().getUserFriends(UserStatus.OFFLINE);
    }

    @Override
    public void updateFriendRequests() {
        userFriendRequests = UserListsImpl.getUserLists().getAllUserFriendRequests();
    }

    @Override
    public VBox getSingleChatVbox(int chatId){
        if(singleChatMap.containsKey(chatId)){
            System.out.println("get old VBOX");
            return singleChatMap.get(chatId);
        }
        else{
            System.out.println("Create new VBOX");
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