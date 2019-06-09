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

import java.util.Date;

public class TaskEditAlertDialog extends Dialog implements DatePickerDialog.OnDateSetListener {
    boolean isNew = false;
    UIRefresher uiRefresher;
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
                    Date pred = InteriorTask.getInstance().getTask().getPredicted_time(), cons = InteriorTask.getInstance().getTask().getConsumed_days();
                    //TODO : SEND DATA TO PHP
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
}
