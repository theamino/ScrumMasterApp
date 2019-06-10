package com.example.scrummaster;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import java.util.Random;

public class NewProjectDialog extends Dialog {
    UIRefresher uiRefresher;
    Project project;
    public NewProjectDialog(@NonNull Context context , UIRefresher uiRefresher) {
        super(context);
        this.uiRefresher = uiRefresher;
    }
    Context context;
    String[] states = {"active" , "to do" , "in progress" ,  "review" , "done" , "deactivated"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_project);
        findViewById(R.id.cancel_project).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        findViewById(R.id.submit_project).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : PERFORM ADD TO PROJECTS
                String name = ((EditText)findViewById(R.id.project_name_fragment)).getText().toString();
                int percent = ((EditText)findViewById(R.id.project_percent_fragment)).getText().toString().length() > 0 ? Integer.parseInt(((EditText)findViewById(R.id.project_percent_fragment)).getText().toString()) : 0;
                String desc = ((EditText)findViewById(R.id.projectDescriptionFragment)).getText().toString();
                project = new Project("-1",name , "" , new Date() , percent , desc , new Date());

                new NewProject().execute();
            }
        });

    }


    public class NewProject extends AsyncTask<Void , Void , Void> {

        ProgressDialog pDialog;
        JSONParser jParser = new JSONParser();
        JSONArray tasks = null;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("در حال بارگذاری لطفا صبر کنید...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Result");
            builder.setMessage(message);
            builder.show();

        }

        @Override
        protected Void doInBackground(Void... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Constants.TAG_PROJECTNAME, String.valueOf(project.getName())));
            params.add(new BasicNameValuePair(Constants.TAG_OWNERID, String.valueOf(InteriorUser.getInstance().getUser().getId())));
            params.add(new BasicNameValuePair(Constants.TAG_LASTUPDATE, (new Date(System.currentTimeMillis())).toString()));
            params.add(new BasicNameValuePair(Constants.TAG_PROGRESSPERCENT, String.valueOf(project.getProgress_percent())));
            params.add(new BasicNameValuePair(Constants.TAG_DESCRIPTION, project.getDescription()));
            params.add(new BasicNameValuePair(Constants.TAG_CREATIONDATE, (new Date(System.currentTimeMillis())).toString()));

            JSONObject json = jParser.makeHttpRequest(Constants.new_project, "GET", params);

            Log.d("New Project : ", json.toString());

            try {
                int success = json.getInt(Constants.TAG_SUCCESS);

                if (success == 1) {
                    message = json.getString(Constants.TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}