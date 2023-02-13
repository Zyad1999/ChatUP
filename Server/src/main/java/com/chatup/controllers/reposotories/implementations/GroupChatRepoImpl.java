package com.chatup.controllers.reposotories.implementations;

import com.chatup.controllers.reposotories.interfaces.GroupChatRepo;
import com.chatup.models.entities.GroupChat;
import com.chatup.models.entities.GroupMessage;
import com.chatup.utils.DBConnection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        System.out.println("from setgroupmessage function ");
        try {
            while (resultSet.next()) {
                System.out.println("from setgroupmessage WHile ");
                System.out.println(resultSet.getString("content"));
                GroupMessage groupMessage = new GroupMessage(resultSet.getInt("group_message_id"), resultSet.getInt("sender_id"),
                        resultSet.getString("content"),
                        resultSet.getTimestamp("message_date").toLocalDateTime(),
                        resultSet.getInt("group_chat_id"), resultSet.getInt("attachment_id"));
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
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, groupChat.getGroupTitle());
            statement.setString(2,(groupChat.getGroupImage() != null) ? saveImg(groupChat.getGroupImage(),groupChat.getGroupTitle()) : null );
            if (statement.executeUpdate() > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    System.out.println("Not create a group");
                    return -1;
                }
            } else {
                System.out.println("Not create a group");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public GroupChat getGroupChat(int groupChatId) {
        GroupChat groupChat = null;
        String sql = "SELECT group_title, group_image FROM group_chat WHERE group_chat_id = ?";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setInt(1, groupChatId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                groupChat = new GroupChat(groupChatId,resultSet.getString("group_title"), Files.readAllBytes((resultSet.getString("group_image")==null)? new File(UserRepoImpl.class.getResource("/Images/default_profile_pic.jpg").toURI()).toPath() :new File(resultSet.getString("group_image")).toPath()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return groupChat;
    }

    @Override
    public GroupMessage getLastMessage(int groupID) {
        GroupMessage chatMessage = null;
        String selectQuerey = "select * from group_message where group_chat_id=? and time(message_date) = (select max(time(message_date)) from group_message where group_chat_id =?) ";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectQuerey, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, groupID);
            ps.setInt(2, groupID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                chatMessage = new GroupMessage(rs.getInt("group_message_id"), rs.getInt("sender_id"), rs.getString("content"), rs.getTimestamp("message_date").toLocalDateTime(), rs.getInt("group_chat_id"), rs.getInt("attachment_id"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return chatMessage;
    }

    // get all messages in chat
    @Override
    public List<GroupMessage> getGroupMessages(int ChatId) {
        System.out.println(ChatId+ "groupchatid");
        List<GroupMessage> groupMessageList = new ArrayList<GroupMessage>();
        String sql = "SELECT * FROM group_message WHERE group_chat_id = ?";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, ChatId);
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
            statement.setString(2,saveImg(groupChat.getGroupImage(),groupChat.getGroupTitle()));
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

    public String saveImg(byte[] img,String groupName){
        FileOutputStream fos = null;
        String imgID = UUID.randomUUID().toString();
        String path = "./src/main/resources/files/imgs/"+groupName;
        try {
            File theDir = new File(path);
            if (!theDir.exists()){
                theDir.mkdirs();
            }
            fos = new FileOutputStream(path+"/"+imgID+".jpg");
            fos.write(img);
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path+"/"+imgID+".jpg";
    }
}