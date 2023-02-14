package com.chatup.utils;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.models.entities.*;
import com.chatup.models.enums.CardType;

public class CardMapper {

    private CardMapper(){}

    public static Card getCard(User user){
        return new Card(user.getId(),user.getUserName(),user.getBio(),CardType.FRIEND,user.getImg());
    }

    public static Card getCard(Chat chat, ChatMessage message){
        int userID = (chat.getFirstUserId()== CurrentUserImp.getCurrentUser().getId()) ? chat.getSecondUserId() : chat.getFirstUserId();
        User user = UserServicesImpl.getUserServices().getUser(userID);
        return new Card(chat.getId(),user.getUserName(),message.getContent(),CardType.CHAT,user.getImg());
    }

    public static Card getCard(Chat chat, String message){
        int userID = (chat.getFirstUserId()== CurrentUserImp.getCurrentUser().getId()) ? chat.getSecondUserId() : chat.getFirstUserId();
        User user = UserServicesImpl.getUserServices().getUser(userID);
        return new Card(chat.getId(),user.getUserName(),message,CardType.CHAT,user.getImg());
    }

    public static Card getCard(GroupChat group, GroupMessage message){
        return new Card(group.getGroupChatID(),group.getGroupTitle(),message.getContent(),CardType.GROUP, group.getGroupImage());
    }

    public static Card getCard(GroupChat group, String message){
        return new Card(group.getGroupChatID(),group.getGroupTitle(),message,CardType.GROUP, group.getGroupImage());
    }
}
