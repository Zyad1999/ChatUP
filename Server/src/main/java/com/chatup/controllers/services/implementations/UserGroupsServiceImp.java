package com.chatup.controllers.services.implementations;

import com.chatup.controllers.reposotories.implementations.GroupChatRepoImpl;
import com.chatup.controllers.reposotories.implementations.GroupMembershipRepoImpl;
import com.chatup.controllers.services.interfaces.UserGroupsService;
import com.chatup.models.entities.*;

import java.util.*;
import java.util.stream.Collectors;

public class UserGroupsServiceImp implements UserGroupsService {

    private static UserGroupsService userGroupsService;

    private UserGroupsServiceImp(){}

    public static UserGroupsService getUserGroupsService(){
        if(userGroupsService == null)
            userGroupsService = new UserGroupsServiceImp();
        return userGroupsService;
    }

    @Override
    public List<GroupMessage> getAllGroupMessages(int groupChatId) {
        return GroupChatRepoImpl.getInstance().getGroupMessages(groupChatId);
    }
    @Override
    public Map<GroupChat, GroupMessage> getAllUserGroups(int user_id) {
        Map<GroupChat, GroupMessage> groupsWithMess = new HashMap<>();
        List<GroupChat> groups = new ArrayList<>();
        List<GroupMembership> groupMemberships = GroupMembershipRepoImpl.getInstance().getAllGroupMembership(user_id);
        for (GroupMembership groupMembership : groupMemberships) {
            groups.add(GroupChatRepoImpl.getInstance().getGroupChat(groupMembership.getGroupChatId()));
        }
        for (GroupChat group:  groups) {
            GroupMessage message = GroupChatRepoImpl.getInstance().getLastMessage(group.getGroupChatID());
            groupsWithMess.put(group, message);
        }
        Map<GroupChat, GroupMessage> resultSet = groupsWithMess.entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().getMessageDate()))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (left, right) -> left,
                        LinkedHashMap::new));
        return resultSet;
    }
}