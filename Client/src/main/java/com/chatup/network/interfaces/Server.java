package com.chatup.network.interfaces;

import com.chatup.models.entities.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Server extends Remote {

    public int signup(User user) throws RemoteException;
    public User login(String phone, String password, Client client) throws RemoteException;
    public void logout(int id, Client client) throws RemoteException;
    public Map<Chat,ChatMessage> getUserChats(int userID) throws RemoteException;
    public Map<GroupChat,GroupMessage> getUserGroups(int userID) throws RemoteException;
    public List<ChatMessage> getChatMessages(int chatID) throws RemoteException;
    public List<GroupMessage> getGroupMessages(int groupID) throws RemoteException;
    public List<User> getUserFriends(int userID) throws RemoteException;
    public List<User> getUserFriendRequests(int userID) throws RemoteException;
    public User getUser(int userID) throws RemoteException;
    public User getUser(String phoneNumber) throws RemoteException;
}