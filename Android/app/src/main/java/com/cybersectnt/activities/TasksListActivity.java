package com.cybersectnt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.globalVars;
import com.cybersectnt.data.TaskListData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class TasksListActivity extends AppCompatActivity {
    ArrayList<TaskListData> arrayList;
    RecyclerView TasksListRecyclerView;
    CustomTasksAdapter adapter;
    ArrayList<TaskListData> TasksArrayList;

    /**
     * Setup and initiate the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
        getSupportActionBar().setTitle("Tasks");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinkDesignWithCode();
        initVars();

    }

    /**
     * This method link the xml views with objects to allow us to control it.
     */
    private void LinkDesignWithCode() {
        TasksListRecyclerView = findViewById(R.id.RecyclerViewToViewTasks);
    }

    /*
    Tis method is responsible for initiating the layout that will be used to show the tasks
     */
    private void initVars() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        TasksListRecyclerView.setLayoutManager(layoutManager);
        TasksArrayList = globalVars.getAllAvailableTasks();
        adapter = new CustomTasksAdapter(getBaseContext(), TasksArrayList);

        TasksListRecyclerView.setAdapter(adapter);
        TasksListRecyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(),
                DividerItemDecoration.VERTICAL));
        Collections.sort(TasksArrayList);
        adapter.notifyDataSetChanged();
    }

    /**
     * This is the Custom adapter class to link the recyclerview from the arraylist
     */
    public class CustomTasksAdapter extends RecyclerView.Adapter<CustomTasksAdapter.ViewHolder> {
        public Context context;
        public ArrayList<TaskListData> arr;

        public CustomTasksAdapter(Context context, ArrayList<TaskListData> arr) {
            this.context = context;
            this.arr = arr;
        }

        /**
         * This method is so we can use the list we created with the information
         * @param parent
         * @param viewType
         * @return
         */
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row_task, parent, false);
            return new ViewHolder(view);
        }

        /**
         *   In this method we are filling the list with the title, picture and the subtitle of each task.
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            holder.Title.setText(arr.get(position).getTitle());
            holder.Subtitle.setText(arr.get(position).getSubTitle());
            Picasso.get().load(arr.get(position).getPicture()).into(holder.Icon);
            holder.Progress.setProgress(arr.get(position).getPercentage());
            if (position % 2 == 0) {
                holder.view.setBackgroundColor(getColor(R.color.colorOffWhite));
            }
            if (arr.get(position).getPercentage() >= 100) {
                holder.Complete.setVisibility(View.VISIBLE);
            } else{
                holder.Complete.setVisibility(View.INVISIBLE);
            }
        }

        /**
         * This is a standards method to retrieve the size of the array (How many elements are going to be in the list)
         * @return the size of the array
         */
        @Override
        public int getItemCount() {
            return arr.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView Title, Subtitle;
            public ImageView Icon, Complete;
            public ProgressBar Progress;

            public View view;

            /*
            This to link the layout with java
             */
            public ViewHolder(View view) {
                super(view);
                this.view = view;
                Title = view.findViewById(R.id.TextViewToShowTaskTitle);
                Subtitle = view.findViewById(R.id.TextViewToTaskSubTitle);
                Icon = view.findViewById(R.id.ImageViewToShowTaskIcon);
                Progress = view.findViewById(R.id.ProgressBarToVisualizeTaskProgressLeft);
                Complete = view.findViewById(R.id.ImageViewToShowCompleted);
            }
        }
    }

    /*
    This method is used to handle the back button if pressed.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
