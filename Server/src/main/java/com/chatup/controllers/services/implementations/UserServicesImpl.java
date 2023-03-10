package com.chatup.controllers.services.implementations;

import com.chatup.controllers.reposotories.implementations.ChatRepoImpl;
import com.chatup.controllers.reposotories.implementations.UserRepoImpl;
import com.chatup.controllers.services.interfaces.UserServices;
import com.chatup.models.entities.Chat;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserServicesImpl implements UserServices {

    private static UserServices userServices;

    private UserServicesImpl() {
    }

    public static UserServices getUserServices() {
        if (userServices == null) userServices = new UserServicesImpl();
        return userServices;
    }

    @Override
    public User getUserInfo(int userId) {
        User user = UserRepoImpl.getUserRepo().getUser(userId);
        if (user == null) {
            return null;
        }
        user.setPassword(null);
        return user;
    }

    @Override
    public User getUserInfo(String phone) {
        User user = UserRepoImpl.getUserRepo().getUser(phone);
        if (user == null) {
            return null;
        }
        user.setPassword(null);
        return user;
    }

    @Override
    public Map<Chat, ChatMessage> getUserchats(int userId) {
        Map<Chat, ChatMessage> userMessages = new HashMap<>();
        ChatMessage chatMessage;
        for ( Chat chat:  ChatRepoImpl.getInstance().getAllUserChats(userId) ) {
            chatMessage= ChatRepoImpl.getInstance().getLastMessage(chat.getId());
            if(chatMessage!=null)
                userMessages.put(chat, chatMessage);
        }

        Map<Chat, ChatMessage> sortedMapInDescending = userMessages.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.<Chat,ChatMessage>comparingByValue()))
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(),
                        (entry1, entry2) -> entry2, LinkedHashMap::new));
        return sortedMapInDescending;
    }

    @Override
    public List<ChatMessage> getChatMsg(int chatId) {
        return ChatRepoImpl.getInstance().getSingleChatMessages(chatId);
    }

    @Override
    public int getNumberAllUsers() {
        return UserRepoImpl.getUserRepo().getNumbersAllUsersOnSystem();
    }

    @Override
    public int getNumberAllMaleUsers() {
        return UserRepoImpl.getUserRepo().getNumberAllMaleUsers();
    }

    @Override
    public int getNumberAllOnlineUsers() {
        return UserRepoImpl.getUserRepo().getNumberAllOnlineUsers();
    }

    @Override
    public int getNumberAllCountryOfUsers(String country) {
        return UserRepoImpl.getUserRepo().getNumberAllCountryOfUsers(country);
    }
    @Override
    public boolean updateUserInfo (User user){
        return UserRepoImpl.getUserRepo().updateUser(user);
    }

    @Override
    public boolean updateUserImage(int userID, String phone, byte[] img) {
        return UserRepoImpl.getUserRepo().updateUserImg(userID,phone,img);
    }

    @Override
    public boolean updateUserPassword(int userID, String password) {
        return UserRepoImpl.getUserRepo().updateUserPassword(userID,password);
    }
}
