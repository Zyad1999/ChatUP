package com.chatup.controllers.services.implementations;

import com.chatup.controllers.services.interfaces.GroupService;
import com.chatup.models.entities.GroupChat;
import com.chatup.models.entities.User;
import com.chatup.network.ServerConnection;
import com.chatup.network.interfaces.Server;

import java.rmi.RemoteException;
import java.util.List;

public class GroupServicesImpl implements GroupService {
    private static GroupService groupService;

    private GroupServicesImpl(){}

    public static GroupService getGroupService(){
        if (groupService ==null)
            groupService = new GroupServicesImpl();
        return groupService;
    }

    @Override
    public int createGroupChat(GroupChat groupChat, List<User> userList) {
        try {
            Server server = ServerConnection.getServer();
            return server.createGroupChat(groupChat,userList);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addUsersToGroup(int groupChatId, List<User> userList) {
        try {
            Server server = ServerConnection.getServer();
           server.addUsersToGroup(groupChatId,userList);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GroupChat getGroupChat(int groupID){
        try {
            return ServerConnection.getServer().getGroupChat(groupID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
