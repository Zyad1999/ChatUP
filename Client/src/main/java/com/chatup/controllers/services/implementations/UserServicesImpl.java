package com.chatup.controllers.services.implementations;

import com.chatup.controllers.services.interfaces.UserServices;
import com.chatup.models.entities.User;
import com.chatup.network.ServerConnection;

import java.rmi.RemoteException;

public class UserServicesImpl implements UserServices {

    private static UserServices userServices;

    private UserServicesImpl(){}

    public static UserServices getUserServices(){
        if (userServices==null)
            userServices = new UserServicesImpl();
        return userServices;
    }
    @Override
    public User getUser(int userID) {
        try {
            return ServerConnection.getServer().getUser(userID);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public User getUser(String phoneNumber) {
        try {
            return ServerConnection.getServer().getUser(phoneNumber);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
