package com.chatup.network.implementations;

import com.chatup.network.interfaces.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client {

    private static Client client;

    private ClientImpl() throws RemoteException {}

    public static Client getClient(){
        try {
            if(client==null)
                client = new ClientImpl();
            return client;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
