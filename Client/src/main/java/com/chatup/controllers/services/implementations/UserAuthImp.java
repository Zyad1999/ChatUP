package com.chatup.controllers.services.implementations;

import com.chatup.controllers.services.interfaces.UserAuth;
import com.chatup.models.entities.User;
import com.chatup.network.ServerConnection;
import com.chatup.network.implementations.ClientImpl;

import java.rmi.RemoteException;

public class UserAuthImp implements UserAuth {
    private static UserAuth userAuth;

    private UserAuthImp() {
    }

    public static UserAuth getUserAuth() {
        if (userAuth == null)
            userAuth = new UserAuthImp();
        return userAuth;
    }

    @Override
    public User loginAuth(String phoneNumber, String password) {
        try {
            return ServerConnection.getServer().login(phoneNumber, password, ClientImpl.getClient());
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int signUpAuth(User user) {
        try {
            return ServerConnection.getServer().signup(user);
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
