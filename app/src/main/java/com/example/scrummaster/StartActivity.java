package com.example.scrummaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
                //TODO : LOGIN OR SHOW ERROR
            }
        });
    }
}
