package com.chatup.controllers.services.implementations;

import com.chatup.controllers.reposotories.implementations.UserRepoImpl;
import com.chatup.controllers.services.interfaces.UserAuth;
import com.chatup.models.entities.User;

public class UserAuthImpl implements UserAuth {
    @Override
    public User sign_In(String phone_Num, String pass) {
        User user = UserRepoImpl.getUserRepo().getUser(phone_Num);
        if(user!=null){
            if(user.getPassword().equals(pass)){
                System.out.println("Login Successfully");
                return user;
            }
            else{
                System.out.println("Password Incorrect");
                return null;
            }
        }
        else{
            System.out.println("User not found Failed");
            return null;
        }

    }
    @Override
    public int sign_Up(User user) {
        int id = UserRepoImpl.getUserRepo().createUser(user);
        if (id!= -1)
        {
            System.out.println("Sign UP Successfully");
            return id;
        }
        else{
            System.out.println("Sign Up Failed");
            return id;
        }
    }
}
