package com.example.scrummaster;

public class Constants {
    // JSON Node names
    public static final String TAG_PROJECTID = "project_id";
    public static final String TAG_TASKID = "task_id";
    public static final String TAG_ID = "id";
    public static final String TAG_USERID = "user_id";
    public static final String TAG_OWNERID = "owner_id";
    public static final String TAG_USERNAME = "username";
    public static final String TAG_PASSWORD = "password";
    public static final String TAG_USER = "user";
    public static final String TAG_PROJECTNAME = "project_name";
    public static final String TAG_LASTUPDATE = "last_update";
    public static final String TAG_PROGRESSPERCENT = "progress_percent";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_CREATIONDATE = "creation_date";
    public static final String TAG_TITLE = "title";
    public static final String TAG_STATUS = "status";
    public static final String TAG_PREDICTEDTIME = "predicted_time";
    public static final String TAG_CONSUMEDTIME = "consumed_time";
    public static final String TAG_PROJECT = "project";
    public static final String TAG_PROJECTS = "projects";
    public static final String TAG_TASKS = "tasks";
    public static final String TAG_TASK = "task";
    public static final String TAG_USERS = "users";
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_FIRSTNAME = "first_name";
    public static final String TAG_LASTNAME = "last_name";

    //URLs
    public static final  String SERVERADDR = "http://192.168.43.17/";
    public static final String assign_task = SERVERADDR + "assign_task.php";
    public static final String assign_to_project = SERVERADDR + "assign_to_project.php";
    public static final String delete_project = SERVERADDR + "delete_project.php";
    public static final String delete_task = SERVERADDR + "delete_task.php";
    public static final String edit_project = SERVERADDR + "edit_project.php";
    public static final String edit_task = SERVERADDR + "edit_task.php";
    public static final String get_project_detail = SERVERADDR + "get_project_detail.php";
    public static final String get_project_tasks = SERVERADDR + "get_project_tasks.php";
    public static final String get_project_users = SERVERADDR + "get_project_users.php";
    public static final String get_task_details = SERVERADDR + "get_task_details.php";
    public static final String get_task_users = SERVERADDR + "get_task_users.php";
    public static final String get_user_projects = SERVERADDR + "get_user_projects.php";
    public static final String get_user_tasks = SERVERADDR + "get_user_tasks.php";
    public static final String new_project = SERVERADDR + "new_project.php";
    public static final String new_task = SERVERADDR + "new_task.php";
    public static final String unassign_from_project = SERVERADDR + "unassign_from_project.php";
    public static final String unassign_task = SERVERADDR + "unassign_task.php";
    public static final String login = SERVERADDR + "login.php";
}
