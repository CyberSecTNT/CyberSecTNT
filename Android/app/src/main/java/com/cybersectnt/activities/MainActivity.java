package com.cybersectnt.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.cybersectnt.MainActivityViewPagerAdapter;
import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.data.AntiVirusListData;
import com.cybersectnt.data.PendingAttackListData;
import com.cybersectnt.data.TaskListData;
import com.cybersectnt.fragments.ATKFragment;
import com.cybersectnt.fragments.BankAccountFragment;
import com.cybersectnt.fragments.LogFragment;
import com.cybersectnt.fragments.ScanFragment;
import com.cybersectnt.fragments.ToolsFragment;
import com.cybersectnt.globalVars;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity
        implements ScanFragment.OnScanFragmentInteractionListener
        , LogFragment.OnLogFragmentInteractionListener,
        ATKFragment.OnATKFragmentInteractionListener,
        ToolsFragment.OnToolsFragmentInteractionListener,
        BankAccountFragment.OnFragmentInteractionListener {
    TextView CurrentPageTitleTextView, UserNameTextView, UserLevelTextView;
    ProgressBar LevelProgressBar;
    ViewPager FragmentsViewPager;
    MainActivityViewPagerAdapter ViewPagerAdapter;
    BottomNavigationView bottomNavigationView;
    MenuItem prevMenuItem;
    NavigationView navigationView;
    ImageButton ShowSideNavigationBarButton, MailBoxOrUploadSpywareButton;
    BankAccountFragment bankAccountFragment;
    ToolsFragment toolsFragment;
    ATKFragment atkFragment;
    LogFragment logFragment;
    ScanFragment scanFragment;
    String CurrentID;
    ListenerRegistration UserChangesListener;

    private static final String TAG = "MainActivity";

    /**
     * Setup and initiate the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        LinkDesignWithCode();
        showUserInfo();
        setupViewPager();
        FirebaseMessaging.getInstance().subscribeToTopic("all");
    }

    /*
        Here we are setting up all the fragments that will be used in this activity so we can switch between them later on.
    */
    private void setupViewPager() {
        ViewPagerAdapter = new MainActivityViewPagerAdapter(getSupportFragmentManager());
        toolsFragment = new ToolsFragment();
        bankAccountFragment = new BankAccountFragment();
        atkFragment = new ATKFragment();
        logFragment = new LogFragment();
        scanFragment = new ScanFragment();
        ViewPagerAdapter.addFragment(logFragment);
        ViewPagerAdapter.addFragment(scanFragment);
        ViewPagerAdapter.addFragment(atkFragment);
        ViewPagerAdapter.addFragment(toolsFragment);
        ViewPagerAdapter.addFragment(bankAccountFragment);
        FragmentsViewPager.setAdapter(ViewPagerAdapter);
        linkBottomNavigationBarWithViewPager();
    }

    /*
        Here we are linking the bottom bar with the fragments
   */
    private void linkBottomNavigationBarWithViewPager() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.LogMenu:
                                FragmentsViewPager.setCurrentItem(0);
                                break;
                            case R.id.ScanMenu:
                                FragmentsViewPager.setCurrentItem(1);
                                break;
                            case R.id.ATKMenu:
                                FragmentsViewPager.setCurrentItem(2);
                                break;
                            case R.id.ToolsMenu:
                                FragmentsViewPager.setCurrentItem(3);
                                break;
                            case R.id.BankAccountMenu:
                                FragmentsViewPager.setCurrentItem(4);
                                break;
                        }
                        return false;
                    }
                });
        FragmentsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
                switch (position) {
                    case 0:
                        CurrentPageTitleTextView.setText("Log");
                        break;
                    case 1:
                        CurrentPageTitleTextView.setText("Scan");
                        break;
                    case 2:
                        CurrentPageTitleTextView.setText("Attack");
                        break;
                    case 3:
                        CurrentPageTitleTextView.setText("Tools");
                        break;
                    case 4:
                        CurrentPageTitleTextView.setText("Bank");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }

    /**
     * This method will show to proper information for the user
     *
     * @return populate the fragments with the data for that user
     * @input user id
     */
    private void showUserInfo() {
        globalVars.getPendingAttacksArrayList().clear();
        if (globalVars.isLocal() || globalVars.getTargetID() == null || globalVars.getTargetID().isEmpty()) {
            CurrentID = globalVars.getUserID();
        } else {
            CurrentID = globalVars.getTargetID();
            globalVars.updateLog("You Device is Accessed by " + globalVars.getUserName(), globalVars.getTargetID());
        }
        UserChangesListener = globalVars.getUserDocument(CurrentID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    HashMap<String, Object> achievementsData = (HashMap<String, Object>) documentSnapshot.get("AchievementsData");
                    globalVars.setAchievementsData(achievementsData);
                    globalVars.setUserBankAccountPending(Integer.parseInt(documentSnapshot.get("BankAccount.Pending") + ""));
                    globalVars.setUserBankAccountSecured(Integer.parseInt(documentSnapshot.get("BankAccount.Secured") + ""));
                    int points = Integer.parseInt(documentSnapshot.get("UserPoints") + "");
                    if ((documentSnapshot.get("ClaimedForFirstTime") + "").equals("null") && globalVars.isLocal()) {
                        HashMap<String, Object> hm = new HashMap<>();
                        hm.put("ClaimedForFirstTime", true);
                        globalVars.getUserDocument(globalVars.getUserID()).set(hm, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                globalVars.getUserDocument(globalVars.getUserID()).update("BankAccount.Pending", FieldValue.increment(200));
                            }
                        });
                    }

                    if (globalVars.isLocal()) {
                        for (TaskListData data : globalVars.getAllAvailableTasks()) {
                            String query = data.getQuery();
                            String str;
                            if (query.contains("UserTools_Upgrade")) {
                                query = query.substring(0, query.indexOf("_"));
                                str = documentSnapshot.get("UserTools." + query) + "";
                            } else {
                                str = documentSnapshot.get("AchievementsData." + query) + "";
                            }
                            if (!str.equals("null")) {
                                data.setQueryResult(Integer.parseInt(str));
                            }
                        }
                    }
                    globalVars.setUserPoints(points);
                    if (globalVars.isLocal()) {
                        globalVars.setLog(documentSnapshot.get("Log") + "");
                    } else {
                        globalVars.setTargetLog(documentSnapshot.get("Log") + "");
                    }

                    bankAccountFragment.loadDB();
                    toolsFragment.loadDB();
                    logFragment.loadDB();
                }
            }
        });
        globalVars.getUserDocument(CurrentID).collection("PendingAttacks").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }
                for (DocumentChange document : snapshots.getDocumentChanges()) {
                    switch (document.getType()) {
                        case ADDED:
                            PendingAttackListData data = new PendingAttackListData();
                            data.setUserID(document.getDocument().get("ID") + "");
                            data.setDocumentID(document.getDocument().getId());
                            data.setEndTime(document.getDocument().get("Finish") + "");
                            data.setStartTime(document.getDocument().get("Started") + "");
                            data.setToolUsed(document.getDocument().get("ToolUsed") + "");
                            String state = document.getDocument().get("State") + "";
                            if (state.equals("null")) {
                                data.setState("false");
                            } else {
                                data.setState(state);
                            }
                            data.setUserName(document.getDocument().get("UserName") + "");
                            if (!data.getUserID().equals(globalVars.getUserID()) && globalVars.getPendingAttacksArrayList().indexOf(data) == -1) {
                                globalVars.getPendingAttacksArrayList().add(data);
                                globalVars.getPendingAttacksHashMap().put(data.getUserID(), Boolean.TRUE);
                            }
                            atkFragment.loadDB();
                            break;
                        case MODIFIED:
                            break;
                        case REMOVED:
                            break;
                    }
                }
            }
        });
    }

    /**
     * This method link the xml views with objects to allow us to control it.
     */
    private void LinkDesignWithCode() {
        CurrentPageTitleTextView = findViewById(R.id.TextViewToCurrentPage);
        CurrentPageTitleTextView.setText("Log");
        FragmentsViewPager = findViewById(R.id.ViewPagerForTheFragments);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        MailBoxOrUploadSpywareButton = findViewById(R.id.ButtonForMailBoxOrUploadSpyware);
        navigationView = findViewById(R.id.SideNavigationView);
        ShowSideNavigationBarButton = findViewById(R.id.ButtonForSideNavigationBar);

        if (globalVars.isLocal()) {
            ShowSideNavigationBarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DrawerLayout navDrawer = findViewById(R.id.drawer_layout);
                    navDrawer.openDrawer(GravityCompat.START);
                    UserNameTextView = navDrawer.findViewById(R.id.TextViewToShowUserName);
                    UserLevelTextView = navDrawer.findViewById(R.id.TextViewToShowUserLevel);
                    LevelProgressBar = navDrawer.findViewById(R.id.ProgressBarToVisualizeLevel);
                    UserNameTextView.setText(globalVars.getUserName());
                    UserLevelTextView.setText("Level: " + globalVars.getUserLevel());
                    LevelProgressBar.setProgress(((globalVars.getUserPoints() - (globalVars.getUserLevel() - 1) * 100)));
                }
            });
            TextView SignoutButton = findViewById(R.id.Signout);
            SignoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    globalVars.getmAuth().signOut();
                    //TODO change it with a method from globalvars
                    Intent intent = new Intent(getBaseContext(), SplashScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.ScoreboardMenu:
                            startActivity(new Intent(getBaseContext(), ScoreboardActivity.class));
                            break;
                        case R.id.ProfileMenu:
                            startActivity(new Intent(getBaseContext(), ProfileActivity.class));
                            break;
                        case R.id.TasksMenu:
                            startActivity(new Intent(getBaseContext(), TasksListActivity.class));
                            break;
                        case R.id.ScenariosMenu:
                            startActivity(new Intent(getBaseContext(), ScenariosListActivity.class));
                            break;
                        case R.id.AntiVirusMenu:
                            antiVirus();
                            break;
                    }
                    return true;
                }
            });
        } else {
            MailBoxOrUploadSpywareButton.setImageDrawable(getDrawable(R.drawable.spyware_icon));
            ShowSideNavigationBarButton.setImageDrawable(getDrawable(R.drawable.back));
            ShowSideNavigationBarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backButton();
                }
            });
        }
    }

    /**
     * This method is to link back button and for the user to logout and go back to the splash screen
     */
    private void backButton() {
        Intent intent = new Intent(getBaseContext(), SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /*
        This method is responsible for detecting all the spy wares implanted in the user and clears them ( Just like an actual anti virus)
    */
    private void antiVirus() {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(MainActivity.this);
        final View dialogView = getLayoutInflater().inflate(R.layout.popup_antivirus_tool, null);
        aBuilder.setView(dialogView);
        final AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();
        final ImageView clearSpywaresButton = dialogView.findViewById(R.id.ButtonForClearingSpywares);
        final ImageView nothingToShowImageView = dialogView.findViewById(R.id.ImageViewToShowSafeAntiVirus);
        final ListView spywaresListView = dialogView.findViewById(R.id.ListViewToViewSpywares);
        globalVars.getUserDocument(globalVars.getUserID()).collection("SpywareAttacks").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                final ArrayList<AntiVirusListData> arr = new ArrayList<>();
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    String type = document.get("ToolUsed") + "";
                    if (type.equals("Spyware")) {
                        int spywareLevel = Integer.parseInt(document.get("ToolUsedLevel") + "");
                        int myAntiVirus = Integer.parseInt(globalVars.getMyToolsLevel().get("AntiVirusLevel").toString());
                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy;MM;dd;HH;mm;ss", Locale.US);
                        sdf.setLenient(false);
                        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
                        try {
                            Date currentDate = new Date();
                            Date finishDate = sdf.parse(document.get("Finish") + "");
                            int luck = new Random().nextInt(2);
                            if ((myAntiVirus > spywareLevel || (myAntiVirus == spywareLevel) && luck == 1) && currentDate.after(finishDate) || true) {
                                AntiVirusListData data = new AntiVirusListData();
                                data.setAttackerID(document.get("AttackerID") + "");
                                data.setDocumentID(document.getId());
                                data.setAttackerDocumentID(document.get("AttackerDocumentID") + "");
                                data.setName(document.get("UserName") + "");
                                arr.add(data);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }
                if (arr.size() == 0) {
                    spywaresListView.setVisibility(View.GONE);
                    nothingToShowImageView.setVisibility(View.VISIBLE);
                    clearSpywaresButton.setVisibility(View.GONE);
                } else {
                    spywaresListView.setVisibility(View.VISIBLE);
                    clearSpywaresButton.setVisibility(View.VISIBLE);
                    nothingToShowImageView.setVisibility(View.GONE);
                }
                final ArrayAdapter<AntiVirusListData> itemsAdapter = new ArrayAdapter<AntiVirusListData>(getBaseContext(), android.R.layout.simple_list_item_1, arr) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        if (position % 2 == 0) {
                            view.setBackgroundColor(getContext().getColor(R.color.colorOffWhite));
                        }
                        TextView tv = view.findViewById(android.R.id.text1);
                        tv.setText(this.getItem(position).getName());
                        return view;
                    }
                };
                spywaresListView.setAdapter(itemsAdapter);
            }
        });
        if (spywaresListView.getAdapter() != null && spywaresListView.getAdapter().getCount() > 0) {
            globalVars.getUserDocument(globalVars.getUserID()).update("AchievementsData.AntiVirus_Used", FieldValue.increment(1));
        }
        clearSpywaresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < spywaresListView.getAdapter().getCount(); i++) {
                    AntiVirusListData data = (AntiVirusListData) spywaresListView.getAdapter().getItem(i);
                    String uploaderID = data.getAttackerID();
                    String attackerDocumentID = data.getAttackerDocumentID();
                    String documentID = data.getDocumentID();
                    globalVars.getUserDocument(uploaderID).collection("PendingAttacks").document(attackerDocumentID).delete();
                    globalVars.getUserDocument(globalVars.getUserID()).collection("SpywareAttacks").document(documentID).delete();
                }
                if (spywaresListView.getAdapter().getCount() > 0) {
                    globalVars.updateLog("Deleted " + spywaresListView.getAdapter().getCount() + " spywares", globalVars.getUserID());
                    globalVars.getUserDocument(globalVars.getUserID()).update("AchievementsData.Spywares_deleted", FieldValue.increment(spywaresListView.getAdapter().getCount()));
                    long points = globalVars.getMyToolsLevel().get("AntiVirusLevel") * 20;
                    globalVars.getUserDocument(globalVars.getUserID()).update("UserPoints", FieldValue.increment(points));
                }
                alertDialog.cancel();
            }
        });
    }

    /**
     * This method is simply to allow the user to either see his email if he was in his page or to upload a spyware if the user was in the
     */
    public void MoveToMailBoxOrUploadSpyware(View view) {
        if (globalVars.isLocal()) {
            startActivity(new Intent(getBaseContext(), MailBoxActivity.class));
        } else {
            view.setClickable(false);
            final long MySpywareLevel = globalVars.getMyToolsLevel().get("SpywareLevel");
            long TargetFirewallLevel = globalVars.getTargetToolsLevel().get("FirewallLevel");
            double TimeCalcInMinutes;
            if (MySpywareLevel >= TargetFirewallLevel) {
                TimeCalcInMinutes = (MySpywareLevel - TargetFirewallLevel + 1) * 7;
            } else {
                TimeCalcInMinutes = (TargetFirewallLevel - MySpywareLevel + 1) * 15;
            }
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy;MM;dd;HH;mm;ss", Locale.US);
            sdf.setLenient(false);
            sdf.setTimeZone(TimeZone.getTimeZone("EST"));
            Date date = new Date();
            String currentTime = sdf.format(date);

            Calendar now = Calendar.getInstance();
            now.add(Calendar.MINUTE, (int) TimeCalcInMinutes);
            final String FormulaTime = sdf.format(now.getTime());

            final HashMap<String, Object> hm = new HashMap<>();
            hm.put("Started", currentTime);
            hm.put("Finish", FormulaTime);
            hm.put("ToolUsed", "Spyware");
            hm.put("ToolUsedLevel", MySpywareLevel);
            hm.put("VictimID", globalVars.getTargetID());
            hm.put("AttackerID", globalVars.getUserID());
            hm.put("UserName", globalVars.getTargetUserName());
            hm.put("ClaimedTime", FormulaTime);
            globalVars.getUserDocument(globalVars.getUserID()).collection("PendingAttacks").add(hm).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    hm.put("AttackerDocumentID", documentReference.getId());
                    hm.put("UserName", globalVars.getUserName());
                    globalVars.getUserDocument(globalVars.getTargetID()).collection("SpywareAttacks").document(globalVars.getUserID()).set(hm, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            globalVars.updateLog("Uploading level " + MySpywareLevel + " spyware to " + globalVars.getTargetUserName(), globalVars.getUserID());
                            globalVars.updateLog("Downloading a level " + MySpywareLevel + " spyware", globalVars.getTargetID());
                            globalVars.getUserDocument(globalVars.getUserID()).update("AchievementsData.Phishing_Used", FieldValue.increment(1));
                            long points = globalVars.getMyToolsLevel().get("SpywareLevel") * 50;
                            globalVars.getUserDocument(globalVars.getUserID()).update("UserPoints", FieldValue.increment(points));
                        }
                    });
                }
            });


        }
    }

    @Override
    public void refreshToolsFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(ViewPagerAdapter.getItem(3)).attach(ViewPagerAdapter.getItem(3)).commit();
    }

    /*
        This method either cancels the application or it allows the user to go back to his machine
    */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (globalVars.isLocal()) {
            finish();
        } else {
            globalVars.setIsLocal(true);
            backButton();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        UserChangesListener.remove();
    }
}
