package com.chatup.controllers.services.implementations;

import com.chatup.controllers.reposotories.implementations.ChatMessageRepoImpl;
import com.chatup.controllers.reposotories.implementations.ChatRepoImpl;
import com.chatup.controllers.services.interfaces.UserChatServices;
import com.chatup.models.entities.Chat;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.User;

import java.util.List;

public class UserChatServicesImpl implements UserChatServices {

    private static UserChatServices userChatServices;

    private UserChatServicesImpl(){}

    public static UserChatServices getUserChatServices(){
        if(userChatServices == null)
            userChatServices = new UserChatServicesImpl();
        return userChatServices;
    }

    @Override
    public int sendChatMessage(ChatMessage message){
        return ChatMessageRepoImpl.getInstance().createSingleChatMessage(message);
    }

    @Override
    public Chat getChat(int chatID){
        return ChatRepoImpl.getInstance().getSingleChat(chatID);
    }

    @Override
    public int createChat(Chat chat){
        return ChatRepoImpl.getInstance().createSingleChat(chat);
    }

    @Override
    public List<User> getSingleChatUsers(int singleChatId) {
        return ChatRepoImpl.getInstance().getSingleChatUsers(singleChatId);
    }


}