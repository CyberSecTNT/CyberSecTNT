package com.cybersectnt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.cybersectnt.activities.MainActivity;
import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.data.PendingAttackListData;
import com.cybersectnt.data.ReceivedEmailListData;
import com.cybersectnt.data.TaskListData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


public class globalVars {
    //User Related
    private static String userName = "";
    private static String TargetUserName = "";
    private static String userID = "";
    private static String Log = "";
    private static String TargetLog = "";
    private static String TargetID = "";
    private static int userPoints = -1;
    private static HashMap<String, Long> MyToolsLevel;
    private static HashMap<String, Long> TargetToolsLevel;
    private static int UserBankAccountPending;
    private static int UserBankAccountSecured;
    private static ArrayList<PendingAttackListData> PendingAttacksArrayList;
    private static ArrayList<TaskListData> AllAvailableTasks;
    private static HashMap<String, Boolean> PendingAttacksHashMap;
    private static HashMap<String, Object> AchievementsData;
    private static ArrayList<ReceivedEmailListData> PhishingEmailTemplatesArrayList;
    private static boolean isLocal = true;

    public static String getTargetUserName() {
        return TargetUserName;
    }

    public static void setTargetUserName(String targetUserName) {
        TargetUserName = targetUserName;
    }

    public static HashMap<String, Long> getTargetToolsLevel() {
        return TargetToolsLevel;
    }

    /*
    This method is to initialize the hashMap that has all the tools and their levels
     */
    public static void setTargetToolsLevel(HashMap<String, Long> targetToolsLevel) {
        TargetToolsLevel = targetToolsLevel;
    }

    /*
    This method return array containing the phishing emails
     */
    public static ArrayList<ReceivedEmailListData> getPhishingEmailTemplatesArrayList() {
        return PhishingEmailTemplatesArrayList;
    }

    /*
    return a boolean whether the user is on their current machine or not
     */
    public static boolean isLocal() {
        return isLocal;
    }

    /*
    changing the value for islocal
     */
    public static void setIsLocal(boolean isLocal) {
        globalVars.isLocal = isLocal;
    }

    /*
    This to initialize the arrayList containing the phishing emails
     */
    public static void setPhishingEmailTemplatesArrayList(ArrayList<ReceivedEmailListData> phishingEmailTemplatesArrayList) {
        PhishingEmailTemplatesArrayList = phishingEmailTemplatesArrayList;
    }

    /*
    This method returns the achievements data
     */
    public static HashMap<String, Object> getAchievementsData() {
        if (AchievementsData == null) {
            AchievementsData = new HashMap<>();
        }
        return AchievementsData;
    }

    /*
    Changing the value of achievementsData
     */
    public static void setAchievementsData(HashMap<String, Object> achievementsData) {
        AchievementsData = achievementsData;
    }

    /*
    This method returns the score for the user
     */
    public static int calculateScore(HashMap<String, Object> achievementsData, int userPoints) {
        int score = userPoints;
        if (!(achievementsData == null)) {
            for (Map.Entry<String, Object> data : achievementsData.entrySet()) {
                score += Integer.parseInt(data.getValue().toString());
            }
        }


        return score;
    }

    /*
    Returns the target's id
     */

    public static String getTargetID() {
        return TargetID;
    }
    /*
    Changes the target id
     */

    public static void setTargetID(String targetID) {
        TargetID = targetID;
    }

    /*
    This method returns the username
     */
    public static String getUserName() {
        return userName;
    }

    /*
    this method returns the user id after checking if they logged in
     */
    public static String getUserID() {
        if (userID == null && mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
        return userID;
    }

    /*
    Initializing the array list containing all the pending attacks
     */
    public static ArrayList<PendingAttackListData> getPendingAttacksArrayList() {
        if (PendingAttacksArrayList == null) {
            PendingAttacksArrayList = new ArrayList<>();
        }
        return PendingAttacksArrayList;
    }

    /*
    This method returns all available tasks
     */
    public static ArrayList<TaskListData> getAllAvailableTasks() {
        if (AllAvailableTasks == null) {
            AllAvailableTasks = new ArrayList<>();
        }
        return AllAvailableTasks;
    }

    /*
    This method returns all the pending attacks
     */
    public static HashMap<String, Boolean> getPendingAttacksHashMap() {
        if (PendingAttacksHashMap == null) {
            PendingAttacksHashMap = new HashMap<>();
        }
        return PendingAttacksHashMap;
    }

    /*
    This method returns the userPoints
     */
    public static int getUserPoints() {
        return userPoints;
    }

    /*
    This method returns the underpants
     */
    public static int getUserLevel() {
        return userPoints / 100 + 1;
    }

    /*
    This method returns a hashMap containing all the tools for the user with their levels
     */
    public static HashMap<String, Long> getMyToolsLevel() {
        return MyToolsLevel;
    }

    /*
    This method returns the user bank account
     */
    public static int getUserBankAccountPending() {
        return UserBankAccountPending;
    }

    /**
     * changing the user bank account
     *
     * @param userBankAccountPending
     */
    public static void setUserBankAccountPending(int userBankAccountPending) {
        UserBankAccountPending = userBankAccountPending;
    }

    /*
    returns the user bank account
     */
    public static int getUserBankAccountSecured() {
        return UserBankAccountSecured;
    }
    /*
    This method is used to change the user bank account
     */

    public static void setUserBankAccountSecured(int userBankAccountSecured) {
        UserBankAccountSecured = userBankAccountSecured;
    }

    /**
     * This method is used to change the username
     *
     * @param userName
     */
    public static void setUserName(String userName) {
        globalVars.userName = userName;
    }

    /**
     * This method is used to change the points for the user
     *
     * @param userPoints
     */
    public static void setUserPoints(int userPoints) {
        globalVars.userPoints = userPoints;
    }

    /*
    This method updates the tools levels for the user
     */
    public static void setMyToolsLevel(HashMap<String, Long> myToolsLevel) {
        globalVars.MyToolsLevel = myToolsLevel;
    }

    //DataBase
    private static FirebaseFirestore db;
    private static FirebaseAuth mAuth;

    /*
    Initializing the database
     */
    public static void initDB() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

    /*
    Getting an instance after authentication process
     */
    public static boolean initmAuth() {
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return false;
        }
        return true;
    }

