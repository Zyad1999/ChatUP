package com.chatup.controllers.reposotories.implementations;

import com.chatup.controllers.reposotories.interfaces.AttachmentRepo;
import com.chatup.models.entities.Attachment;
import com.chatup.utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AttachmentRepoImp  implements AttachmentRepo {
    private static AttachmentRepoImp attachmentRepoImp ;
    protected AttachmentRepoImp() {
    }
    public static AttachmentRepoImp getInstance() {
        if (attachmentRepoImp == null) {
                attachmentRepoImp = new AttachmentRepoImp();
        }
        return attachmentRepoImp;
    }


    @Override
    public boolean deleteAttachment(int id)  {
        String deleteStatement = "delete  from attachment where attachment_id =?";
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
    public int addAttachment(Attachment attachment)  {
        int id =-1;

        String insertSQL = "insert into attachment ( attachment_type ,extension , byte_size , location ) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, attachment.getAttachmentType());
            ps.setString(2, attachment.getExtension());
            ps.setInt(3, attachment.getByteSize());
            ps.setString(4,attachment.getLocation());
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
    public Attachment getAttachment(int id)  {
        String getAttachmentQuery =   "select attachment_type , extension , byte_size , location  from attachment where attachment_id  = ?";
        Attachment attachment = null;
        try(PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(getAttachmentQuery)){
            preparedStatement.setInt(1,id);
            ResultSet res = preparedStatement.executeQuery();
            if(res.next()) {

                attachment = new Attachment(id,res.getString("attachment_type"), res.getString("extension"), res.getInt("byte_size"), res.getString("location"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return attachment;
    }

    @Override
    public boolean updateAttachmentt(Attachment attachment)  {
        String updateSQL = "update attachment set attachment_type  = ?, extension  = ?, byte_size  = ? ,location = ? where attachment_id  = ? ";
        try (PreparedStatement ps =DBConnection.getConnection().prepareStatement(updateSQL)) {
            ps.setString(1, attachment.getAttachmentType());
            ps.setString(2, attachment.getExtension());
            ps.setInt(3, attachment.getByteSize());
            ps.setString(4, attachment.getLocation());
            ps.setInt(5, attachment.getId());
            if(ps.executeUpdate()==1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public List<Attachment> getAllAttachment() throws RuntimeException {
        List<Attachment> attachments = new ArrayList<>();
        String selectSQL = "select  * from attachment ";
        try (PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(selectSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Attachment newAttachment;
            while (resultSet.next()) {
                newAttachment = new Attachment(resultSet.getInt("attachment_id"), resultSet.getString("attachment_type"), resultSet.getString("extension"), resultSet.getInt("byte_size"),resultSet.getString("location"));
                attachments.add(newAttachment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attachments;
    }
}
