package com.example.scrummaster.Classes;

import android.provider.ContactsContract;

import java.util.Date;
import java.util.List;

public class User {
    String userID;
    String first_name , last_name , user_name,
    password , description;
    Date lastOnlineDate;
    float rate;
    Date birthDate;
    ContactsContract.CommonDataKinds.Email email;
    String phone_number;
    String gender;
    String country;
    List<Project> collaboration_project_list;
    List<Project> management_project_list;

    public String getID() {
        return userID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public String getDescription() {
        return description;
    }

    public Date getLastOnlineDate() {
        return lastOnlineDate;
    }

    public float getRate() {
        return rate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public ContactsContract.CommonDataKinds.Email getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public List<Project> getCollaboration_project_list() {
        return collaboration_project_list;
    }

    public List<Project> getManagement_project_list() {
        return management_project_list;
    }

    public User(String userID, String first_name, String last_name) {
        this.userID = userID;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public User(String userID , String first_name, String last_name, String user_name, String password, String description, Date lastOnlineDate, float rate, Date birthDate, ContactsContract.CommonDataKinds.Email email, String phone_number, String gender, String country, List<Project> collaboration_project_list, List<Project> management_project_list) {
        this.userID = userID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_name = user_name;
        this.password = password;
        this.description = description;
        this.lastOnlineDate = lastOnlineDate;
        this.rate = rate;
        this.birthDate = birthDate;
        this.email = email;
        this.phone_number = phone_number;
        this.gender = gender;
        this.country = country;
        this.collaboration_project_list = collaboration_project_list;
        this.management_project_list = management_project_list;
    }
}
