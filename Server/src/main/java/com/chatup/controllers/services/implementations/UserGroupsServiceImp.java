package com.chatup.controllers.services.implementations;

import com.chatup.controllers.reposotories.implementations.GroupChatRepoImpl;
import com.chatup.controllers.reposotories.implementations.GroupMembershipRepoImpl;
import com.chatup.controllers.services.interfaces.UserGroupsService;
import com.chatup.models.entities.*;

import java.time.LocalDateTime;
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
        System.out.println(user_id);
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

    @Override
    public int createGroupChat(GroupChat groupChat , List<User> userList) {
       int groupChatId= GroupChatRepoImpl.getInstance().createGroupChat(groupChat);
       addUsersToGroup(groupChatId,userList);
       return groupChatId;
    }

    @Override
    public void addUsersToGroup(int groupChatId,List<User> userList) {
        for(User user: userList) {
            GroupMembershipRepoImpl.getInstance().createGroupMembership(new GroupMembership(groupChatId,user.getId(), LocalDateTime.now() ));
            System.out.println("User "+ user.getUserName() + " added to group "+ groupChatId);
        }
    }


}