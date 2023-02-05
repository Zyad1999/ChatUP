package com.chatup.controllers.reposotories.interfaces;

import com.chatup.models.entities.GroupMembership;

import java.util.List;

public interface GroupMembershipRepoInt {
    public boolean deleteGroupMembership(int id) ;
    public int createGroupMembership(GroupMembership groupMembership);
    public List<GroupMembership> getAllGroupMembership(int userId );
    
    public GroupMembership getGroupMembership(int membershipId );
    public boolean updateGroupMembership(GroupMembership groupMembership) ;
    public List<GroupMembership> getContactsGroupMembership(int groupId);
}
