package com.example.scrummaster;

import com.example.scrummaster.Classes.User;

public class InteriorUser {

    private User user;

    private static final InteriorUser ourInstance = new InteriorUser();

    public static InteriorUser getInstance() {
        return ourInstance;
    }

    private InteriorUser() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
