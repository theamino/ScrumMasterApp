package com.example.scrummaster;

import com.example.scrummaster.Classes.Task;

public class InteriorTask {

    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    private static final InteriorTask ourInstance = new InteriorTask();

    public static InteriorTask getInstance() {
        return ourInstance;
    }

    private InteriorTask() {
    }
}
