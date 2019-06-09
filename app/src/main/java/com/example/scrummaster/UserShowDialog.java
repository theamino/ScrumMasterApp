package com.example.scrummaster;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.scrummaster.Classes.User;

import java.util.Date;

public class UserShowDialog extends Dialog {
    UIRefresher uiRefresher;
    public UserShowDialog(@NonNull Context context , UIRefresher uiRefresher) {
        super(context);
        this.uiRefresher = uiRefresher;
    }
    Context context;
    String[] states = {"active" , "to do" , "in progress" ,  "review" , "done" , "deactivated"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_show_dialog);
        User user = InteriorUser.getInstance().getSelectedUser();
        ((TextView)findViewById(R.id.name_user_show)).setText("Name : "  + user.getFirst_name() + " " + user.getLast_name());
        ((TextView)findViewById(R.id.username_usershow)).setText("Username : " + user.getUser_name());
        ((TextView)findViewById(R.id.age_usershow)).setText("Birthday : " + user.getBirthDate().toString());
        ((TextView)findViewById(R.id.description_usershow)).setText(user.getDescription());
        ((TextView)findViewById(R.id.email_usershow)).setText("Email : " + user.getEmail());
        ((TextView)findViewById(R.id.gender_country_usershow)).setText("Gender : " + user.getGender() + " " + "Country : " + user.getCountry());
        findViewById(R.id.close_usershow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        findViewById(R.id.delete_usershow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : PERFORM DELETE FROM PROJECT
            }
        });

    }
}
