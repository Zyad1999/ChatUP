package com.chatup.network.implementations;

import com.chatup.controllers.services.implementations.FriendsServicesImpl;
import com.chatup.controllers.services.implementations.UserAuthImpl;
import com.chatup.controllers.services.implementations.UserGroupsServiceImp;
import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.models.entities.*;
import com.chatup.network.interfaces.Client;
import com.chatup.network.interfaces.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl extends UnicastRemoteObject implements Server {
    private static Server server;
    public static ConcurrentHashMap<Integer, Client> clients = new ConcurrentHashMap<>();
    private ServerImpl() throws RemoteException {}

    public static Server getServer(){
        try {
            if(server==null)
                server = new ServerImpl();
            return server;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int signup(User user) throws RemoteException {
        return UserAuthImpl.getUserAuth().sign_Up(user);
    }

    @Override
    public User login(String phone, String password, Client client) throws RemoteException{
        User user = UserAuthImpl.getUserAuth().sign_In(phone, password);
        if(user != null){
            clients.put(user.getId(),client);
            //TODO notify friends
            return user;
        }else{
            return null;
        }
    }

    @Override
    public void logout(int id, Client client) throws RemoteException {
        List<User> friends = FriendsServicesImpl.getFriendsServices().getUserFriends(id);
        clients.remove(id, client);
        UserAuthImpl.getUserAuth().logout(id);
        //TODO notify friends
    }

    @Override
    public Map<Chat,ChatMessage> getUserChats(int userID) throws RemoteException {
        return UserServicesImpl.getUserServices().getUserchats(userID);
    }

    @Override
    public Map<GroupChat, GroupMessage> getUserGroups(int userID) throws RemoteException {
        return UserGroupsServiceImp.getUserGroupsService().getAllUserGroups(userID);
    }

    @Override
    public List<ChatMessage> getChatMessages(int chatID) throws RemoteException {
        return UserServicesImpl.getUserServices().getChatMsg(chatID);
    }

    @Override
    public List<GroupMessage> getGroupMessages(int groupID) throws RemoteException {
        return UserGroupsServiceImp.getUserGroupsService().getAllGroupMessages(groupID);
    }

    @Override
    public List<User> getUserFriends(int userID) throws RemoteException {
        return FriendsServicesImpl.getFriendsServices().getUserFriends(userID);
    }

    @Override
    public List<User> getUserFriendRequests(int userID) throws RemoteException {
        return FriendsServicesImpl.getFriendsServices().getUserFriendRequests(userID);
    }
}