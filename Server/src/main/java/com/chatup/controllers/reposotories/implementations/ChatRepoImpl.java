package com.chatup.controllers.reposotories.implementations;

import com.chatup.controllers.reposotories.interfaces.ChatRepo;
import com.chatup.controllers.reposotories.interfaces.UserRepo;
import com.chatup.models.entities.Chat;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.User;
import com.chatup.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatRepoImpl  implements ChatRepo {

    private static ChatRepoImpl instance;

    protected ChatRepoImpl() {
    }

    public static ChatRepo getInstance() {
        if (instance == null) {
                instance = new ChatRepoImpl();
        }
        return instance;
    }

    @Override
    public int createSingleChat(Chat singleChat) {
        String query = "INSERT INTO chat(first_user_id,second_user_id) VALUES(?,?)";
        try(PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS)){
            stmnt.setInt(1, singleChat.getFirstUserId());
            stmnt.setInt(2, singleChat.getSecondUserId());
            if(stmnt.executeUpdate() == 0){
                System.out.println("Chat was not Created");
                return -1;
            }else{
                ResultSet res = stmnt.getGeneratedKeys();
                if(res.next()){
                    return res.getInt(1);
                }else{
                    System.out.println("Chat was not Created no ID returned");
                    return -1;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Chat getSingleChat(int singleChatId) {
        Chat singleChat = null;
        String sql = "select chat_id, first_user_id, second_user_id from chat where chat_id= ? ";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, singleChatId);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                singleChat = new Chat(rs.getInt(1), rs.getInt(2), rs.getInt(3));
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return singleChat;
    }

    @Override
    public List<User> getSingleChatUsers(int singleChatId) {
        List<User> users = new ArrayList<>();
        String sql = "select first_user_id, second_user_id from chat where chat_id= ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, singleChatId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
//                users.add(resultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<ChatMessage> getSingleChatMessages(int singleChatId) {
        String sql = "select message_id, chat_id, sender_id, content, message_date ,attachment_id from chat_message where chat_id = ?";
        List<ChatMessage> singleChatMessages = new ArrayList<>();
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, singleChatId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                singleChatMessages.add(new ChatMessage(rs.getInt(1),
                        rs.getInt(2), rs.getInt(3),
                        rs.getString(4), rs.getTimestamp(5).toLocalDateTime(),rs.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return singleChatMessages;
    }

    @Override
    public void updateSingleChat(Chat singleChat) {
        String sql = "UPDATE chat SET first_user_id = ? , second_user_id = ? where chat_id= ? ";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, singleChat.getFirstUserId());
            ps.setInt(2, singleChat.getSecondUserId());
            ps.setInt(3, singleChat.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSingleChat(int singleChatId) {
        String sql = "delete from chat where chat_id= ?";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, singleChatId);
            stmt.executeUpdate();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }
}