package com.example.scrummaster;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;

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

public class ProjectManagemenetActivity extends AppCompatActivity implements UIRefresher {

    static View rootView = null;
    static String projectid="0";
    static  Context context;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static UIRefresher uiRefresher;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_managemenet);
        uiRefresher = this;
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        Context context = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TaskEditAlertDialog(ProjectManagemenetActivity.this , true , uiRefresher).show();
            }
        });

        projectid = getIntent().getExtras().getString(Constants.TAG_PROJECTID,"0");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project_managemenet, menu);
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

    @Override
    public void refresh() {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public class LoadTasks extends AsyncTask<Void , Void , Void> {

            ProgressDialog pDialog;
            JSONParser jParser = new JSONParser();
            JSONArray tasks = null;
            List<Task> taskList = new ArrayList<Task>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            /*pDialog = new ProgressDialog(ProjectManagemenetActivity.this);
            pDialog.setMessage("در حال بارگذاری لطفا صبر کنید...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //pDialog.dismiss();
                RecyclerView recyclerView;
                recyclerView = rootView.findViewById(R.id.fragment_recycler);
                FragmentRecyclerAdapter adapter = new FragmentRecyclerAdapter(getContext(), new ArrayList<Project>(), taskList, new ArrayList<User>(), V.MainActivityRecyclerAdapter.TASK , uiRefresher);
                recyclerView.setAdapter(adapter);

            }

            @Override
            protected Void doInBackground(Void... strings) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(Constants.TAG_PROJECTID,String.valueOf(projectid)));

                JSONObject json = jParser.makeHttpRequest(Constants.get_project_tasks, "GET", params);

                Log.d("Project_id",projectid);
                Log.d("Project Tasks: ", json.toString());

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

        public class LoadUsers extends AsyncTask<Void , Void , Void> {

            ProgressDialog pDialog;
            JSONParser jParser = new JSONParser();
            JSONArray users = null;
            List<User> userList = new ArrayList<>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            /*pDialog = new ProgressDialog(ProjectManagemenetActivity.this);
            pDialog.setMessage("در حال بارگذاری لطفا صبر کنید...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //pDialog.dismiss();
                RecyclerView recyclerView;
                recyclerView = rootView.findViewById(R.id.fragment_recycler);
                FragmentRecyclerAdapter adapter = new FragmentRecyclerAdapter(getContext(), new ArrayList<Project>(), new ArrayList<Task>(), userList, V.MainActivityRecyclerAdapter.USER , uiRefresher);
                recyclerView.setAdapter(adapter);

            }

            @Override
            protected Void doInBackground(Void... strings) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(Constants.TAG_PROJECTID,String.valueOf(projectid)));

                JSONObject json = jParser.makeHttpRequest(Constants.get_project_users, "GET", params);
                Log.d("Project_id",projectid);
                Log.d("Project Users: ", json.toString());

                try {
                    int success = json.getInt(Constants.TAG_SUCCESS);

                    if (success == 1) {

                        users = json.getJSONArray(Constants.TAG_TASKS);
                        for (int i = 0; i < users.length(); i++) {
                            JSONObject c = users.getJSONObject(i);

                            String id = c.getString(Constants.TAG_ID);
                            String first_name = c.getString(Constants.TAG_FIRSTNAME);
                            String last_name = c.getString(Constants.TAG_LASTNAME);
                            userList.add(new User(id,first_name,last_name));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                rootView = inflater.inflate(R.layout.fragment_project_managemenet, container, false);
                //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                //TODO : get project Tasks
                new LoadTasks().execute();
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                rootView = inflater.inflate(R.layout.fragment_project_managemenet, container, false);
                //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                new LoadUsers().execute();
            } else {
                rootView = inflater.inflate(R.layout.fragment_edit_project, container, false);
                Project project = InteriorProject.getInstance().getProject();
                ((EditText)rootView.findViewById(R.id.project_name_fragment)).setText(project.getName());
                ((EditText)rootView.findViewById(R.id.projectDescriptionFragment)).setText(project.getDescription());
                ((EditText)rootView.findViewById(R.id.project_percent_fragment)).setText(String.valueOf(project.getProgress_percent()));

                rootView.findViewById(R.id.submit_project).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: SEND TO PHP
                    }
                });


            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
