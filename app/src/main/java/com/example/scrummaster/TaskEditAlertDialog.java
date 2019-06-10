package com.example.scrummaster;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.scrummaster.Classes.Task;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TaskEditAlertDialog extends Dialog implements DatePickerDialog.OnDateSetListener {
    boolean isNew = false;
    UIRefresher uiRefresher;
    Task task;
    public TaskEditAlertDialog(@NonNull Context context , boolean isNew , UIRefresher uiRefresher) {
        super(context);
        this.isNew = isNew;
        this.uiRefresher = uiRefresher;
    }
    Spinner spinner;
    DatePickerDialog datePicker;
    int which = 0;
    int PRED = 1;
    int CONS = 2;
    Context context;
    String[] states = {"active" , "to do" , "in progress" ,  "review" , "done" , "deactivated"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_edit_layout);
        if(!isNew) {
            ((EditText) findViewById(R.id.edit_text_desc_task)).setText(InteriorTask.getInstance().getTask().getDescription());
            ((EditText) findViewById(R.id.taskTitleEdit)).setText(InteriorTask.getInstance().getTask().getTitle());

        }
        final DatePickerDialog.OnDateSetListener dateSetListener = this;
        spinner = findViewById(R.id.spinner_edit_task);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
                states);
        spinner.setAdapter(adapter);
        findViewById(R.id.predicted_time_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which = PRED;
                if (datePicker != null)
                    datePicker = new DatePickerDialog(getContext(), dateSetListener, 2018, 10, 10);
                datePicker.show();
            }
        });

        findViewById(R.id.consumed_time_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which = CONS;
                if (datePicker == null)
                    datePicker = new DatePickerDialog(getContext(), dateSetListener, 2018, 10, 10);
                datePicker.show();
            }
        });

        findViewById(R.id.submit_edit_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = states[spinner.getSelectedItemPosition()];
                String desc = ((EditText) findViewById(R.id.edit_text_desc_task)).getText().toString();
                String title = ((EditText) findViewById(R.id.taskTitleEdit)).getText().toString();
                Date pred = InteriorTask.getInstance().getTask().getPredicted_time(), cons = InteriorTask.getInstance().getTask().getConsumed_days();
                //TODO : SEND DATA TO PHP
                Task temp = InteriorTask.getInstance().getTask();
                task = new Task(desc, title, isNew ? temp.getTaskid() : "", temp.getPredicted_time(), temp.getConsumed_days(), state, temp.getProjectID());

            }
        });

        findViewById(R.id.cancel_edit_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(which == PRED)
            InteriorTask.getInstance().getTask().setPredicted_time(new Date(year , month , dayOfMonth));
        else
            InteriorTask.getInstance().getTask().setConsumed_days(new Date(year , month , dayOfMonth));
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
            params.add(new BasicNameValuePair(Constants.TAG_TASKID, String.valueOf((new Random()).nextInt())));
            params.add(new BasicNameValuePair(Constants.TAG_TITLE, String.valueOf(task.getTitle())));
            params.add(new BasicNameValuePair(Constants.TAG_STATUS, task.getStatus()));
            params.add(new BasicNameValuePair(Constants.TAG_PREDICTEDTIME, String.valueOf(task.getPredicted_time())));
            params.add(new BasicNameValuePair(Constants.TAG_CONSUMEDTIME, String.valueOf(task.getConsumed_days())));
            params.add(new BasicNameValuePair(Constants.TAG_DESCRIPTION, task.getDescription()));
            params.add(new BasicNameValuePair(Constants.TAG_PROJECTID, String.valueOf(task.getProjectID())));

            JSONObject json = jParser.makeHttpRequest(isNew ? Constants.new_task : Constants.edit_task, "GET", params);

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