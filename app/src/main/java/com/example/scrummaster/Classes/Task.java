package com.example.scrummaster.Classes;

import java.util.Date;
import java.util.List;

public class Task {
    String description,title,taskid;
    Date predicted_time;
    Date consumed_days;
    String status;
    int projectID;

    public Task(String description, String title, String taskid, Date predicted_time, Date consumed_days, String status, int projectID) {
        this.description = description;
        this.title = title;
        this.taskid = taskid;
        this.predicted_time = predicted_time;
        this.consumed_days = consumed_days;
        this.status = status;
        this.projectID = projectID;
    }

    //list tags
    List<User> members;
    //sprint


    public Task(String taskid,String title,String description, Date predicted_time, Date consumed_days, String status) {
        this.taskid = taskid;
        this.title = title;
        this.description = description;
        this.predicted_time = predicted_time;
        this.consumed_days = consumed_days;
        this.status = status;
    }

    public Task(String taskid,String title,String description, Date predicted_time, Date consumed_days, String status, List<User> members) {
        this.taskid = taskid;
        this.title = title;
        this.description = description;
        this.predicted_time = predicted_time;
        this.consumed_days = consumed_days;
        this.status = status;
        this.members = members;
    }

    public String getTitle() {
        return title;
    }

    public String getTaskid() {
        return taskid;
    }

    public int getProjectID() {
        return projectID;
    }

    public String getDescription() {
        return description;
    }

    public Date getPredicted_time() {
        return predicted_time;
    }

    public Date getConsumed_days() {
        return consumed_days;
    }

    public String getStatus() {
        return status;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPredicted_time(Date predicted_time) {
        this.predicted_time = predicted_time;
    }

    public void setConsumed_days(Date consumed_days) {
        this.consumed_days = consumed_days;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
