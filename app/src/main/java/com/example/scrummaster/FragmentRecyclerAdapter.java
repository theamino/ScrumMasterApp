package com.example.scrummaster;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scrummaster.Classes.Project;
import com.example.scrummaster.Classes.Task;
import com.example.scrummaster.Classes.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        } else if (type == V.MainActivityRecyclerAdapter.TASK) {
            masterDetailImageViewHolder.imageView.setImageResource(R.drawable.scrum_master);
            masterDetailImageViewHolder.masterTextView.setText(taskList.get(i).getDescription());
            masterDetailImageViewHolder.detailTextView.setText("Status : " + taskList.get(i).getStatus());
            masterDetailImageViewHolder.itemView.setOnClickListener(onClick(i));
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
                    context.startActivity(new Intent(context.getApplicationContext() , ProjectManagemenetActivity.class));
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
