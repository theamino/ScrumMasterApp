package com.example.scrummaster;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scrummaster.Classes.User;

public class NewProjectDialog extends Dialog {
    UIRefresher uiRefresher;
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
            }
        });

    }
}
