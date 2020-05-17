package com.cybersectnt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.data.ReceivedEmailListData;
import com.cybersectnt.globalVars;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;

public class SingleEmailActivity extends AppCompatActivity {
    ReceivedEmailListData email;
    TextView UserNameAndEmailTextView, TitleTextView, ContentTextView;
    Button AuthenticOrReplyButton, FakeOrDeleteButton;


    /**
     * Setup and initiate the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_email);
        getSupportActionBar().setTitle("Email Viewer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        email = (ReceivedEmailListData) getIntent().getSerializableExtra("Email");
        LinkDesignWithCode();
    }

    /**
     * This method link the xml views with objects to allow us to control it.
     */
    private void LinkDesignWithCode() {
        UserNameAndEmailTextView = findViewById(R.id.TextViewToShowUserNameAndEmailAddress);
        TitleTextView = findViewById(R.id.TextViewToShowEmailTitle);
        ContentTextView = findViewById(R.id.TextViewToShowEmailContent);
        AuthenticOrReplyButton = findViewById(R.id.ButtonForAuthenticOrReply);
        FakeOrDeleteButton = findViewById(R.id.ButtonForFakeOrDelete);
        if (email.isPhishing()) {
            AuthenticOrReplyButton.setText("Authentic");
            FakeOrDeleteButton.setText("Fake");
        } else {
            AuthenticOrReplyButton.setText("Reply");
            FakeOrDeleteButton.setText("Delete");
        }
        UserNameAndEmailTextView.setText(email.getFrom());
        TitleTextView.setText(email.getSubject());
        ContentTextView.setText(email.getBody());
    }

    /*
    This options to retrieve the item selected
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

    /*
    This method is to handel if the user chose to reply to the phishing emails or they spotted that the email is fake
     */

    public void AuthenticOrReplyButton(View view) {
        if (email.isPhishing()) {
            if (email.isFake()) {
                giveAccessThenDelete();
            } else {
                givePointsThenDelte();
            }
        } else {
            globalVars.sendEmailToAFriend(SingleEmailActivity.this, email.getFrom(), email.getSubject());
        }
    }

    /*
    This if the user chose the email to be fake
     */

    public void FakeOrDeleteButton(View view) {
        if (email.isPhishing()) {
            if (email.isFake()) {
                givePointsThenDelte();
            } else {
                giveAccessThenDelete();
            }
        } else {
            deleteEmail();
        }
    }


    /*
    This method is to give the points to the user
     */
    private void givePointsThenDelte() {
        int points = 20;
        globalVars.getUserDocument(globalVars.getUserID()).update("UserPoints", FieldValue.increment(points));
        deleteEmail();
    }

    /*
    This method is used to give access to the attacker
     */
    private void giveAccessThenDelete() {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("UserID", email.getPhisherID());
        globalVars.getUserDocument(globalVars.getUserID()).collection("BankAccountAccess").add(hm);
        globalVars.getUserDocument(email.getPhisherID()).update("AchievementsData.Phishing_Used", FieldValue.increment(1));
        deleteEmail();
    }


    /**
     * This method is used to delete the email
     */
    private void deleteEmail() {
        globalVars.getUserDocument(globalVars.getUserID()).collection("Mail").document(email.getID()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Position", getIntent().getIntExtra("Position", -1));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }


}
