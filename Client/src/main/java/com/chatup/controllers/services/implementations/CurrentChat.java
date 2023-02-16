package com.chatup.controllers.services.implementations;

import com.chatup.models.entities.Chat;
import com.chatup.models.entities.GroupChat;
import com.chatup.models.enums.CardType;
import com.chatup.models.enums.ChatType;

public class CurrentChat {

    private static CurrentChat currentChat;

    private int chatID;
    private ChatType chatType;

    public static CurrentChat getCurrentChat(){
        return currentChat;
    }

    public static void setCurrentChat(CurrentChat currentCha){
        currentChat = currentCha;
    }

    public static void setCurrentChatGroup(int groupID){
        if(currentChat == null)
            currentChat = new CurrentChat();
        currentChat.chatID = groupID;
        currentChat.chatType = ChatType.GROUP;
    }

    public static void setCurrentChatSingle(int chatID){
        if(currentChat == null)
            currentChat = new CurrentChat();
        currentChat.chatID = chatID;
        currentChat.chatType = ChatType.SINGLE;
    }

    public ChatType getCurrentChatType(){
        return currentChat.chatType;
    }

    public int getCurrentChatID(){
        return currentChat.chatID;
    }
}
