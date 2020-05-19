package com.cybersectnt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.data.ReceivedEmailListData;
import com.cybersectnt.data.TaskListData;
import com.cybersectnt.globalVars;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class SplashScreenActivity extends AppCompatActivity {
    GifImageView gifImageView;
    boolean isLocal;

    /**
     * Setup and initiate the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        gifImageView = findViewById(R.id.GifImageViewToShowLogo);
        loadDB();
    }

    /*
    This method is used for to check if the user already logged in or not and if the user is logged in we retiree all of his info while loading the splash screen
     */
    private void loadDB() {
        if (!globalVars.initmAuth()) {
            startActivity(new Intent(getBaseContext(), AuthenticationActivity.class));
            finish();
            return;
        }
        try {
            GifDrawable gifFromResource = new GifDrawable(getResources(), R.drawable.anim_logo);
            gifFromResource.setLoopCount(1);
            gifImageView.setImageDrawable(gifFromResource);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String UserID = getIntent().getStringExtra("TargetID");
                    if (UserID != null) {
                        isLocal = false;
                        globalVars.setTargetID(UserID);
                    } else {
                        UserID = globalVars.getUserID();
                        isLocal = true;
                        globalVars.setTargetID(null);
                    }
                    globalVars.setIsLocal(isLocal);
                    globalVars.getUserDocument(UserID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String Name = documentSnapshot.get("UserName") + "";
                            String points = documentSnapshot.get("UserPoints") + "";
                            if (points.equals("null")) {
                                points = "0";
                            }
                            int Points = Integer.parseInt(points);
                            String log = documentSnapshot.get("Log") + "";
                            if (log.equals("null")) {
                                log = "";
                            }
                            HashMap<String, Long> toolsLevel = (HashMap<String, Long>) documentSnapshot.get("UserTools");
                            globalVars.setUserPoints(Points);
                            globalVars.setUserBankAccountPending(Integer.parseInt(documentSnapshot.get("BankAccount.Pending") + ""));
                            globalVars.setUserBankAccountSecured(Integer.parseInt(documentSnapshot.get("BankAccount.Secured") + ""));
                            if (isLocal) {
                                globalVars.setUserName(Name);
                                HashMap<String, Object> achievementsData = (HashMap<String, Object>) documentSnapshot.get("AchievementsData");
                                globalVars.setMyToolsLevel(toolsLevel);
                                globalVars.setAchievementsData(achievementsData);
                                globalVars.setLog(log);
                                globalVars.getPhishingEmailTemplates().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        ArrayList<ReceivedEmailListData> arrayList = new ArrayList<>();
                                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                                            ReceivedEmailListData email = new ReceivedEmailListData();
                                            email.setFrom(document.get("From") + "");
                                            email.setSubject(document.get("Subject") + "");
                                            email.setBody(document.get("Body") + "");
                                            email.setFake(document.getBoolean("isFake"));
                                            arrayList.add(email);
                                        }
                                        globalVars.setPhishingEmailTemplatesArrayList(arrayList);
                                        globalVars.getAchievementsCollection().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                globalVars.getAllAvailableTasks().clear();
                                                for (DocumentSnapshot doc : queryDocumentSnapshots) {
                                                    TaskListData data = new TaskListData();
                                                    data.setTitle(doc.get("Title") + "");
                                                    data.setBadge(doc.get("Badge") + "");
                                                    data.setPicture(doc.get("Picture") + "");
                                                    data.setQuery(doc.get("Query") + "");
                                                    data.setClaimType(doc.get("ClaimType") + "");
                                                    data.setClaimValue(doc.get("ClaimValue") + "");
                                                    data.setQueryValue(doc.get("QueryValue") + "");
                                                    data.setSubTitle(doc.get("Subtitle") + "");
                                                    data.setDescription(doc.get("Description") + "");
                                                    if(globalVars.getAllAvailableTasks().indexOf(data) == -1){
                                                        globalVars.getAllAvailableTasks().add(data);
                                                    }
                                                }
                                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                                finish();
                                            }
                                        });

                                    }
                                });
                            } else {
                                globalVars.setTargetUserName(Name);
                                globalVars.setTargetLog(log);
                                globalVars.setTargetToolsLevel(toolsLevel);
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                finish();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SplashScreenActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }, gifFromResource.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}