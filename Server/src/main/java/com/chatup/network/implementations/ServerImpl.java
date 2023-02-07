package com.chatup.network.implementations;

import com.chatup.models.entities.User;
import com.chatup.network.interfaces.Client;
import com.chatup.network.interfaces.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
    public User login(String phone, String password, Client client) throws RemoteException{
        User user = new User.Builder(phone,"ziad",null).id(3).build(); //TODO get from user services login service when implemented
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
        User user = null; //TODO get from user services get user
        clients.remove(id, client);
        //TODO notify friends
    }
}
