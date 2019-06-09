package com.example.scrummaster;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context context = this;
    RecyclerView mainRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Project> projectList = new ArrayList<Project>();
    MainRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainRecyclerView = findViewById(R.id.main_recycler_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        } else if (id == R.id.nav_user_profile) {

        } else if (id == R.id.nav_manage) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class LoadProjects extends AsyncTask<Void , Void , Void> {

        List<Project> projectList = new ArrayList<Project>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new MainRecyclerViewAdapter(context , projectList , new ArrayList<Task>() , new ArrayList<User>() , V.MainActivityRecyclerAdapter.PROJECT);
            layoutManager = new LinearLayoutManager(context);
            mainRecyclerView.setLayoutManager(layoutManager);
            mainRecyclerView.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... strings) {
            projectList.add(new Project("aminoz" , "amin" , new Date(2009 , 10 ,13) , new ArrayList<Task>() , 50 , "hellow" , new Date()));
            return null;
        }
    }

    ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    JSONArray units = null;
    private ArrayList<Project> itemList;

    class LoadAllUnits extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("در حال بارگذاری لیست واحد ها لطفا صبر کنید...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jParser.makeHttpRequest(Constants.url_all_units, "GET", params);

            Log.d("All Units: ", json.toString());

            try {
                int success = json.getInt(Constants.TAG_SUCCESS);

                if (success == 1) {

                    units = json.getJSONArray(Constants.TAG_UNITS);
                    for (int i = 0; i < units.length(); i++) {
                        JSONObject c = units.getJSONObject(i);

                        String unitid = c.getString(Constants.TAG_UNITID);
                        String num = c.getString(Constants.TAG_NUM);
                        String voipnum = c.getString(Constants.TAG_VOIPNUM);
                        String owner = c.getString(Constants.TAG_OWNER);
                        //if (!unitid.equals(myunitid))
                        {
                           // itemList.add(new Project(Integer.parseInt(unitid), Integer.parseInt(num), owner, voipnum));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (itemList.size() > 0) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        //adapter = new MyRecyclerAdapter(itemList, InterCallList.this);
                        //list.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
            } else {
//        finish();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("خطایی رخ داد");
                builder.setMessage("واحدی یافت نشد. در صورت تکرار به مدیر ساختمان اطلاع دهید");
                builder.setCancelable(false);
                builder.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.create();
                builder.show();
            }
        }

    }

}
