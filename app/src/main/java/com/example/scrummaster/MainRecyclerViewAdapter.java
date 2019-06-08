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
import java.util.List;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter {

    Context context;
    List<Project> projectList = new ArrayList<Project>();
    List<Task> taskList = new ArrayList<Task>();
    List<User> userList = new ArrayList<User>();
    int type;

    public MainRecyclerViewAdapter(Context context, List<Project> projectList, List<Task> taskList, List<User> userList, int type) {
        this.context = context;
        this.projectList = projectList;
        this.taskList = taskList;
        this.userList = userList;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = null;
        switch (getItemViewType(i)) {
            case V.MainActivityRecyclerAdapter.PROJECT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_image_list_item, parent, false);
                return new MasterDetailImageViewHolder(view);
        }
        return null;
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
                break;
            case V.MainActivityRecyclerAdapter.USER:
                break;
        }
    }

    private void setMasterDetailImageViewHolder(int i, MasterDetailImageViewHolder masterDetailImageViewHolder) {
        if (type == V.MainActivityRecyclerAdapter.PROJECT) {
            masterDetailImageViewHolder.imageView.setImageResource(R.drawable.scrum_master);
            masterDetailImageViewHolder.masterTextView.setText(projectList.get(i).getName());
            masterDetailImageViewHolder.detailTextView.setText("Owner : " + projectList.get(i).getOwner_username());
            masterDetailImageViewHolder.itemView.setOnClickListener(onClick(i));
        }
    }

    private View.OnClickListener onClick(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == V.MainActivityRecyclerAdapter.PROJECT) {
                    InteriorProject.getInstance().setProject(projectList.get(i));
                    context.startActivity(new Intent(context.getApplicationContext() , ProjectManagemenetActivity.class));
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
