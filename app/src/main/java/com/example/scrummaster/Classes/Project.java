package com.example.scrummaster.Classes;

import java.util.Date;
import java.util.List;

public class Project {
    private String projectID;
    private String name;
    private String owner_username;
    private Date created_date;
    List<Task> tasks;
    //List of sprints
    //roles users
    int progress_percent;
    String description;
    Date last_update;

    public String getId() {
        return projectID;
    }

    public Project(String projectID, String name, String owner_username, Date created_date, int progress_percent, String description, Date last_update) {
        this.projectID = projectID;
        this.name = name;
        this.owner_username = owner_username;
        this.created_date = created_date;
        this.progress_percent = progress_percent;
        this.description = description;
        this.last_update = last_update;
    }

    public Project(String name, String owner_username, Date created_date, List<Task> tasks, int progress_percent, String description, Date last_update) {
        this.name = name;
        this.owner_username = owner_username;
        this.created_date = created_date;
        this.tasks = tasks;
        this.progress_percent = progress_percent;
        this.description = description;
        this.last_update = last_update;
    }

    public Project(String name, String owner_username, Date created_date, int progress_percent, String description, Date last_update) {
        this.name = name;
        this.owner_username = owner_username;
        this.created_date = created_date;
        this.tasks = tasks;
        this.progress_percent = progress_percent;
        this.description = description;
        this.last_update = last_update;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setProgress_percent(int progress_percent) {
        this.progress_percent = progress_percent;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getOwner_username() {
        return owner_username;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public int getProgress_percent() {
        return progress_percent;
    }

    public String getDescription() {
        return description;
    }

    public Date getLast_update() {
        return last_update;
    }
}
