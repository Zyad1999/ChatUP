package com.chatup.utils;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.models.entities.*;
import com.chatup.models.enums.CardType;
import com.chatup.models.enums.UserStatus;

public class CardMapper {

    private CardMapper(){}

    public static Card getCard(User user) {
        if (user.getStatus().equals(UserStatus.ONLINE)) {
            return new Card(user.getId(), user.getUserName(), user.getMode().toString(), CardType.FRIEND, user.getImg());
        }else {
            return new Card(user.getId(), user.getUserName(), user.getStatus().toString(), CardType.FRIEND, user.getImg());
        }
    }

    public static Card getCard(Chat chat, ChatMessage message){
        int userID = (chat.getFirstUserId()== CurrentUserImp.getCurrentUser().getId()) ? chat.getSecondUserId() : chat.getFirstUserId();
        User user = UserServicesImpl.getUserServices().getUser(userID);
        String newContent = "";
        if (message.getContent().length() > 100) {
            newContent = message.getContent().substring(0, 100) + "....";
        }
        return new Card(chat.getId(),user.getUserName(),newContent,CardType.CHAT,user.getImg());
    }

    public static Card getCard(Chat chat, String message){
        int userID = (chat.getFirstUserId()== CurrentUserImp.getCurrentUser().getId()) ? chat.getSecondUserId() : chat.getFirstUserId();
        User user = UserServicesImpl.getUserServices().getUser(userID);
        String newContent = "";
        if (message.length() > 100) {
            newContent = message.substring(0, 100) + "....";
        }
        return new Card(chat.getId(),user.getUserName(),newContent,CardType.CHAT,user.getImg());
    }

    public static Card getCard(GroupChat group, GroupMessage message){
        String newContent = "";
        if (message.getContent().length() > 100) {
            newContent = message.getContent().substring(0, 100) + "....";
        }
        return new Card(group.getGroupChatID(),group.getGroupTitle(),newContent,CardType.GROUP, group.getGroupImage());
    }

    public static Card getCard(GroupChat group, String message){
        String newContent = "";
        if (message.length() > 100) {
            newContent = message.substring(0, 100) + "....";
        }
        return new Card(group.getGroupChatID(),group.getGroupTitle(),newContent,CardType.GROUP, group.getGroupImage());
    }
}
