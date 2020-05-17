package com.cybersectnt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.data.ScenarioListData;
import com.cybersectnt.globalVars;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ScenariosListActivity extends AppCompatActivity {
    RecyclerView ScenaiosListRecyclerView;
    CustomScenariosListAdapter adapter;
    ArrayList<ScenarioListData> ScenariosArrayList;

    /**
     * Setup and initiate the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenarios_list);
        getSupportActionBar().setTitle("Scenarios");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinkDesignWithCode();
        initVars();
        loadDB();

    }

    /*
    This method loads the list of scenarios for the user to choose from
     */
    private void loadDB() {
        globalVars.getScenariosCollection().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ScenarioListData data = new ScenarioListData();
                    data.setID(documentSnapshot.getId());
                    data.setTitle(documentSnapshot.get("Title") + "");
                    ScenariosArrayList.add(data);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * This method link the xml views with objects to allow us to control it.
     */
    private void LinkDesignWithCode() {
        ScenaiosListRecyclerView = findViewById(R.id.RecyclerViewToViewScenarios);
    }

    /*
    This method initializes the layout manager which will be used to show the scenarios
     */

    private void initVars() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ScenaiosListRecyclerView.setLayoutManager(layoutManager);
//        ScenariosArrayList = (ArrayList<TaskListData>) globalVars.getScenariosArrayList().clone(); //TODO load and store then retrieve and use them as completed or not yet
        ScenariosArrayList = new ArrayList<>();
        adapter = new CustomScenariosListAdapter(getBaseContext(), ScenariosArrayList);
        ScenaiosListRecyclerView.setAdapter(adapter);
        ScenaiosListRecyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(),
                DividerItemDecoration.VERTICAL));

    }

    /**
     * This is the Custom adapter class to link the recyclerview from the arraylist
     */
    public class CustomScenariosListAdapter extends RecyclerView.Adapter<CustomScenariosListAdapter.ViewHolder> {
        public Context context;
        public ArrayList<ScenarioListData> arr;

        public CustomScenariosListAdapter(Context context, ArrayList<ScenarioListData> arr) {
            this.context = context;
            this.arr = arr;
        }

        @NonNull
        @Override
        public CustomScenariosListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row_single_text, parent, false);
            return new CustomScenariosListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final CustomScenariosListAdapter.ViewHolder holder, final int position) {
            holder.Title.setText(arr.get(position).getTitle());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), ScenarioActivity.class);
                    intent.putExtra("ScenarioID", arr.get(position).getID());
                    startActivity(intent);
                }
            });
            if (position % 2 == 0) {
                holder.view.setBackgroundColor(getColor(R.color.colorOffWhite));
            }
        }

        @Override
        public int getItemCount() {
            return arr.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView Title;
            public View view;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                Title = view.findViewById(R.id.TextViewToShowSingleText);
            }
        }
    }



    /**
     * This method is to show which of the items got pressed
     * @param item
     * @return
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
