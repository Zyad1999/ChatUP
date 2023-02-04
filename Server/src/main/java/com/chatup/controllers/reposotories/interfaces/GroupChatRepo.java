package com.chatup.controllers.reposotories.interfaces;

import com.chatup.models.entities.GroupChat;
import com.chatup.models.entities.GroupMessage;

import java.util.List;

public interface GroupChatRepo {
    int createGroupChat(GroupChat groupChat);

    GroupChat getGroupChat(int groupChatId);

    List<GroupMessage> getGroupMessages(int groupChatId);

    boolean updateGroupChat(GroupChat groupChat);

    boolean deleteGroupChat(int groupChatId);
}