package com.chatup.controllers.reposotories.implementations;

import com.chatup.controllers.reposotories.interfaces.UserRepo;
import com.chatup.models.entities.User;
import com.chatup.models.enums.Gender;
import com.chatup.models.enums.UserMode;
import com.chatup.models.enums.UserStatus;
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

public class UserRepoImpl implements UserRepo {

    private static UserRepo userRepo;

    private UserRepoImpl() {
    }

    public static UserRepo getUserRepo() {
        if (userRepo == null) userRepo = new UserRepoImpl();
        return userRepo;
    }

    private static User resultSetToUser(ResultSet res) {
        try {
            //System.out.println(new File(UserRepoImpl.class.getResource("/Images/default_profile_pic.jpg").toURI()).toPath());
            User user = new User.Builder(res.getString("phone_number"), res.getString("user_name"), res.getString("user_password")).bio(res.getString("bio")).birthDate(res.getDate("birth_date")).country(res.getString("country")).email(res.getString("email")).gnder((res.getString("gender") == null) ? null : Gender.valueOf(res.getString("gender"))).id(res.getInt("user_id")).mode((res.getString("user_mode") == null) ? null : UserMode.valueOf(res.getString("user_mode"))).img(Files.readAllBytes((res.getString("img") == null) ? new File(UserRepoImpl.class.getResource("/images/default_profile_pic.jpg").toURI()).toPath() : new File(res.getString("img")).toPath())).status((res.getString("user_status") == null) ? null : UserStatus.valueOf(res.getString("user_status"))).build();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createUser(User user) {
        if (UserRepoImpl.getUserRepo().getUser(user.getPhoneNumber()) != null) {
            System.out.println("User with the same number exist");
            return -1;
        }
        String query = "INSERT INTO chat_user(phone_number,user_name,email,user_password,gender,country,birth_date,bio," + "user_status,user_mode,img) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmnt.setString(1, user.getPhoneNumber());
            stmnt.setString(2, user.getUserName());
            stmnt.setString(3, user.getEmail());
            stmnt.setString(4, user.getPassword());
            stmnt.setString(5, (user.getGender() != null) ? (user.getGender().toString()) : null);
            stmnt.setString(6, user.getCountry());
            stmnt.setDate(7, (user.getBirthDate() != null) ? (new java.sql.Date(user.getBirthDate().getTime())) : null);
            stmnt.setString(8, user.getBio());
            stmnt.setString(9, (user.getStatus() != null) ? (user.getStatus().toString()) : null);
            stmnt.setString(10, (user.getMode() != null) ? (user.getMode().toString()) : null);
            stmnt.setString(11, saveImg(user.getImg(), user.getPhoneNumber()));
            if (stmnt.executeUpdate() == 0) {
                System.out.println("User was not inserted");
                return -1;
            } else {
                ResultSet res = stmnt.getGeneratedKeys();
                if (res.next()) {
                    return res.getInt(1);
                } else {
                    System.out.println("User was not inserted no ID returned");
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean deleteUser(int userID) {
        String query = "DELETE FROM chat_user WHERE user_id = ?";
        try (PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)) {
            stmnt.setInt(1, userID);
            if (stmnt.executeUpdate() == 0) {
                System.out.println("User was not deleted");
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        String query = "UPDATE chat_user SET user_name=?, email=?, user_password=?, gender=?, " + "country=?, birth_date=?, bio=?, user_status=?, user_mode=? WHERE user_id=?";
        try (PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)) {
            stmnt.setString(1, user.getUserName());
            stmnt.setString(2, user.getEmail());
            stmnt.setString(3, user.getPassword());
            stmnt.setString(4, (user.getGender() != null) ? (user.getGender().toString()) : null);
            stmnt.setString(5, user.getCountry());
            stmnt.setDate(6, (user.getBirthDate() != null) ? (new java.sql.Date(user.getBirthDate().getTime())) : null);
            stmnt.setString(7, user.getBio());
            stmnt.setString(8, (user.getStatus() != null) ? (user.getStatus().toString()) : null);
            stmnt.setString(9, (user.getMode() != null) ? (user.getMode().toString()) : null);
            stmnt.setInt(10, user.getId());
            if (stmnt.executeUpdate() == 0) {
                System.out.println("User was not updated");
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUserImg(int userID, String phone, byte[] img) {
        String query = "UPDATE chat_user SET img=? WHERE user_id=?";
        try (PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)) {
            stmnt.setString(1, saveImg(img, phone));
            stmnt.setInt(2, userID);
            if (stmnt.executeUpdate() == 0) {
                System.out.println("User was not updated");
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUser(int userID) {
        String query = "SELECT * FROM chat_user WHERE user_id = ?";
        try (PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)) {
            stmnt.setInt(1, userID);
            ResultSet res = stmnt.executeQuery();
            if (res.next()) {
                return resultSetToUser(res);
            } else {
                System.out.println("User not found");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getUser(String userPhone) {
        String query = "SELECT * FROM chat_user WHERE phone_number = ?";
        try (PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)) {
            stmnt.setString(1, userPhone);
            ResultSet res = stmnt.executeQuery();
            if (res.next()) {
                return resultSetToUser(res);
            } else {
                System.out.println("User not found");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT * FROM chat_user";
        try (PreparedStatement stmnt = DBConnection.getConnection().prepareStatement(query)) {
            ResultSet res = stmnt.executeQuery();
            List<User> users = new ArrayList<User>();
            while (res.next()) {
                users.add(resultSetToUser(res));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getNumbersAllUsersOnSystem() {
        String sql = "SELECT COUNT(user_id) FROM chat_user";
        int count = 0;
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getNumberAllMaleUsers() {
        String sql = "SELECT COUNT(user_id) FROM chat_user WHERE gender = 'MALE'";
        int count = 0;
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getNumberAllOnlineUsers() {
        String sql = "SELECT COUNT(user_id) FROM chat_user WHERE user_status = 'ONLINE'";
        int count = 0;
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getNumberAllCountryOfUsers(String country) {
        String sql = "SELECT COUNT(user_id) FROM chat_user WHERE country = ?";
        int count = 0;
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, country);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public String saveImg(byte[] img, String phoneNumber) {
        FileOutputStream fos = null;
        String imgID = UUID.randomUUID().toString();
        String path = "./files/imgs/" + phoneNumber;
        try {
            File theDir = new File(path);
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            fos = new FileOutputStream(path + "/" + imgID + ".jpg");
            fos.write(img);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path + "/" + imgID + ".jpg";
    }
}