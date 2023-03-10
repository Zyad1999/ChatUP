package com.chatup.network.interfaces;

import com.chatup.models.entities.Announcement;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.GroupMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    public void sendGroupMessage(GroupMessage message) throws RemoteException;
    public void sendChatMessage(ChatMessage message) throws RemoteException;

    void addedToGroup(int groupID) throws RemoteException;

    public void friendLoggedIn(int friendID) throws RemoteException;
    public void friendLoggedOut(int friendID) throws RemoteException;
    public void friendAcceptedRequest(int friendID) throws RemoteException;
    public void receivedFriendRequest(int friendID) throws RemoteException;
    public void receiveAnnouncement(Announcement announcement) throws RemoteException;

    public void disconnect() throws RemoteException;
}
