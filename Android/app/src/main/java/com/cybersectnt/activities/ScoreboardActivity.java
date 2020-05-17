package com.cybersectnt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.globalVars;
import com.cybersectnt.data.ScoreboardListData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ScoreboardActivity extends AppCompatActivity {
    RecyclerView ScoreboardListRecyclerView;
    ArrayList<ScoreboardListData> ScoreboardArrayList;
    CustomScoreboardAdapter adapter;

    /**
     * Setup and initiate the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        getSupportActionBar().setTitle("Scoreboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinkDesignWithCode();
        initVars();
        loadDB();
    }

    /*
    This method is used to retrieve the information
     */

    private void loadDB() {

        globalVars.getUsersCollection().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        HashMap<String, Object> achievementsData = (HashMap<String, Object>) documentSnapshot.get("AchievementsData");
                        ScoreboardListData data = new ScoreboardListData();
                        data.setID(documentSnapshot.getId());
                        data.setName(documentSnapshot.get("UserName") + "");
                        int score;
                        if (achievementsData != null && !achievementsData.isEmpty()) {
                            score = globalVars.calculateScore(achievementsData, Integer.parseInt(documentSnapshot.get("UserPoints") + ""));
                        } else {
                            score = 0;
                        }
                        data.setScore(score);
                        ScoreboardArrayList.add(data);
                    }
                    Collections.sort(ScoreboardArrayList);
                    ScoreboardArrayList.add(0, new ScoreboardListData("Username", "1", -1));
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    /*
    This method is to initialize the variables and the layout which will be used to show the scoreboard
     */

    private void initVars() {
        ScoreboardArrayList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ScoreboardListRecyclerView.setLayoutManager(layoutManager);
        adapter = new CustomScoreboardAdapter(getBaseContext(), ScoreboardArrayList);
        ScoreboardListRecyclerView.setAdapter(adapter);
    }

    /**
     * This method link the xml views with objects to allow us to control it.
     */
    private void LinkDesignWithCode() {
        ScoreboardListRecyclerView = findViewById(R.id.RecyclerViewToViewScoreboard);
    }

    /**
     * This is the Custom adapter class to link the recyclerview from the arraylist
     */
    public class CustomScoreboardAdapter extends RecyclerView.Adapter<CustomScoreboardAdapter.ViewHolder> {
        public Context context;
        public ArrayList<ScoreboardListData> arr;

        public CustomScoreboardAdapter(Context context, ArrayList<ScoreboardListData> arr) {
            this.context = context;
            this.arr = arr;
        }

        @NonNull
        @Override
        public CustomScoreboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row_scoreboard, parent, false);
            return new CustomScoreboardAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            String name = arr.get(position).getName();
            String score = arr.get(position).getScore() + "";
            String rank = position + "";
            if (position == 0) {
                rank = "Rank";
                score = "Score";
            }
            holder.Rank.setText(rank);
            holder.Name.setText(name);
            holder.Score.setText(score);

            if (position % 2 != 0) {
                holder.view.setBackgroundColor(getColor(R.color.colorOffWhite));
            }
        }

        @Override
        public int getItemCount() {
            return arr.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView Rank, Name, Score;
            public View view;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                Rank = view.findViewById(R.id.TextViewToShowRank);
                Name = view.findViewById(R.id.TextViewToShowName);
                Score = view.findViewById(R.id.TextViewToShowScore);
            }
        }
    }

    /*
    This method is to check which item got selected
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
