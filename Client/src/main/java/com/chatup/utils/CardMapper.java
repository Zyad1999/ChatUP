package com.chatup.utils;

import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.models.entities.*;
import com.chatup.models.enums.CardType;

public class CardMapper {

    private CardMapper(){}

    public static Card getCard(User user){
        return new Card(user.getId(),user.getUserName(),user.getBio(),CardType.FRIEND,user.getImg());
    }

    public static Card getCard(Chat chat, ChatMessage message){
        User user = UserServicesImpl.getUserServices().getUser(message.getSenderId());
        return new Card(chat.getId(),user.getUserName(),message.getContent(),CardType.CHAT,user.getImg());
    }

    public static Card getCard(GroupChat group, GroupMessage message){
        return new Card(group.getGroupChatID(),group.getGroupTitle(),message.getContent(),CardType.GROUP, group.getGroupImage());
    }
}