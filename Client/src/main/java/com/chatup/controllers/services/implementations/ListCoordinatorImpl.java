package com.chatup.controllers.services.implementations;

import com.chatup.controllers.services.interfaces.ListCoordinator;
import com.chatup.models.entities.Card;
import com.chatup.models.entities.FriendRequest;
import com.chatup.models.entities.User;
import com.chatup.models.enums.CardType;
import com.chatup.models.enums.UserStatus;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListCoordinatorImpl implements ListCoordinator {

    public static CardType currentList;
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
    private static VBox announcemets;

    private ListCoordinatorImpl() {
    }

    public static ListCoordinatorImpl getListCoordinator() {
        if (listCoordinatorImpl == null)
            listCoordinatorImpl = new ListCoordinatorImpl();
        return listCoordinatorImpl;
    }

    @Override
    public ObservableList<Card> getUserChats() {
        if (userChats == null)
            userChats = UserListsImpl.getUserLists().getAllUserChats();
        return userChats;
    }

    @Override
    public ObservableList<User> getGroupMembers(int groupId) {
        if (groupMembers == null) {
            groupMembers = FXCollections.<User>observableArrayList(UserListsImpl.getUserLists().getAllgroupMembers(groupId));
        } else {
            groupMembers.clear();
            groupMembers.addAll(UserListsImpl.getUserLists().getAllgroupMembers(groupId));
        }
        return groupMembers;
    }

    @Override
    public void updateGroupMembers(int chat_id) {


    }

    @Override
    public VBox getAnnouncement() {
        if (announcemets == null) {
            announcemets = AnnouncementServicesImp.getAnnouncementService().getAnnouncementVBox();
        }
        return announcemets;
    }
    
    public void refershAnnouncmentVBox() {
        announcemets = AnnouncementServicesImp.getAnnouncementService().getAnnouncementVBox();
    }
    
    @Override
    public ObservableList<Card> getUserGroups() {
        if (userGroups == null)
            userGroups = UserListsImpl.getUserLists().getAllUserGroups();
        return userGroups;
    }

    public ObservableList<User> getAllUserFriendRequests() {

        return UserListsImpl.getUserLists().getAllUserFriendRequests();
    }

    public Boolean updatesUserFriendRequests(FriendRequest friendRequests) {
        return UserListsImpl.getUserLists().updatesUserFriendRequests(friendRequests);

    }

    public void updatesUserGroups() {
        userGroups.clear();
        userGroups.addAll(UserListsImpl.getUserLists().getAllUserGroups());
    }

    @Override
    public ObservableList<Card> getUserOnlineFriends() {
        if (userOnlineFriends == null)
            userOnlineFriends = UserListsImpl.getUserLists().getUserFriends(UserStatus.ONLINE);
        else {
            userOnlineFriends.clear();
            userOnlineFriends.addAll(UserListsImpl.getUserLists().getUserFriends(UserStatus.ONLINE));
        }

        return userOnlineFriends;
    }

    @Override
    public ObservableList<Card> getUserOfflineFriends() {
        if (userOfflineFriends == null)
            userOfflineFriends = UserListsImpl.getUserLists().getUserFriends(UserStatus.OFFLINE);
        else {
            userOfflineFriends.clear();
            userOfflineFriends.addAll(UserListsImpl.getUserLists().getUserFriends(UserStatus.OFFLINE));
        }
        return userOfflineFriends;
    }

    @Override
    public void updateOnlineFriends() {
        userOnlineFriends = UserListsImpl.getUserLists().getUserFriends(UserStatus.ONLINE);
    }

    @Override
    public void updateOfflineFriends() {
        userOfflineFriends = UserListsImpl.getUserLists().getUserFriends(UserStatus.OFFLINE);
    }

    @Override
    public void updateFriendRequests() {
        userFriendRequests = UserListsImpl.getUserLists().getAllUserFriendRequests();
    }

    @Override
    public VBox getSingleChatVbox(int chatId) {
        if (singleChatMap.containsKey(chatId)) {
            System.out.println("get old VBOX");
            return singleChatMap.get(chatId);
        } else {
            System.out.println("Create new VBOX");
            VBox box = ChatServicesImpl.getChatService().getSingleChatVbox(chatId);
            singleChatMap.put(chatId, box);
            return box;
        }
    }

    @Override
    public VBox getGroupChatVbox(int chatId) {
        if (groupChatMap.containsKey(chatId)) {
            return groupChatMap.get(chatId);
        } else {
            VBox box = ChatServicesImpl.getChatService().getGroupChatVbox(chatId);
            groupChatMap.put(chatId, box);
            return box;
        }
    }
    public boolean annoncementNull() {
        if (announcemets == null) {
            return true;
        }
        return false;
    }

    public boolean chatNull(int chatID){
        return singleChatMap.containsKey(chatID);
    }

    public boolean groupChatNull(int groupID){
        return groupChatMap.containsKey(groupID);
    }

    public boolean groupChatsNull(){
        if(userGroups == null)
            return true;
        return false;
    }

    public void flushLists(){
        Platform.runLater(()->{
            currentList = null;
            listCoordinatorImpl= null;
            if(userChats!=null){
                userChats.clear();
                userChats= null;
            }
            if(userGroups!=null){
                userGroups.clear();
                userGroups= null;
            }
            if(userOnlineFriends!=null){
                userOnlineFriends.clear();
                userOnlineFriends= null;
            }
            if(userOfflineFriends!=null){
                userOfflineFriends.clear();
                userOfflineFriends= null;
            }
            if(userFriendRequests!=null){
                userFriendRequests.clear();
                userFriendRequests= null;
            }
            if(groupMembers!=null){
                groupMembers.clear();
                groupMembers= null;
            }
            singleChatMap = new HashMap<>();
            groupChatMap = new HashMap<>();
            requests = new ArrayList<>();
        });
    }
}