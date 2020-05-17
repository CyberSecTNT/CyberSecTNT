package com.cybersectnt.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.globalVars;
import com.cybersectnt.data.ReceivedEmailListData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class MailBoxActivity extends AppCompatActivity {
    RecyclerView ReceivedEmailListRecyclerView;
    CustomReceivedEmailsAdapter adapter;
    ArrayList<ReceivedEmailListData> ReceivedEmailsArrayList;
    FloatingActionButton SendEmailFloatingActionButton;
    private int SingleEmailViewRequestCode = 1996;

    /**
     * Setup and initiate the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_box);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinkDesignWithCode();
        initVars();
        loadDB();
    }

    /**
     * This method link the xml views with objects to allow us to control it.
     */
    private void LinkDesignWithCode() {
        ReceivedEmailListRecyclerView = findViewById(R.id.RecyclerViewToViewReceivedEmails);
        SendEmailFloatingActionButton = findViewById(R.id.FloatingActionButtonForSendingEmail);
    }

    /**
     * This method will initiate the global variables and set listeners
     */
    private void initVars() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ReceivedEmailListRecyclerView.setLayoutManager(layoutManager);
        ReceivedEmailsArrayList = new ArrayList<>();
        adapter = new CustomReceivedEmailsAdapter(this, ReceivedEmailsArrayList);
        ReceivedEmailListRecyclerView.setAdapter(adapter);
        ReceivedEmailListRecyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(),
                DividerItemDecoration.VERTICAL));
        SendEmailFloatingActionButton.bringToFront();
        SendEmailFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalVars.sendEmailToAFriend(MailBoxActivity.this, null, null);
            }
        });

    }

    /**
     * This method will load database data from User's Mail collection adding the result to the arraylist to populate the list
     * @input UserID
     */
    private void loadDB() {
        globalVars.getUserDocument(globalVars.getUserID()).collection("Mail").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("TAG", "listen:error", e);
                    return;
                }

                for (DocumentChange documentChange : snapshots.getDocumentChanges()) {
                    switch (documentChange.getType()) {
                        case ADDED:
                            DocumentSnapshot document = documentChange.getDocument();
                            ReceivedEmailListData mail = new ReceivedEmailListData();
                            mail.setID(document.getId());
                            mail.setFrom(document.get("From") + "");
                            mail.setSubject(document.get("Subject") + "");
                            mail.setBody(document.get("Body") + "");
                            mail.setPhisherID(document.get("PhisherID") + "");
                            mail.setDate(document.get("Date") + "");
                            if (mail.getPhisherID().equals("null")) {
                                mail.setPhisherID(document.get("SenderID") + "");
                                mail.setPhishing(false);
                            } else {
                                mail.setPhishing(true);
                            }
                            ReceivedEmailsArrayList.add(mail);
                            Collections.sort(ReceivedEmailsArrayList);
                            ReceivedEmailListRecyclerView.setAdapter(adapter);
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
     * This is the Custom adapter class to link the recyclerview from the arraylist
     */
    public class CustomReceivedEmailsAdapter extends RecyclerView.Adapter<CustomReceivedEmailsAdapter.ViewHolder> {
        public Context context;
        public ArrayList<ReceivedEmailListData> arr;

        public CustomReceivedEmailsAdapter(Context context, ArrayList<ReceivedEmailListData> arr) {
            this.context = context;
            this.arr = arr;
        }
        /**
         * This method will assign the layout to be viewed for each item in the recyclerview
         */
        @NonNull
        @Override
        public CustomReceivedEmailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row_emailbox_email, parent, false);
            return new ViewHolder(view);
        }

        /**
         * This method will populate the data in each view and setup a listener to move to the email view
         */
        @Override
        public void onBindViewHolder(@NonNull CustomReceivedEmailsAdapter.ViewHolder holder, final int position) {
            String content = arr.get(position).getBody();
            String title = arr.get(position).getSubject();

            if (content.length() > 100) {
                content = content.substring(0, 100) + "...";
            }

            holder.Title.setText(title);
            holder.Content.setText(content);


            holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    DialogInterface.OnClickListener dialockClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    globalVars.getUserDocument(globalVars.getUserID()).collection("Mail").document(arr.get(position).getID()).delete();
                                    globalVars.updateLog(arr.get(position).getSubject() + " mail is deleted", globalVars.getUserID());
                                    arr.remove(position);
                                    notifyDataSetChanged();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(MailBoxActivity.this);
                    builder.setMessage("Are you sure you want to delete this email").setPositiveButton("Yes", dialockClickListener)
                            .setNegativeButton("No", dialockClickListener).show();
                    return false;
                }
            });

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), SingleEmailActivity.class);
                    intent.putExtra("Email", arr.get(position));
                    intent.putExtra("Position", position);
                    startActivityForResult(intent, SingleEmailViewRequestCode);
                }
            });
            if (position % 2 == 0) {
                holder.view.setBackgroundColor(getColor(R.color.colorOffWhite));
            }
        }

        /*
        This method is responsible for returning the number of emails
         */

        @Override
        public int getItemCount() {
            return arr.size();
        }

        /*
        This method is to provide to connect the title text view and the content to the email so we can fill them up from the database later

         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView Title, Content;
            public View view;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                Title = view.findViewById(R.id.TextViewToShowEmailTitle);
                Content = view.findViewById(R.id.TextViewToShowEmailContent);
            }
        }
    }

    /**
     * This method handles the menu options in the actionbar
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


    /**
     * This method will be called when the user is back from the emailviewer to update the list
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SingleEmailViewRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                int position = data.getIntExtra("Position", -1);
                if (position != -1) {
                    ReceivedEmailsArrayList.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

}
