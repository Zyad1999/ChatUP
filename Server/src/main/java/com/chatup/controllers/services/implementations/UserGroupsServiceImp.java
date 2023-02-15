package com.chatup.controllers.services.implementations;

import com.chatup.controllers.reposotories.implementations.GroupChatRepoImpl;
import com.chatup.controllers.reposotories.implementations.GroupMembershipRepoImpl;
import com.chatup.controllers.reposotories.implementations.GroupMessageRepoImp;
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
        List<GroupMembership> groupMemberships = GroupMembershipRepoImpl.getInstance().getAllGroupMembership(user_id);
        for (GroupMembership groupMembership : groupMemberships) {
            groups.add(GroupChatRepoImpl.getInstance().getGroupChat(groupMembership.getGroupChatId()));
        }
        for (GroupChat group:  groups) {
            GroupMessage message = GroupChatRepoImpl.getInstance().getLastMessage(group.getGroupChatID());
            if(message!=null) {
                groupsWithMess.put(group, message);
            }else {
                groupsWithMess.put(group, new GroupMessage(0,null,LocalDateTime.now()));
            }
        }

        Map<GroupChat, GroupMessage> sortedMapInDescending = groupsWithMess.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.<GroupChat, GroupMessage>comparingByValue()))
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(),
                        (entry1, entry2) -> entry2, LinkedHashMap::new));
        return sortedMapInDescending;

    }

    @Override
    public List<User> getGroupMembers(int groupID){
        List<GroupMembership> memberships = GroupMembershipRepoImpl.getInstance().getContactsGroupMembership(groupID);
        List<User> members = new ArrayList<>();
        for (GroupMembership membership: memberships){
            User member = UserServicesImpl.getUserServices().getUserInfo(membership.getUserId());
            if(member != null){
                members.add(member);
            }
        }
        return members;
    }

    @Override
    public int sendGroupMessage(GroupMessage message){
        return GroupMessageRepoImp.getInstance().createGroupMessage(message);
    }
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

    @Override
    public GroupChat getGroupChat(int groupID){
        return GroupChatRepoImpl.getInstance().getGroupChat(groupID);
    }

    @Override
    public boolean deleteUserFromGroup(int userId, int groupId) {
        return GroupMembershipRepoImpl.getInstance().deleteGroupMembership(userId,groupId);
    }

}