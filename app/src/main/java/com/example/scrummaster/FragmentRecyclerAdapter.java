package com.example.scrummaster;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.scrummaster.ProjectManagemenetActivity.projectid;

public class FragmentRecyclerAdapter extends RecyclerView.Adapter {

    Context context;
    List<Project> projectList = new ArrayList<Project>();
    List<Task> taskList = new ArrayList<>();
    List<User> userList = new ArrayList<User>();
    int type;
    UIRefresher uiRefresher;
    public FragmentRecyclerAdapter(Context context, List<Project> projectList, List<Task> taskList, List<User> userList, int type , UIRefresher uiRefresher) {
        this.context = context;
        this.projectList = projectList;
        this.taskList = taskList;
        this.userList = userList;
        this.type = type;
        this.uiRefresher = uiRefresher;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = null;
        switch (getItemViewType(i)) {
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_image_list_item, parent, false);
                return new MasterDetailImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MasterDetailImageViewHolder masterDetailImageViewHolder;
        switch (getItemViewType(i)) {
            case V.MainActivityRecyclerAdapter.PROJECT:
                masterDetailImageViewHolder = (MasterDetailImageViewHolder) viewHolder;
                setMasterDetailImageViewHolder(i , masterDetailImageViewHolder);
                break;
            case V.MainActivityRecyclerAdapter.TASK:
                masterDetailImageViewHolder = (MasterDetailImageViewHolder) viewHolder;
                setMasterDetailImageViewHolder(i , masterDetailImageViewHolder);
                break;
            case V.MainActivityRecyclerAdapter.USER:
                masterDetailImageViewHolder = (MasterDetailImageViewHolder) viewHolder;
                setMasterDetailImageViewHolder(i , masterDetailImageViewHolder);
                break;
        }
    }

    private void setMasterDetailImageViewHolder(int i, MasterDetailImageViewHolder masterDetailImageViewHolder) {
        if (type == V.MainActivityRecyclerAdapter.PROJECT) {
            masterDetailImageViewHolder.imageView.setImageResource(R.drawable.scrum_master);
            masterDetailImageViewHolder.masterTextView.setText(projectList.get(i).getName());
            masterDetailImageViewHolder.detailTextView.setText("Owner : " + projectList.get(i).getOwner_username());
            masterDetailImageViewHolder.itemView.setOnClickListener(onClick(i));
            masterDetailImageViewHolder.itemView.setOnLongClickListener(onLongClick(i));
        } else if (type == V.MainActivityRecyclerAdapter.TASK) {
            masterDetailImageViewHolder.imageView.setImageResource(R.drawable.scrum_master);
            masterDetailImageViewHolder.masterTextView.setText(taskList.get(i).getDescription());
            masterDetailImageViewHolder.detailTextView.setText("Status : " + taskList.get(i).getStatus());
            masterDetailImageViewHolder.itemView.setOnClickListener(onClick(i));
            masterDetailImageViewHolder.itemView.setOnLongClickListener(onLongClick(i));
        } else if (type == V.MainActivityRecyclerAdapter.USER) {
            masterDetailImageViewHolder.imageView.setImageResource(R.drawable.scrum_master);
            masterDetailImageViewHolder.masterTextView.setText(userList.get(i).getUser_name());
            masterDetailImageViewHolder.detailTextView.setText("Name : " + userList.get(i).getFirst_name() + " " + userList.get(i).getLast_name()
                    + " Age : " +  String.valueOf((new Date(System.currentTimeMillis())).getYear() - userList.get(i).getBirthDate().getYear()));
        }
    }

    private View.OnClickListener onClick(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == V.MainActivityRecyclerAdapter.PROJECT) {
                    InteriorProject.getInstance().setProject(projectList.get(i));
                    Intent x = new Intent(context.getApplicationContext(), ProjectManagemenetActivity.class);
                    x.putExtra(Constants.TAG_PROJECTID,projectList.get(i).getId());
                    context.startActivity(x);
                } else if (type == V.MainActivityRecyclerAdapter.TASK) {
                    InteriorTask.getInstance().setTask(taskList.get(i));
                    new TaskEditAlertDialog(context, false , uiRefresher).show();
                } else if (type == V.MainActivityRecyclerAdapter.USER) {
                    InteriorUser.getInstance().setSelectedUser(userList.get(i));
                    new UserShowDialog(context.getApplicationContext() , uiRefresher).show();
                }
            }
        };
    }

    private View.OnLongClickListener onLongClick(final int i)
    {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(type == V.MainActivityRecyclerAdapter.PROJECT) {
                    new DeleteProject().execute(new String[]{projectList.get(i).getId()});
                }
                return false;
            }
        };
    }

    public class DeleteProject extends AsyncTask<String , String , String > {

        ProgressDialog pDialog;
        JSONParser jParser = new JSONParser();
        int success;

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
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            //pDialog.dismiss();
            if(success==1)
            {
                Toast.makeText(context,"Project Deleted Please refresh",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Constants.TAG_PROJECTID, strings[0]));

            JSONObject json = jParser.makeHttpRequest(Constants.delete_project, "GET", params);

            Log.d("Project_id", projectid);
            Log.d("Project Tasks: ", json.toString());

            try {
                success = json.getInt(Constants.TAG_SUCCESS);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }




    private class MasterDetailImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView masterTextView , detailTextView;

        public MasterDetailImageViewHolder(@NonNull View itemView) {
            super(itemView);
            masterTextView = itemView.findViewById(R.id.master_text_list_item);
            detailTextView = itemView.findViewById(R.id.detail_text_list_item);
            imageView = itemView.findViewById(R.id.image_list_item);
        }
    }

    @Override
    public int getItemCount() {
        switch (type) {
            case V.MainActivityRecyclerAdapter.PROJECT:
                return projectList.size();
            case V.MainActivityRecyclerAdapter.TASK:
                return taskList.size();
            case V.MainActivityRecyclerAdapter.USER:
                return userList.size();
            default:
                return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }
}
