package com.chatup.controllers.reposotories.implementations;

import com.chatup.controllers.reposotories.interfaces.FriendRequestRepo;
import com.chatup.models.entities.FriendRequest;
import com.chatup.models.enums.FriendRequestStatus;
import com.chatup.utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestRepoImpl implements FriendRequestRepo {

    private static FriendRequestRepo friendRequestRepo;

    private FriendRequestRepoImpl(){}

    public static FriendRequestRepo getFriendRequestRepo(){
        if (friendRequestRepo == null)
            friendRequestRepo = new FriendRequestRepoImpl();
        return friendRequestRepo;
    }

    @Override
    public int createFriendRequest(FriendRequest request) {

        if(FriendRequestRepoImpl.getFriendRequestRepo().getFriendRequest(request.getSenderID(),request.getReceiverID())!=null) {
            System.out.println("Invitation already exist");
            return -1;
        }

        String query = "INSERT INTO friend_request(sender_id,reciver_id,request_status) VALUES(?,?,?)";
        try(PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            stmnt.setInt(1,request.getSenderID());
            stmnt.setInt(2,request.getReceiverID());
            stmnt.setString(3, (request.getRequestStatus() != null) ? request.getRequestStatus().toString() : null);
            if(stmnt.executeUpdate() == 0){
                System.out.println("Request was not inserted");
                return -1;
            }else{
                ResultSet res = stmnt.getGeneratedKeys();
                if(res.next()){
                    return res.getInt(1);
                }else{
                    System.out.println("Request was not inserted no ID returned");
                    return -1;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean deleteFriendRequest(int id) {
        String query = "DELETE FROM friend_request WHERE request_id = ?";
        try(PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)){
            stmnt.setInt(1, id);
            if(stmnt.executeUpdate() == 0){
                System.out.println("Request was not deleted");
                return false;
            }else{
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteFriendRequest(int senderID, int receiverID) {
        String query = "DELETE FROM friend_request WHERE sender_id = ? AND reciver_id = ?";
        try(PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)){
            stmnt.setInt(1, senderID);
            stmnt.setInt(2, receiverID);
            if(stmnt.executeUpdate() == 0){
                System.out.println("Request was not deleted");
                return false;
            }else{
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateFriendRequestStatus(int id, FriendRequestStatus newStatus) {
        String query = "UPDATE friend_request SET request_status = ? WHERE request_id = ?";
        try(PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)){
            stmnt.setString(1, (newStatus != null) ? newStatus.toString() : null);
            stmnt.setInt(2,id);
            if(stmnt.executeUpdate() == 0){
                System.out.println("Request was not updated");
                return false;
            }else{
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateFriendRequestStatus(int senderID, int receiverID, FriendRequestStatus newStatus) {
        String query = "UPDATE friend_request SET request_status = ? WHERE sender_id = ? AND reciver_id = ?";
        try(PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)){
            stmnt.setString(1, (newStatus != null) ? newStatus.toString() : null);
            stmnt.setInt(2,senderID);
            stmnt.setInt(3,receiverID);
            if(stmnt.executeUpdate() == 0){
                System.out.println("Request was not updated");
                return false;
            }else{
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public FriendRequest getFriendRequest(int id) {
        String query = "SELECT * FROM friend_request WHERE request_id = ?";
        try(PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)){
            stmnt.setInt(1, id);
            ResultSet res = stmnt.executeQuery();
            if(res.next()){
                return resultSetToFriendRequest(res);
            }else{
                System.out.println("Request not found");
                return null;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FriendRequest getFriendRequest(int senderID, int receiverID) {
        String query = "SELECT * FROM friend_request WHERE sender_id = ? AND receiver_id = ?";
        try(PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)){
            stmnt.setInt(1, senderID);
            stmnt.setInt(2, receiverID);
            ResultSet res = stmnt.executeQuery();
            if(res.next()){
                return resultSetToFriendRequest(res);
            }else{
                System.out.println("Request not found");
                return null;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<FriendRequest> getAllUserFriendRequests(int userID, FriendRequestStatus status) {
        String query = "SELECT * FROM friend_request WHERE (reciver_id = ? OR sender_id = ?) AND request_status = ?";
        try(PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)){
            stmnt.setInt(1,userID);
            stmnt.setInt(2,userID);
            stmnt.setString(3, status.toString());
            ResultSet res = stmnt.executeQuery();
            List<FriendRequest> requests = new ArrayList<FriendRequest>();
            while(res.next()){
                requests.add(resultSetToFriendRequest(res));
            }
            return requests;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<FriendRequest> getUserFriendRequests(int userID, FriendRequestStatus status) {
        String query = "SELECT * FROM friend_request WHERE reciver_id = ? AND request_status = ?";
        try(PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)){
            stmnt.setInt(1,userID);
            stmnt.setString(2, status.toString());
            ResultSet res = stmnt.executeQuery();
            List<FriendRequest> requests = new ArrayList<FriendRequest>();
            while(res.next()){
                requests.add(resultSetToFriendRequest(res));
            }
            return requests;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<FriendRequest> getUserSentFriendRequests(int userID, FriendRequestStatus status) {
        String query = "SELECT * FROM friend_request WHERE sender_id = ? AND request_status = ?";
        try(PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)){
            stmnt.setInt(1, userID);
            stmnt.setString(2, status.toString());
            ResultSet res = stmnt.executeQuery();
            List<FriendRequest> requests = new ArrayList<FriendRequest>();
            while(res.next()){
                requests.add(resultSetToFriendRequest(res));
            }
            return requests;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private FriendRequest resultSetToFriendRequest(ResultSet res){
        try {
            return new FriendRequest(res.getInt("request_id"),res.getInt("sender_id"),
                    res.getInt("reciver_id"),FriendRequestStatus.valueOf(res.getString("request_status")));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}