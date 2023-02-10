package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.GroupChat;
import com.chatup.models.entities.GroupMembership;
import com.chatup.models.entities.GroupMessage;

import java.util.List;

public interface UserGroupsService {
    List<GroupMessage> getAllGroupMessages(int groupChatId);
    List<GroupChat> getAllUserGroups(int user_id);
}
