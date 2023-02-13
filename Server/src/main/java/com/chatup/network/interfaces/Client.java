package com.chatup.network.interfaces;

import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.GroupMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    public void sendGroupMessage(GroupMessage message) throws RemoteException;
    public void sendChatMessage(ChatMessage message) throws RemoteException;
}
