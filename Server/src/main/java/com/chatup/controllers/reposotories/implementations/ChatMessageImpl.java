package com.chatup.controllers.reposotories.implementations;

import com.chatup.controllers.reposotories.interfaces.ChatMessageRepo;
import com.chatup.models.entities.Chat;
import com.chatup.models.entities.ChatMessage;
import com.chatup.utils.DBConnection;

import java.rmi.RemoteException;
import java.sql.*;


public class ChatMessageImpl implements ChatMessageRepo {
    private static ChatMessageImpl instance;
    protected ChatMessageImpl()  {
    }

    public static ChatMessageRepo getInstance() {
        if (instance == null) {
                instance = new ChatMessageImpl();
        }
        return instance;
    }

    @Override
    public int createSingleChatMessage(ChatMessage singleChatMessage)  {
        String query = "INSERT INTO chat_message(sender_id,content,message_date,chat_id,attachment_id) VALUES(?,?)";
        try(PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            stmnt.setInt(1, singleChatMessage.getSenderId());
            stmnt.setString(2, singleChatMessage.getContent());
            stmnt.setTimestamp(3, Timestamp.valueOf(singleChatMessage.getMessageDateTime()));
            if(stmnt.executeUpdate() == 0){
                System.out.println("ChatMessage was not Created");
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
    public ChatMessage getSingleChatMessage(int singleChatMessageId)  {
        ChatMessage singleChatMessage = null;
        String sql = "select chat_id, sender_id,content,message_date,chat_id,attachment_id from chat_message where message_id= ? ";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setInt(1, singleChatMessageId);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                singleChatMessage = new ChatMessage(rs.getInt(1), rs.getInt(2), rs.getInt(3),rs.getString(4),
                        rs.getTimestamp(5).toLocalDateTime(),rs.getInt(6));
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return singleChatMessage;
    }

    @Override
    public void updateSingleChatMessage(ChatMessage singleChatMessage)  {
        String sql = "UPDATE CHAT_MESSAGE SET sender_ID = ?, CONTENT= ?, MESSAGE_DATE=?,chat_id=?,attachment_id=? where message_id=?";
        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, singleChatMessage.getSenderId());
            preparedStatement.setString(2, singleChatMessage.getContent());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(singleChatMessage.getMessageDateTime()));
            preparedStatement.setInt(4, singleChatMessage.getChatId());
            preparedStatement.setInt(5, singleChatMessage.getAttachment_Id());
            preparedStatement.setInt(6, singleChatMessage.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteSingleChatMessage(int singleChatMessageId)  {
        String sql = "delete from CHAT_MESSAGE where message_id = ?";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, singleChatMessageId);
            stmt.executeUpdate();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }
}
