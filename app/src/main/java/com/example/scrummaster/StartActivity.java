package com.example.scrummaster;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.scrummaster.Classes.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText)findViewById(R.id.username_start)).getText().toString()
                        , password = ((EditText)findViewById(R.id.password_start)).getText().toString();
                boolean b = false;
                if(username.trim().length() < 4) {
                    ((EditText)findViewById(R.id.username_start)).setError("Username Too Short");
                    b = true;
                }
                if (password.trim().length() < 4) {
                    ((EditText)findViewById(R.id.password_start)).setError("Password Too Short");
                    b = true;
                }
                if (b) return;
                new LoadUser().execute();
                //TODO : LOGIN OR SHOW ERROR
            }
        });
    }

    public class LoadUser extends AsyncTask<Void , Void , Void> {

        ProgressDialog pDialog;
        JSONParser jParser = new JSONParser();
        JSONArray tasks = null;
        private ArrayList<User> itemList;
        List<User> taskList= new ArrayList<User>();
        String userId , password;
        boolean b = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StartActivity.this);
            pDialog.setMessage("در حال بارگذاری لطفا صبر کنید...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            userId = ((EditText)findViewById(R.id.username_start)).getText().toString();
            password = ((EditText)findViewById(R.id.password_start)).getText().toString();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (b) {
                        Intent intent = new Intent(StartActivity.this , MainActivity.class);
                        intent.putExtra(V.Extras.userID , userId);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                        builder.setTitle("خطا");
                        builder.setMessage("نام کاربری یا رمز عبور اشتباه است");
                        builder.show();
                    }
                }
            });
        }

        @Override
        protected Void doInBackground(Void... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Constants.TAG_USERNAME,userId));
            params.add(new BasicNameValuePair(Constants.TAG_PASSWORD,password));

            JSONObject json = jParser.makeHttpRequest(Constants.login , "GET", params);

            Log.d("Login Result: ", json.toString());

            try {
                int success = json.getInt(Constants.TAG_SUCCESS);

                if (success == 1) {
                    tasks = json.getJSONArray(Constants.TAG_USER);

                    for (int i = 0; i < tasks.length(); i++) {
                        JSONObject c = tasks.getJSONObject(i);
                        userId = c.getString(Constants.TAG_ID);
                    }
                    b=true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
