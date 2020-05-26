package com.cybersectnt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.data.TaskListData;
import com.cybersectnt.globalVars;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    TextInputLayout UsernameEditText, EmailEditText, PasswordEditText;
    RecyclerView BadgesRecyclerView;
    TextView ScoreTextView, HackedMachinesTextView, BankAccessTextView, DeletedSpywaresTextView, ScannedUsersTextView, SocialEngineeringTextView, PhishingUsedTextView;
    private ArrayList<TaskListData> BadgesArrayList;
    private CustomBadgesAdapter adapter;
    boolean EditState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinkDesignWithCode();
        initVars();
        loadDB();
    }
    /*
    this method is to initialize the values needed in this class and and linking the adapter, and showing the username and email of the user
     */

    private void initVars() {
        BadgesArrayList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        BadgesRecyclerView.setLayoutManager(layoutManager);
        adapter = new CustomBadgesAdapter(getBaseContext(), BadgesArrayList);
        BadgesRecyclerView.setAdapter(adapter);
        BadgesRecyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(),
                DividerItemDecoration.HORIZONTAL));
        UsernameEditText.getEditText().setText(globalVars.getUserName());
        EmailEditText.getEditText().setText(globalVars.getmAuth().getCurrentUser().getEmail());
    }

    /**
     * This method will check if a string is null or not
     *
     * @return boolean
     * @input user id
     */
    private String getNonNull(String str) {
        try {
            return Integer.parseInt(str) + "";
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * This method is responsible for fetching the data from the database. which is the query to calculate the score
     */

    private void loadDB() {
        int score = globalVars.calculateScore(globalVars.getAchievementsData(), globalVars.getUserPoints()); // total points + hackedMachines + bankAccess + deletedSpywares + ...etc
        String hackedMachines = globalVars.getAchievementsData().get("Exploited_Users") + "";
        String bankAccess = globalVars.getAchievementsData().get("Bank_Access_Users") + "";
        String deletedSpywares = globalVars.getAchievementsData().get("Spywares_deleted") + "";
        String scannedUsers = globalVars.getAchievementsData().get("Scanned_Users") + "";
        String socialEngineering = globalVars.getAchievementsData().get("Social_Engineering_Used") + "";
        String phishingUsed = globalVars.getAchievementsData().get("Phishing_Used") + "";
        HackedMachinesTextView.setText(getNonNull(hackedMachines));
        BankAccessTextView.setText(getNonNull(bankAccess));
        DeletedSpywaresTextView.setText(getNonNull(deletedSpywares));
        ScannedUsersTextView.setText(getNonNull(scannedUsers));
        SocialEngineeringTextView.setText(getNonNull(socialEngineering));
        PhishingUsedTextView.setText(getNonNull(phishingUsed));
        for (TaskListData data : globalVars.getAllAvailableTasks()) {
            String query = data.getQuery();
            String str;
            if (query.contains("UserTools_Upgrade")) {
                query = query.substring(0, query.indexOf("_"));
                str = globalVars.getMyToolsLevel().get(query) + "";
            } else {
                str = globalVars.getAchievementsData().get(query) + "";
            }
            if (!str.equals("null")) {
                data.setQueryResult(Integer.parseInt(str));
            }
            if (data.getPercentage() >= 100 && data.getClaimType().equals("Points")) {
                score += data.getClaimValue();
            }
            if (!data.getBadge().equals("null")) {
                BadgesArrayList.add(data);
            }
        }
        ScoreTextView.setText(score + "");

        Collections.sort(BadgesArrayList);
        Collections.reverse(BadgesArrayList);
        adapter.notifyDataSetChanged();
    }

    /*
    this method is responsible for linking the code with the design
     */

    private void LinkDesignWithCode() {
        UsernameEditText = findViewById(R.id.EditTextForUsername);
        EmailEditText = findViewById(R.id.EditTextForEmail);
        PasswordEditText = findViewById(R.id.EditTextForPassword);
        BadgesRecyclerView = findViewById(R.id.RecyclerViewToViewBadges);
        ScoreTextView = findViewById(R.id.TextViewToShowScore);
        HackedMachinesTextView = findViewById(R.id.TextViewToShowHackedMachines);
        BankAccessTextView = findViewById(R.id.TextViewToShowBankAccess);
        DeletedSpywaresTextView = findViewById(R.id.TextViewToShowDeletedSpywares);
        ScannedUsersTextView = findViewById(R.id.TextViewToShowScannedUsers);
        SocialEngineeringTextView = findViewById(R.id.TextViewToShowSocialEngineering);
        PhishingUsedTextView = findViewById(R.id.TextViewToShowPhishingUsed);
    }

    public boolean CheckUsernames() {
        final boolean[] found = {false};
        globalVars.getUsersCollection().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    String username = documentSnapshot.get("UserName") + "";
                    if (username.equals(UsernameEditText)) {
                        found[0] = true;
                    }
                }
            }
        });
        return found[0];
    }

    /**
     * This method allows the user to change their password, email and their user name
     *
     * @return update the info in the database
     * @input user's changed info
     */

    public void ChangeInformationOrSave() {
        if (EditState) {
            PasswordEditText.getEditText().setText("");
        } else {
            //Save State
            if (globalVars.isEmptyTextInputLayout(UsernameEditText) || globalVars.isEmptyTextInputLayout(PasswordEditText) || globalVars.isEmptyTextInputLayout(EmailEditText)) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            AuthCredential credential = EmailAuthProvider
                    .getCredential(globalVars.getmAuth().getCurrentUser().getEmail(), globalVars.getTextInputLayoutString(PasswordEditText));
            globalVars.getmAuth().getCurrentUser().reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //update
                                if (!globalVars.getTextInputLayoutString(EmailEditText).equals(globalVars.getmAuth().getCurrentUser().getEmail())) {
                                    FirebaseAuth.getInstance().getCurrentUser().updateEmail(globalVars.getTextInputLayoutString(EmailEditText)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            AuthCredential credential = EmailAuthProvider
                                                    .getCredential(globalVars.getTextInputLayoutString(EmailEditText), globalVars.getTextInputLayoutString(PasswordEditText));
                                            globalVars.getmAuth().getCurrentUser().reauthenticate(credential);
                                            globalVars.moveToMainActivityFresh(getBaseContext());
                                        }
                                    });
                                }
                                if (!globalVars.getTextInputLayoutString(UsernameEditText).equals(globalVars.getUserName()) && (!CheckUsernames())) {
                                    HashMap<String, String> hm = new HashMap<>();
                                    hm.put("UserName", globalVars.getTextInputLayoutString(UsernameEditText));
                                    globalVars.getUserDocument(globalVars.getUserID()).set(hm, SetOptions.merge());
                                    globalVars.moveToMainActivityFresh(getBaseContext());
                                }
                            } else {
                                Toast.makeText(ProfileActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                                PasswordEditText.getEditText().setText("password");
                            }
                            PasswordEditText.getEditText().setText("password");
                        }
                    });
        }

        UsernameEditText.setEnabled(EditState);
        EmailEditText.setEnabled(EditState);
        PasswordEditText.setEnabled(EditState);
        EditState = !EditState;
    }


    /**
     * This method is responsible for linking the adapter which will show the badges
     */

    public class CustomBadgesAdapter extends RecyclerView.Adapter<CustomBadgesAdapter.ViewHolder> {
        Context context;
        ArrayList<TaskListData> arr;

        public CustomBadgesAdapter(Context context, ArrayList<TaskListData> arr) {
            this.context = context;
            this.arr = arr;
        }

        @NonNull
        @Override
        public CustomBadgesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row_badge, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            Picasso.get().load(arr.get(position).getBadge()).into(holder.Icon);
            if (arr.get(position).getPercentage() < 100) {
                ColorMatrix colorMatrix = new ColorMatrix();
                colorMatrix.setSaturation(0);
                ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
                holder.Icon.setColorFilter(colorMatrixColorFilter);
                holder.Icon.setImageAlpha(128);
            }
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, arr.get(position).getDescription(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        /**
         * This method is part of the adapter to check the proper number of badges
         *
         * @return number of badges
         */

        @Override
        public int getItemCount() {
            return arr.size();
        }

        /**
         * This method is for linking the view holder to the update information
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView Icon;
            public View view;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                Icon = view.findViewById(R.id.ImageViewToShowBadgeIcon);
            }
        }

    }

    /**
     * This method allows the user to edit the info then save them
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.ChangeInformationOrSaveMenu:
                ChangeInformationOrSave();
                if (EditState) {
                    item.setTitle("Save");
                } else {
                    item.setTitle("Edit");
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method Inflate the main_menu, this adds items to the action bar if it is present.
     */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }
}
