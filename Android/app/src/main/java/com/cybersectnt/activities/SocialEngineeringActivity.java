package com.cybersectnt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.globalVars;
import com.cybersectnt.data.SocialEngineeringInformationData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SocialEngineeringActivity extends AppCompatActivity {
    CustomAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<SocialEngineeringInformationData> AllInformationArrayList;
    ArrayList<SocialEngineeringInformationData> SocialEngineeringAvailableArrayList;
    EditText PasswordEditText;
    TextView UserIDTextView, HintTextView, CallingButton, SocialMediaButton;
    Button SubmitPasswordButton;
    String PassPhrase;
    ProgressDialog progressDialog;
    int formula = 0;

    /**
     * Setup and initiate the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_engineering);
        getSupportActionBar().setTitle(globalVars.getTargetUserName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        loadDB();
    }

    /**
     * This method will retrieve the phone and social media values from the database
     */
    private void loadDB() {
        globalVars.getUserDocument(globalVars.getTargetID())
                .collection("Information").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                AllInformationArrayList = new ArrayList<>();
                int counterPhone = 0;
                int counterSocialMedia = 0;
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    SocialEngineeringInformationData data = new SocialEngineeringInformationData();
                    data.setInformation(documentSnapshot.get("Information") + "");
                    data.setTitle(documentSnapshot.get("Title") + "");
                    data.setBy(documentSnapshot.get("By") + "");
                    if (data.getBy().equals("Phone") && counterPhone <= formula / 2) {
                        counterPhone++;
                    } else if (counterSocialMedia <= formula / 2) {
                        counterSocialMedia++;
                    } else {
                        continue;
                    }

                    AllInformationArrayList.add(data);
                }
                generatePassowrd();
                globalVars.getUserDocument(globalVars.getTargetID()).collection("InformationAccess").document(globalVars.getUserID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        boolean PhoneAccess = !(documentSnapshot.get("Phone") + "").equals("null");
                        boolean SocialMediaAccess = !(documentSnapshot.get("SocialMedia") + "").equals("null");
                        if (PhoneAccess) {
                            loadInformation("Phone");
                        }
                        if (SocialMediaAccess) {
                            loadInformation("SocialMedia");
                        }
                    }
                });
            }
        });

    }

    /**
     * This method is used to  load load information in the progress dialog
     * @param accessType
     */
    private void btnLoadInformation(final String accessType) {
        progressDialog.setTitle("Getting data");
        progressDialog.show();
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadInformation(accessType);
            }
        };
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 1000);
    }

    /**
     * This method is to retrieve the information from the database
     * @param accessType
     */
    private void loadInformation(final String accessType) {
        if (AllInformationArrayList != null && !AllInformationArrayList.isEmpty()) {
            for (SocialEngineeringInformationData data : AllInformationArrayList) {
                if (data.getBy().equals(accessType)) {
                    SocialEngineeringAvailableArrayList.add(data);
                }
            }
        }
        adapter = new CustomAdapter(getBaseContext(), SocialEngineeringAvailableArrayList);
        recyclerView.setAdapter(adapter);
        TextView textView;
        if (accessType.equals("Phone")) {
            textView = CallingButton;
        } else {
            textView = SocialMediaButton;
        }
        textView.setEnabled(false);
        textView.setText("Done");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put(accessType, true);
        globalVars.getUserDocument(globalVars.getTargetID()).collection("InformationAccess").document(globalVars.getUserID()).set(hm, SetOptions.merge());
        progressDialog.dismiss();
    }

    /**
     * This method is used to initialize the variables and link the layout with the code
     */
    private void init() {
        progressDialog = new ProgressDialog(this);
        SocialEngineeringAvailableArrayList = new ArrayList<>();

        HintTextView = findViewById(R.id.TextViewToShowHint);
        PasswordEditText = findViewById(R.id.EditTextForPassword);
        PasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SubmitPasswordButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    SubmitPasswordButton.setEnabled(false);
                } else {
                    SubmitPasswordButton.setEnabled(true);
                }
            }
        });
        SubmitPasswordButton = findViewById(R.id.ButtonForSubmitPassword);
        UserIDTextView = findViewById(R.id.TextViewToShowName);
        recyclerView = findViewById(R.id.RecyclerViewToViewInformationData);
        CallingButton = findViewById(R.id.ButtonForStartCalling);
        SocialMediaButton = findViewById(R.id.ButtonForStartSocialMedia);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CustomAdapter(getBaseContext(), SocialEngineeringAvailableArrayList);
        recyclerView.setAdapter(adapter);
        CallingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                btnLoadInformation("Phone");
            }
        });

        SocialMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                btnLoadInformation("SocialMedia");
            }
        });
        SubmitPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPasswordButton();
            }
        });

        double mine = globalVars.getMyToolsLevel().get("SocialEngineeringLevel");
        double his = globalVars.getTargetToolsLevel().get("PasswordLevel");
        formula = (int) (his / mine) + 3 % 11;
    }

    /**
     * This method is used to submit the password
     */
    private void submitPasswordButton() {
        if (PassPhrase.toLowerCase().equals(PasswordEditText.getText().toString().toLowerCase())) {
            Toast.makeText(SocialEngineeringActivity.this, "Access Succeeded", Toast.LENGTH_SHORT).show();
            HashMap<String, String> hm = new HashMap<>();
            hm.put("UserID", globalVars.getUserID());
            globalVars.getUserDocument(globalVars.getTargetID()).collection("BankAccountAccess").add(hm);
            globalVars.getUserDocument(globalVars.getUserID()).update("AchievementsData.Social_Engineering_Used", FieldValue.increment(1));
            long points = globalVars.getMyToolsLevel().get("SocialEngineeringLevel") * 50;
            globalVars.getUserDocument(globalVars.getUserID()).update("UserPoints", FieldValue.increment(points));
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", true);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            Toast.makeText(SocialEngineeringActivity.this, "Access Denied", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is used to generate the password for the attacker to guess
     */
    private void generatePassowrd() {
        ArrayList<String> shuffle = new ArrayList<>();
        for (int i = 0; i < AllInformationArrayList.size(); i++) {
            shuffle.add(AllInformationArrayList.get(i).getInformation());

        }
        Collections.shuffle(shuffle);
        String passPhrase = "";

        for (int i = 0; i < formula; i++) {
            String word = shuffle.get(i).replace(" ", "");
            passPhrase += word;
        }
        PassPhrase = passPhrase.toLowerCase();
        Log.d("Password", PassPhrase);
        HintTextView.setText("Hint: " + formula + " Words");
    }

    /**
     * This method is used to clear the edit text that has the password
     * @param view
     */
    public void Clear(View view) {
        PasswordEditText.setText("");
        for (int i = 0; i < SocialEngineeringAvailableArrayList.size(); i++) {
            SocialEngineeringAvailableArrayList.get(i).setSelected(false);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * This is the Custom adapter class to link the recyclerview from the arraylist
     */
    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
        Context context;
        ArrayList<SocialEngineeringInformationData> arr;

        public CustomAdapter(Context context, ArrayList<SocialEngineeringInformationData> arr) {
            this.context = context;
            this.arr = arr;
        }

        /**
         * This method is used to link the adapter to the view
         * @param parent
         * @param viewType
         * @return
         */

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row_social_engineering_information, parent, false);
            return new MyViewHolder(view);
        }

        /**
         * This method is used to attach the info to the list
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
            SocialEngineeringInformationData data = arr.get(position);
            Picasso.get().load(data.getImg()).into(holder.img);
            holder.title.setText(data.getTitle());
            holder.info.setText(data.getInformation());
            if (arr.get(position).isSelected()) {
                holder.view.setBackgroundColor(Color.parseColor("#50000000"));
            } else {
                holder.view.setBackgroundColor(Color.TRANSPARENT);
            }
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String word = arr.get(position).getInformation().replace(" ", "");
                    if (!arr.get(position).isSelected()) {
                        PasswordEditText.setText(PasswordEditText.getText().toString() + word);
                    } else {
                        PasswordEditText.setText(PasswordEditText.getText().toString().replace(word, ""));
                    }
                    arr.get(position).setSelected(!arr.get(position).isSelected());
                    notifyDataSetChanged();
                }
            });
        }

        /**
         *
         * @return size of the array
         */
        @Override
        public int getItemCount() {
            return arr.size();
        }

        /**
         * This class is used to link the row to the code
         */

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, info;
            public ImageView img;
            public View view;

            public MyViewHolder(View view) {
                super(view);
                this.view = view;
                title = view.findViewById(R.id.title);
                info = view.findViewById(R.id.info);
                img = view.findViewById(R.id.img);
            }
        }
    }

    /**
     * This method is used to activate the back button
     * @param item
     * @return boolean
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
