package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.GroupChat;
import com.chatup.models.entities.GroupMessage;

import java.util.List;
import java.util.Map;

public interface UserGroupsService {
    List<GroupMessage> getAllGroupMessages(int groupChatId);
    Map<GroupChat, GroupMessage> getAllUserGroups(int user_id);
}
