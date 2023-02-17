package com.chatup.controllers.services.implementations;

import com.chatup.controllers.reposotories.implementations.UserRepoImpl;
import com.chatup.controllers.services.interfaces.UserAuth;
import com.chatup.models.entities.User;
import com.chatup.models.enums.UserStatus;
import com.chatup.utils.PasswordAuthentication;

public class UserAuthImpl implements UserAuth {

    private static UserAuth userAuth;

    private UserAuthImpl(){}

    public static UserAuth getUserAuth(){
        if(userAuth == null)
            userAuth = new UserAuthImpl();
        return userAuth;
    }

    @Override
    public User sign_In(String phone_Num, String pass) {
        User user = UserRepoImpl.getUserRepo().getUser(phone_Num);
        if(user!=null){
            if(new PasswordAuthentication().authenticate(pass.toCharArray(),user.getPassword())){
                System.out.println("Login Successfully");
                user.setStatus(UserStatus.ONLINE);
                UserRepoImpl.getUserRepo().updateUser(user);
                return user;
            } else {
                System.out.println("Password Incorrect");
                return null;
            }
        } else {
            System.out.println("User not found Failed");
            return null;
        }
    }

    @Override
    public boolean logout(int userID) {
        User user = UserRepoImpl.getUserRepo().getUser(userID);
        if (user != null) {
            user.setStatus(UserStatus.OFFLINE);
            UserRepoImpl.getUserRepo().updateUser(user);
            return true;
        } else {
            System.out.println("User Not Found");
            return false;
        }
    }

    @Override
    public int sign_Up(User user) {
        int id = UserRepoImpl.getUserRepo().createUser(user);
        if (id != -1) {
            System.out.println("Sign UP Successfully");
            return id;
        } else {
            System.out.println("Sign Up Failed");
            return id;
        }
    }
}
