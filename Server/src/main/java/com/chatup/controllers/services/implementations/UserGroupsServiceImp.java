package com.chatup.controllers.services.implementations;

import com.chatup.controllers.reposotories.implementations.GroupChatRepoImpl;
import com.chatup.controllers.reposotories.implementations.GroupMembershipRepoImpl;
import com.chatup.controllers.services.interfaces.UserGroupsService;
import com.chatup.models.entities.GroupChat;
import com.chatup.models.entities.GroupMembership;
import com.chatup.models.entities.GroupMessage;

import java.util.ArrayList;
import java.util.List;

public class UserGroupsServiceImp implements UserGroupsService {
    @Override
    public List<GroupMessage> getAllGroupMessages(int groupChatId) {
        return GroupChatRepoImpl.getInstance().getGroupMessages(groupChatId);
    }
    @Override
    public List<GroupChat> getAllUserGroups(int user_id) {
        List<GroupChat> groups = new ArrayList<>();
        List<GroupMembership> groupMemberships = GroupMembershipRepoImpl.getInstance().getAllGroupMembership(user_id);
        for (GroupMembership groupMembership : groupMemberships) {
            groups.add(GroupChatRepoImpl.getInstance().getGroupChat(groupMembership.getGroupChatId()));
        }
        return groups;
    }
}