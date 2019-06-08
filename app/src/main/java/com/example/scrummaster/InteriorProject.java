package com.example.scrummaster;

import com.example.scrummaster.Classes.Project;

public class InteriorProject {

    private Project project;

    private static final InteriorProject ourInstance = new InteriorProject();

    public static InteriorProject getInstance() {
        return ourInstance;
    }

    private InteriorProject() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