    /*
    This method is used to retrieve the user id
     */
    public static DocumentReference getUserDocument(String ID) {
        if (ID.isEmpty()) {
            ID = getUserID();
        }
        return getDB().collection("Users").document(ID);
    }

    /*
    This method is to get the phishingEmails from the database
     */
    public static CollectionReference getPhishingEmailTemplates() {
        return getDB().collection("PhishingTemplates");
    }

    /*
    This method is to retrieve the users from the database
     */
    public static CollectionReference getUsersCollection() {
        return getDB().collection("Users");
    }

    /*
    This method is to retrieve the achievements from the database
     */
    public static CollectionReference getAchievementsCollection() {
        return getDB().collection("Achievements");
    }

    /*
    This method is used to retrieve the scenarios from the database
     */
    public static CollectionReference getScenariosCollection() {
        return getDB().collection("Scenarios");
    }

    /*
    Initializing the firestore databases
     */
    public static FirebaseFirestore getDB() {

        if (db == null) {
            initDB();
        }
        return db;
    }

    /*
    Initializing firebase authentication
     */
    public static FirebaseAuth getmAuth() {
        if (mAuth == null || userID == null) {
            initmAuth();
        }
        return mAuth;
    }

    /**
     * his method allows the user to go to the main activity
     *
     * @param baseContext
     */
    public static void moveToMainActivityFresh(Context baseContext) {
        Intent intent = new Intent(baseContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        baseContext.startActivity(intent);
    }

    /**
     * Check if an edit text is empty and returns a boolean
     *
     * @param editText
     */
    public static boolean isEmptyEditText(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    /**
     * This method converts the text inside an edit to text to a string
     *
     * @param editText
     */
    public static String getEditTextString(EditText editText) {
        return editText.getText().toString().trim();
    }


    /**
     * This method checks if an input is empty
     *
     * @param editText
     */
    public static boolean isEmptyTextInputLayout(TextInputLayout editText) {
        return editText.getEditText().getText().toString().isEmpty();
    }


    /**
     * This method to retrieve the text inside an edit text
     *
     * @param editText
     */
    public static String getTextInputLayoutString(TextInputLayout editText) {
        return editText.getEditText().getText().toString().trim();
    }


    /*
    This method returns target log
     */
    public static String getTargetLog() {
        return TargetLog;
    }


    /*
    This method changes the log based on a string
     */
    public static void setLog(String log) {
        Log = log;
    }

    /*
    This method initialize the targetLog
     */
    public static void setTargetLog(String targetLog) {
        TargetLog = targetLog;
    }


    /**
     * This method is used to update the log
     *
     * @param log
     * @param ID
     */
    public static void updateLog(String log, String ID) {

        if (ID.equals(TargetID)) {
            log = log + "\n" + TargetLog;
            TargetLog = log;
        } else {
            log = log + "\n" + getLog();
            setLog(log);
        }
        getUserDocument(ID).update("Log", log.trim());
    }


    /*
    This method returns the log
     */
    public static String getLog() {

        return Log;
    }

    /**
     * This method will allow the user to send an email which requires the email to be sent to and the subject
     *
     * @param Subject, To
     * @return sends an email
     */
    public static void sendEmailToAFriend(final Activity activity, String To, String Subject) {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(activity);
        View dialogView = activity.getLayoutInflater().inflate(R.layout.popup_send_email, null);
        aBuilder.setView(dialogView);
        final AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();
        final EditText ToEditText = dialogView.findViewById(R.id.EditTextForSendingTo);
        final EditText SubjectEditText = dialogView.findViewById(R.id.EditTextForSendingSubject);

        if (To != null && Subject != null) {
            ToEditText.setText(To.substring(0, To.indexOf('@')));
            SubjectEditText.setText("RE:" + Subject);
        }

        final EditText ContentEditText = dialogView.findViewById(R.id.EditTextForSendingContent);
        Button SendingButton = dialogView.findViewById(R.id.ButtonForSendingEmail);
        SendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getEditTextString(ToEditText).isEmpty() || getEditTextString(SubjectEditText).isEmpty() || getEditTextString(ContentEditText).isEmpty()) {
                    Toast.makeText(activity, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                getUsersCollection().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String searchingFor = getEditTextString(ToEditText);
                        String ID = "";
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String result = documentSnapshot.get("UserName") + "";
                            if (searchingFor.toLowerCase().equals(result.toLowerCase())) {
                                ID = documentSnapshot.getId();
                                break;
                            }
                        }
                        if (!ID.isEmpty()) {
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("Subject", getEditTextString(SubjectEditText));
                            hm.put("Body", getEditTextString(ContentEditText));
                            hm.put("From", getUserName() + "@cybsectnt.com");
                            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy;MM;dd;HH;mm;ss", Locale.US);
                            sdf.setLenient(false);
                            sdf.setTimeZone(TimeZone.getTimeZone("EST"));
                            Date date = new Date();
                            String currentTime = sdf.format(date);
                            hm.put("Date", currentTime);
                            getUserDocument(ID)
                                    .collection("Mail")
                                    .add(hm).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(activity, "Email is sent", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                            });
                        } else {
                            Toast.makeText(activity, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
