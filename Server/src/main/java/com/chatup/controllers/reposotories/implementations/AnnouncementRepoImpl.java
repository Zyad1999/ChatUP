package com.chatup.controllers.reposotories.implementations;

import com.chatup.controllers.reposotories.interfaces.AnnouncementRepoInt;
import com.chatup.models.entities.Announcement;
import com.chatup.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementRepoImpl implements AnnouncementRepoInt {
    private static AnnouncementRepoImpl announcementInstance;
    protected AnnouncementRepoImpl() {
    }
    public static AnnouncementRepoImpl getInstance() {
        if (announcementInstance == null) {

                announcementInstance = new AnnouncementRepoImpl();

        }
        return announcementInstance;
    }

    @Override
    public boolean deleteAnnouncement( int id)  {

        String deleteStatement = "delete  from announcement where announcement_id=?";
        try (PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(deleteStatement)) {
            pStatement.setInt(1,id);
            if(pStatement.executeUpdate()==1){
                DBConnection.stopConnection();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int createAnnouncement(Announcement announcement)  {
        int id =-1;
        String insertSQL = "insert into announcement ( title, content, announcement_date) VALUES ( ?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, announcement.getTitle());
            ps.setString(2, announcement.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(announcement.getData()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
           /* send to all clients */
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Announcement getAnnouncement(int id)  {
        String getAnnouncementQuery =   "select announcement_id, title, content, announcement_date from announcement where announcement_id = ?";
        Announcement announcement = null;
        try(PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(getAnnouncementQuery)){
            preparedStatement.setInt(1,id);
            ResultSet res = preparedStatement.executeQuery();
            if(res.next()) {

                announcement = new Announcement(res.getInt("announcement_id"), res.getString("title"), res.getString("content"), res.getTimestamp("announcement_date").toLocalDateTime());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return announcement;
    }

    @Override
    public boolean updateAnnouncement(Announcement announcement)  {
        String updateSQL = "update announcement set title = ?, content = ?, announcement_date = ? where announcement_id = ? ";
        try (PreparedStatement ps =DBConnection.getConnection().prepareStatement(updateSQL)) {
            ps.setString(1, announcement.getTitle());
            ps.setString(2, announcement.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(announcement.getData()));
            ps.setInt(4, announcement.getId());
            if(ps.executeUpdate()==1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public List<Announcement> getAllAnnouncement()  {
        List<Announcement> announcements = new ArrayList<>();
        String selectSQL = "select  * FROM announcement ";
        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(selectSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Announcement newAnnouncement;
            while (resultSet.next()) {
                newAnnouncement = new Announcement(resultSet.getInt("announcement_id"), resultSet.getString("title"), resultSet.getString("content"), resultSet.getTimestamp("announcement_date").toLocalDateTime());
                announcements.add(newAnnouncement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return announcements;
    }

}
