package com.chatup.network;

import com.chatup.network.implementations.ClientImpl;
import com.chatup.network.interfaces.Client;
import com.chatup.network.interfaces.Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServerConnection {

    private static Server server;
    private final static String serverURI = "rmi://localhost:1900/ChatUPServer";

    private ServerConnection(){}

    public static Server getServer(){
        try {
            if(server == null)
                server = (Server) Naming.lookup(serverURI);
            return server;
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
