package com.chatup.controllers.reposotories.implementations;

import com.chatup.controllers.reposotories.interfaces.GroupMessageRepo;
import com.chatup.models.entities.GroupMessage;
import com.chatup.utils.DBConnection;

import java.sql.*;

public class GroupMessageRepoImp implements GroupMessageRepo {
    private static GroupMessageRepoImp instance;

    public static GroupMessageRepo getInstance() {
        if (instance == null) {
            instance = new GroupMessageRepoImp();
        }
        return instance;
    }

    // add a new message to a group chat
    @Override
    public int createGroupMessage(GroupMessage groupMessage) {
        String sql = "INSERT INTO group_message (sender_id, content, message_date, group_chat_id, attachment_id) " + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, groupMessage.getSenderId());
            statement.setString(2, groupMessage.getContent());
            statement.setTimestamp(3, Timestamp.valueOf(groupMessage.getMessageDate()));
            statement.setInt(4, groupMessage.getGroupChatId());
            if(groupMessage.getAttachmentID()!=0){
                statement.setInt(5, groupMessage.getAttachmentID());
            }else {
                statement.setString(5,null);
            }
            if (statement.executeUpdate() > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    System.out.println("Not added a new message");
                    return -1;
                }
            } else {
                System.out.println("Not added a new message");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public GroupMessage getGroupMessage(int groupMessageId) {
        GroupMessage groupMessage = null;
        String sql = "SELECT * FROM group_message WHERE group_message_id = ?";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setInt(1, groupMessageId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                groupMessage = new GroupMessage(resultSet.getInt("group_message_id"), resultSet.getInt("sender_id"), resultSet.getString("content"), resultSet.getTimestamp("message_date").toLocalDateTime(), resultSet.getInt("group_chat_id"), resultSet.getInt("attachment_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupMessage;
    }

    @Override
    public boolean updateGroupMessage(GroupMessage groupMessage) {
        String sql = "UPDATE group_message SET content = ?, message_date = ? where group_message_id = ?";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, groupMessage.getContent());
            statement.setTimestamp(2, Timestamp.valueOf(groupMessage.getMessageDate()));
            statement.setInt(3, groupMessage.getGroupMessageId());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteGroupMessage(int groupMessageId) {
        String sql = "DELETE FROM group_message WHERE group_message_id = ?";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, groupMessageId);
            if (statement.executeUpdate() > 0) {
                System.out.println("deleted successfully");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}