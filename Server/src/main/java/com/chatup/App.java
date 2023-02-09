package com.chatup;

import com.chatup.controllers.services.implementations.UserServicesImpl;

public class App {
    public static void main(String[] args) {

        UserServicesImpl userServices = new UserServicesImpl();
        userServices.getUserchats(6);
    }
}