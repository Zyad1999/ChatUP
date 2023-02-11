package com.chatup.models.entities;

import com.chatup.models.enums.Gender;
import com.chatup.models.enums.UserMode;
import com.chatup.models.enums.UserStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -558553967080513791L;

    private int id;
    private byte[] img;
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private Gender gender;
    private String country;
    private Date birthDate;
    private String bio;
    private UserStatus status;
    private UserMode mode;

    private User() {
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserMode getMode() {
        return mode;
    }

    public void setMode(UserMode mode) {
        this.mode = mode;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    @Override
    public String toString() {

        return "user: " +
                phoneNumber + ", " +
                userName + ", " +
                email + ", " +
                password + ", " +
                gender + ", " +
                country + ", " +
                birthDate;
    }

    public static class Builder {
        private User user = new User();

        public Builder(String phoneNumber, String userName, String password) {
            user.phoneNumber = phoneNumber;
            user.userName = userName;
            user.password = password;
        }

        public Builder id(int id) {
            user.id = id;
            return this;
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public Builder gnder(Gender gender) {
            user.gender = gender;
            return this;
        }

        public Builder country(String country) {
            user.country = country;
            return this;
        }

        public Builder birthDate(Date birthDate) {
            user.birthDate = birthDate;
            return this;
        }

        public Builder bio(String bio) {
            user.bio = bio;
            return this;
        }

        public Builder status(UserStatus status) {
            user.status = status;
            return this;
        }

        public Builder mode(UserMode mode) {
            user.mode = mode;
            return this;
        }

        public Builder img(byte[] img) {
            user.img = img;
            return this;
        }

        public User build() {
            return user;
        }
    }
}
