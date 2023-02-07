package com.chatup.network.interfaces;

import com.chatup.models.entities.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    public User login(String phone, String password, Client client) throws RemoteException;
    public void logout(int id, Client client) throws RemoteException;
}