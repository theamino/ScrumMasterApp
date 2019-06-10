package com.example.scrummaster;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.scrummaster.Classes.Project;
import com.example.scrummaster.Classes.Task;
import com.example.scrummaster.Classes.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , UIRefresher {
    Context context = this;
    RecyclerView mainRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Project> projectList = new ArrayList<Project>();
    UIRefresher uiRefresher;
    FragmentRecyclerAdapter adapter;
    public static String ownerid = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainRecyclerView = findViewById(R.id.main_recycler_view);
        uiRefresher = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewProjectDialog(context, uiRefresher).show();
                refresh();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ownerid = getIntent().getExtras().getString(V.Extras.userID,"0");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_projects) {
            //TODO : get data from php
            new LoadProjects().execute();
        } else if (id == R.id.nav_tasks) {
            new LoadTasks().execute();
        } else if (id == R.id.nav_user_profile) {

        } else if (id == R.id.nav_manage) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void refresh() {
        new LoadProjects().execute();

    }

    public class LoadProjects extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;
        JSONParser jParser = new JSONParser();
        JSONArray projects = null;
        List<Project> projectList = new ArrayList<Project>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("در حال بارگذاری لطفا صبر کنید...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            projectList.clear();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            adapter = new FragmentRecyclerAdapter(context, projectList, new ArrayList<Task>(), new ArrayList<User>(), V.MainActivityRecyclerAdapter.PROJECT, uiRefresher);
            layoutManager = new LinearLayoutManager(context);
            mainRecyclerView.setLayoutManager(layoutManager);
            mainRecyclerView.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Constants.TAG_OWNERID,String.valueOf(ownerid)));

            JSONObject json = jParser.makeHttpRequest(Constants.get_user_projects, "GET", params);

            Log.d("User Projects: ", json.toString());

            try {
                int success = json.getInt(Constants.TAG_SUCCESS);

                if (success == 1) {

                    projects = json.getJSONArray(Constants.TAG_PROJECTS);
                    for (int i = 0; i < projects.length(); i++) {
                        JSONObject c = projects.getJSONObject(i);

                        String projectid = c.getString(Constants.TAG_PROJECTID);
                        String name = c.getString(Constants.TAG_PROJECTNAME);
                        String lastupdate = c.getString(Constants.TAG_LASTUPDATE);
                        String progress = c.getString(Constants.TAG_PROGRESSPERCENT);
                        String desc = c.getString(Constants.TAG_DESCRIPTION);
                        String createdat = c.getString(Constants.TAG_CREATIONDATE);
                        String ownerid = c.getString(Constants.TAG_OWNERID);
                        Date crd = new Date();
                        Date lud = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            crd = format.parse(createdat);
                            lud = format.parse(lastupdate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        projectList.add(new Project(projectid,name,ownerid,crd,Integer.valueOf(progress),desc,lud));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class LoadTasks extends AsyncTask<Void , Void , Void> {

        ProgressDialog pDialog;
        JSONParser jParser = new JSONParser();
        JSONArray tasks = null;
        List<Task> taskList= new ArrayList<Task>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("در حال بارگذاری لطفا صبر کنید...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            taskList.clear();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            adapter = new FragmentRecyclerAdapter(context , new ArrayList<Project>() , taskList , new ArrayList<User>() , V.MainActivityRecyclerAdapter.TASK , uiRefresher);
            layoutManager = new LinearLayoutManager(context);
            mainRecyclerView.setLayoutManager(layoutManager);
            mainRecyclerView.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Constants.TAG_USERID,String.valueOf(ownerid)));

            JSONObject json = jParser.makeHttpRequest(Constants.get_user_tasks, "GET", params);

            Log.d("User Tasks: ", json.toString());

            try {
                int success = json.getInt(Constants.TAG_SUCCESS);

                if (success == 1) {

                    tasks = json.getJSONArray(Constants.TAG_TASKS);
                    for (int i = 0; i < tasks.length(); i++) {
                        JSONObject c = tasks.getJSONObject(i);

                        String taskid = c.getString(Constants.TAG_TASKID);
                        String title = c.getString(Constants.TAG_TITLE);
                        String status = c.getString(Constants.TAG_STATUS);
                        String desc = c.getString(Constants.TAG_DESCRIPTION);
                        String predictedtimestr = c.getString(Constants.TAG_PREDICTEDTIME);
                        String consumedtimestr = c.getString(Constants.TAG_CONSUMEDTIME);
                        String projectid = c.getString(Constants.TAG_PROJECTID);
                        Date predictedtime = new Date();
                        Date consumedtime = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            predictedtime = format.parse(predictedtimestr);
                            consumedtime = format.parse(consumedtimestr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        taskList.add(new Task(taskid,title,desc,predictedtime,consumedtime,status));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    //taskList.add(new Task("aminoz"  , new Date(2009 , 10 ,13) , new Date(2009 , 10 ,13) , "hellow"));
}
