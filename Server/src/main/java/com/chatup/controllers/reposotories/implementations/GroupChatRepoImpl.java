package com.chatup.controllers.reposotories.implementations;

import com.chatup.controllers.reposotories.interfaces.GroupChatRepo;
import com.chatup.models.entities.GroupChat;
import com.chatup.models.entities.GroupMessage;
import com.chatup.utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupChatRepoImpl implements GroupChatRepo {
    private static GroupChatRepoImpl instance;

    public static GroupChatRepo getInstance() {
        if (instance == null) {
            instance = new GroupChatRepoImpl();
        }
        return instance;
    }

    public static List<GroupMessage> setToGroupMessageList(ResultSet resultSet) {
        List<GroupMessage> groupMessageList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                GroupMessage groupMessage = new GroupMessage(resultSet.getInt("group_message_id"), resultSet.getInt("sender_id"), resultSet.getString("content"), resultSet.getTimestamp("message_date").toLocalDateTime(), resultSet.getInt("chat_id"), resultSet.getInt("attachment_id"));
                groupMessageList.add(groupMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupMessageList;
    }

    @Override
    public int createGroupChat(GroupChat groupChat) {
        String sql = "INSERT INTO group_chat (group_title, group_image) VALUES (?, ?)";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, groupChat.getGroupTitle());
            statement.setString(2, groupChat.getGroupImage());
            if (statement.executeUpdate() > 0) {
                return groupChat.getGroupChatID();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public GroupChat getGroupChat(int groupChatId) {
        GroupChat groupChat = null;
        String sql = "SELECT group_title, group_image FROM group_chat WHERE group_chat_id = ?";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setInt(1, groupChatId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                groupChat = new GroupChat(resultSet.getString("group_title"), resultSet.getString("group_image"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupChat;
    }

    // get all messages in chat
    @Override
    public List<GroupMessage> getGroupMessages(int groupChatId) {
        List<GroupMessage> groupMessageList = new ArrayList<GroupMessage>();
        String sql = "SELECT sender_id, content, message_date, group_chat_id, attachment_id FROM group_message WHERE group_message_id = ?";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, groupChatId);
            ResultSet resultSet = statement.executeQuery();
            groupMessageList = setToGroupMessageList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupMessageList;
    }

    @Override
    public boolean updateGroupChat(GroupChat groupChat) {
        String sql = "UPDATE group_chat SET group_title = ?, group_image = ? WHERE group_chat_id = ?";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, groupChat.getGroupTitle());
            statement.setString(2, groupChat.getGroupImage());
            statement.setInt(3, groupChat.getGroupChatID());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteGroupChat(int groupChatId) {
        String sql = "DELETE FROM group_chat WHERE group_chat_id = ?";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, groupChatId);
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