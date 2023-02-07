package com.chatup.models.entities;

import com.chatup.models.enums.Gender;
import com.chatup.models.enums.UserMode;
import com.chatup.models.enums.UserStatus;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private int id ;
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

    private User(){}

    public static class Builder{
        private User user = new User();

        public Builder(String phoneNumber, String userName, String password){
            user.phoneNumber = phoneNumber;
            user.userName = userName;
            user.password = password;
        }

        public Builder id(int id){
            user.id = id;
            return this;
        }

        public Builder email(String email){
            user.email = email;
            return this;
        }

        public Builder gnder(Gender gender){
            user.gender = gender;
            return this;
        }

        public Builder country(String country){
            user.country = country;
            return this;
        }

        public Builder birthDate(Date birthDate){
            user.birthDate = birthDate;
            return this;
        }

        public Builder bio(String bio){
            user.bio = bio;
            return this;
        }

        public Builder status(UserStatus status){
            user.status = status;
            return this;
        }

        public Builder mode(UserMode mode){
            user.mode = mode;
            return this;
        }

        public User build(){
            return user;
        }
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public Gender getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getBio() {
        return bio;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserMode getMode() {
        return mode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setMode(UserMode mode) {
        this.mode = mode;
    }
}
