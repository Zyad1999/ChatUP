package com.chatup.controllers.reposotories.implementations;

import com.chatup.controllers.reposotories.interfaces.GroupMembershipRepo;
import com.chatup.models.entities.GroupMembership;
import com.chatup.utils.DBConnection;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupMembershipRepoImpl  implements GroupMembershipRepo {
    private static GroupMembershipRepoImpl groupMembership ;
    protected GroupMembershipRepoImpl() throws RemoteException {
        super();
    }
    public  static GroupMembershipRepoImpl getInstance(){
        if(groupMembership == null){
            try {
                groupMembership = new GroupMembershipRepoImpl();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }
        return groupMembership;
    }

    @Override
    public boolean deleteGroupMembership(int id) {

        String sql = "delete from group_membership where membership_id = ?";

            try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, id);

                if (preparedStatement.executeUpdate() == 1) {
                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        return false;
    }

    @Override
    public int createGroupMembership(GroupMembership groupMembership){
        int id = -1;

        String insertSQL = "insert into group_membership ( user_id , group_chat_id, join_date ) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, groupMembership.getUserId());
            preparedStatement.setInt(2, groupMembership.getGroupChatId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(groupMembership.getJoinDate()));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }

        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean updateGroupMembership(GroupMembership groupMembership) {
        String updateSQL = "update group_membership set user_id =?, group_chat_id =?, join_date=? where membership_id =?";
        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(updateSQL)) {
            preparedStatement.setInt(1, groupMembership.getUserId());
            preparedStatement.setInt(2, groupMembership.getGroupChatId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(groupMembership.getJoinDate()));
            preparedStatement.setInt(4, groupMembership.getId());

            if(preparedStatement.executeUpdate()==1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /*get all groups for this membership_id */
    @Override
    public GroupMembership getGroupMembership(int membershipId) {
        GroupMembership groupMembership = null;
        String selectSQL = "select group_chat_id, user_id, join_date from group_membership where membership_id = ? ";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            ps.setInt(1, membershipId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                groupMembership = new GroupMembership( membershipId,rs.getInt("group_chat_id"), rs.getInt("user_id"), rs.getTimestamp("join_date").toLocalDateTime());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return groupMembership;
    }
    /*get all groups for this user id*/
    @Override
    public List<GroupMembership> getAllGroupMembership(int user_id) {
        List<GroupMembership> groupsMempership = new ArrayList<>();
        GroupMembership groupMembership = null;
        String selectSQL = "select membership_id,group_chat_id ,join_date   from group_membership where user_id = ? ";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                groupMembership = new GroupMembership(rs.getInt("membership_id"), rs.getInt("group_chat_id"), user_id, rs.getTimestamp("join_date").toLocalDateTime());
                groupsMempership.add(groupMembership);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return groupsMempership;
    }
    // get all contacts for this grpoup
    @Override
    public List<GroupMembership> getContactsGroupMembership(int groupid) {
        List<GroupMembership> groupsMempership = new ArrayList<>();
        GroupMembership groupMembership = null;
        String selectSQL = "select  membership_id,user_id ,join_date   from group_membership where group_chat_id = ? ";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectSQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            ps.setInt(1, groupid);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                groupMembership = new GroupMembership(resultSet.getInt("membership_id"),groupid, resultSet.getInt("user_id"), resultSet.getTimestamp("join_date").toLocalDateTime());
                groupsMempership.add(groupMembership);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return groupsMempership;
    }


}
