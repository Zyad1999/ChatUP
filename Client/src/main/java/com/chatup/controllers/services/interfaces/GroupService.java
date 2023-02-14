package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.GroupChat;
import com.chatup.models.entities.User;

import java.rmi.RemoteException;
import java.util.List;

public interface GroupService {

     int createGroupChat(GroupChat groupChat , List<User> userList);

     void addUsersToGroup(int groupChatId,List<User> userList);

    GroupChat getGroupChat(int groupID);
}
