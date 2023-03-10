package com.chatup.controllers.reposotories.implementations;

import com.chatup.controllers.reposotories.interfaces.ChatRepo;
import com.chatup.models.entities.Chat;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.User;
import com.chatup.utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        Chat chat =ChatRepoImpl.getInstance().getSingleChat(singleChat.getFirstUserId(),singleChat.getSecondUserId());
        if(chat != null) {
            System.out.println(("found the chat"));
            System.out.println("chat already exist ");
            return chat.getId();
        }
        System.out.println("Did not find the chat");
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
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
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
    public Chat getSingleChat(int firstUserId, int secondUserId) {
        Chat singleChat = null;
        String sql = "select * from chat where (first_user_id = ? AND second_user_id = ?) OR (first_user_id = ? AND second_user_id = ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, firstUserId);
            ps.setInt(2, secondUserId);
            ps.setInt(3, secondUserId);
            ps.setInt(4, firstUserId);
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
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, singleChatId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               users.add(UserRepoImpl.getUserRepo().getUser(rs.getInt("first_user_id")));
                users.add(UserRepoImpl.getUserRepo().getUser(rs.getInt("second_user_id")));
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
    public boolean updateSingleChat(Chat singleChat) {
        String sql = "UPDATE chat SET first_user_id = ? , second_user_id = ? where chat_id= ? ";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, singleChat.getFirstUserId());
            ps.setInt(2, singleChat.getSecondUserId());
            ps.setInt(3, singleChat.getId());
            if(ps.executeUpdate()!=0){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteSingleChat(int singleChatId) {
        String sql = "delete from chat where chat_id= ?";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, singleChatId);
            if(stmt.executeUpdate()!=0){
                return true;
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Chat> getAllUserChats(int userId) {
        List<Chat> userChats = new ArrayList<>();
        String selectQuerey = "select * from chat where ? in (first_user_id,second_user_id)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectQuerey, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            Chat chat =null;
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                chat = new Chat(resultSet.getInt("chat_id"),resultSet.getInt("first_user_id"),resultSet.getInt("second_user_id"));
                userChats.add(chat);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userChats;
    }

    @Override
    public ChatMessage getLastMessage(int chatid) {
        ChatMessage chatMessage = null;
        String selectQuerey = "select message_id,sender_id,content,message_date,chat_id,attachment_id from chat_message where chat_id=? and time(message_date) = (select max(time(message_date)) from chat_message where chat_id =?) ";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectQuerey, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, chatid);
            ps.setInt(2, chatid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               chatMessage = new ChatMessage(rs.getInt("message_id"),rs.getInt("chat_id"),rs.getInt("sender_id"),rs.getString("content"),rs.getTimestamp("message_date").toLocalDateTime(),rs.getInt("attachment_id"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return chatMessage;
    }



}