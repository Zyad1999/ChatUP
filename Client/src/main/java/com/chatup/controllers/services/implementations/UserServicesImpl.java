package com.chatup.controllers.services.implementations;

import com.chatup.controllers.services.interfaces.UserServices;
import com.chatup.models.entities.FriendRequest;
import com.chatup.models.entities.User;
import com.chatup.network.ServerConnection;

import java.rmi.RemoteException;
import java.util.List;

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
    @Override
    public Boolean UpdateUser(User userInfo){
        try {
            return ServerConnection.getServer().updateUserInfo(userInfo);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public Boolean UpdateUserImage(int userID, String phone, byte[] img){
        try {
            return ServerConnection.getServer().updateUserImage(userID,phone,img);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public Boolean UpdateUserPassword(int userID, String password){
        try {
            return ServerConnection.getServer().updateUserPassword(userID,password);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getFiendData(String userPhone) {
        try {
            return ServerConnection.getServer().getUser(userPhone);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public Boolean createFriendRequests(List<FriendRequest> friendRequestList) {
        try {
            return ServerConnection.getServer().sendFriendRequest(friendRequestList);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

}
