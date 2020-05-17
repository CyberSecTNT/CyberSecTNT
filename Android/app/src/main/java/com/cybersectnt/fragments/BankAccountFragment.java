package com.cybersectnt.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cybersectnt.activities.SocialEngineeringActivity;
import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.data.ReceivedEmailListData;
import com.cybersectnt.globalVars;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class BankAccountFragment extends Fragment {
    View view;
    TextView PendingTextView, SecureTextView, TotalTextView;
    Button SecureOrTransferButton, PhishingButton, SocialEngineeringButton, BruteForceButton;
    EditText UsernameEditText;
    LinearLayout HaveAccessLinearLayout, NoAccessLinearLayout;
    private OnFragmentInteractionListener mListener;
    private int SocialEngineeringRequestCode = 1996;

    public BankAccountFragment() {
        // Required empty public constructor
    }

    /*
    Initializing the starting the view for the fragment
     */

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bank_account, container, false);

        PendingTextView = view.findViewById(R.id.TextViewToShowPendingAmount);
        SecureTextView = view.findViewById(R.id.TextViewToShowSecuredAmount);
        TotalTextView = view.findViewById(R.id.TextViewToShowTotalAmount);

        UsernameEditText = view.findViewById(R.id.EditTextForUsername);
        PhishingButton = view.findViewById(R.id.ButtonForPhishing);
        SocialEngineeringButton = view.findViewById(R.id.ButtonForSocialEngineering);
        BruteForceButton = view.findViewById(R.id.ButtonForBruteForce);
        HaveAccessLinearLayout = view.findViewById(R.id.LinearLayoutToHoldBankAccountInformation);
        NoAccessLinearLayout = view.findViewById(R.id.LinearLayoutToHoldBankAccountAttack);
        SecureOrTransferButton = view.findViewById(R.id.ButtonForSecureOrTransfer);

        if (globalVars.isLocal()) {
            haveAccess();
        } else {
            noAccess();
        }

        SecureOrTransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secureOrTransfer();
            }
        });
        loadDB();
        return view;
    }

    /*
    This method is used to allow the attacker to start social engineering or brute force to access the bank
     */
    private void noAccess() {
        UsernameEditText.setText(globalVars.getTargetUserName());
        HaveAccessLinearLayout.setVisibility(View.GONE);
        NoAccessLinearLayout.setVisibility(View.VISIBLE);
        SecureOrTransferButton.setVisibility(View.GONE);
        PhishingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phishing();
            }
        });
        BruteForceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bruteForce();
            }
        });
        SocialEngineeringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialEngineering();
            }
        });

        globalVars.getUserDocument(globalVars.getTargetID()).collection("BankAccountAccess").whereEqualTo("UserID", globalVars.getUserID()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() > 0) {
                    haveAccess();
                }
            }
        });
    }

    /*
    This method is activated when the attacker were able to break the bank account
     */
    private void haveAccess() {
        HaveAccessLinearLayout.setVisibility(View.VISIBLE);
        NoAccessLinearLayout.setVisibility(View.GONE);
        SecureOrTransferButton.setVisibility(View.VISIBLE);
        if (globalVars.isLocal()) {
            SecureOrTransferButton.setText("Secure");
        } else {
            SecureOrTransferButton.setText("Transfer");
        }
        if (globalVars.getUserBankAccountPending() == 0) {
            SecureOrTransferButton.setEnabled(false);
        }
        int pending = globalVars.getUserBankAccountPending();
        int secured = globalVars.getUserBankAccountSecured();
        String total = pending + secured + "";
        if (PendingTextView != null) {
            PendingTextView.setText(pending + "");
            SecureTextView.setText(secured + "");
            TotalTextView.setText(total + "");
        }
    }

    /*
    This method is used when the attacker initiate a brute force attack and it calculates the time required based on the password level
     */
    private void bruteForce() {
        long MyBruteForceLevel = globalVars.getMyToolsLevel().get("BruteForceLevel");
        long TargetPasswordLevel = globalVars.getTargetToolsLevel().get("PasswordLevel");
        double TimeCalcInMinutes = 0;
        if (MyBruteForceLevel >= TargetPasswordLevel) {
            TimeCalcInMinutes = (MyBruteForceLevel - TargetPasswordLevel + 1) * 80;
        } else {
            TimeCalcInMinutes = (TargetPasswordLevel - MyBruteForceLevel + 1) * 140;
        }
        TimeCalcInMinutes += 6; // Scanning Time
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy;MM;dd;HH;mm;ss", Locale.US);
        sdf.setLenient(false);
        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        Date date = new Date();
        String currentTime = sdf.format(date);

        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, (int) TimeCalcInMinutes);
        String FormulaTime = sdf.format(now.getTime());

        HashMap<String, String> hm = new HashMap<>();
        hm.put("Started", currentTime);
        hm.put("Finish", FormulaTime);
        hm.put("ToolUsed", "BruteForce");
        hm.put("UserName", globalVars.getTargetUserName());
        hm.put("ID", globalVars.getTargetID());
        hm.put("ToolUsedLevel", MyBruteForceLevel + "");
        globalVars.getUserDocument(globalVars.getUserID()).collection("PendingAttacks").document().set(hm, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "Brute Force Attack is started", Toast.LENGTH_SHORT).show();
                globalVars.updateLog("Brute force attack on " + globalVars.getUserName(), globalVars.getUserID());
                globalVars.updateLog("Your account is being attacked by " + globalVars.getUserName(), globalVars.getTargetID());

            }
        });
    }

    /*
    This method allows you to go to the socialengineering activity
     */
    private void socialEngineering() {
        Intent intent = new Intent(getContext(), SocialEngineeringActivity.class);
        startActivityForResult(intent, SocialEngineeringRequestCode);
    }

    /*
    This method allows the attacker to send a phishing email for multiple ones we created to the victim
     */
    private void phishing() {
        int index = new Random().nextInt(globalVars.getPhishingEmailTemplatesArrayList().size());
        ReceivedEmailListData email = globalVars.getPhishingEmailTemplatesArrayList().get(index);
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("Subject", email.getSubject());
        hm.put("Body", email.getBody());
        hm.put("From", email.getFrom());
        hm.put("isFake", email.isFake());
        hm.put("PhisherID", globalVars.getUserID());
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy;MM;dd;HH;mm;ss", Locale.US);
        sdf.setLenient(false);
        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        Date date = new Date();
        String currentTime = sdf.format(date);
        hm.put("Date", currentTime);
        globalVars.getUserDocument(globalVars.getTargetID())
                .collection("Mail")
                .add(hm).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(getContext(), "Phishing email is sent", Toast.LENGTH_SHORT).show();
                globalVars.updateLog("Phishing mail sent to " + globalVars.getUserName(), globalVars.getUserID());
            }
        });
    }

    /*
    This method check if the attacker successful got into the bank, and if he can transfer money
     */
    private void secureOrTransfer() {
        if (globalVars.isLocal()) {
            globalVars.updateLog("Secured " + globalVars.getUserBankAccountPending(), globalVars.getUserID());
            globalVars.setUserBankAccountSecured(globalVars.getUserBankAccountPending() + globalVars.getUserBankAccountSecured());
            globalVars.setUserBankAccountPending(0);
            HashMap<String, Object> account = new HashMap<>();
            account.put("Secured", globalVars.getUserBankAccountSecured());
            account.put("Pending", 0);
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("BankAccount", account);
            globalVars.getUserDocument(globalVars.getUserID()).set(hm, SetOptions.merge());

        } else {
            long pendingAmount = globalVars.getUserBankAccountPending();
            globalVars.setUserBankAccountPending(0);
            HashMap<String, Object> account = new HashMap<>();
            account.put("Secured", globalVars.getUserBankAccountSecured());
            account.put("Pending", 0);
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("BankAccount", account);
            globalVars.getUserDocument(globalVars.getTargetID()).set(hm, SetOptions.merge());
            globalVars.getUserDocument(globalVars.getUserID()).update("BankAccount.Pending", FieldValue.increment(pendingAmount));
            globalVars.updateLog("Transferred " + pendingAmount + " From " + globalVars.getTargetUserName(), globalVars.getUserID());
            globalVars.updateLog("Transferred " + pendingAmount + " To " + globalVars.getUserName(), globalVars.getTargetID());
            SecureOrTransferButton.setEnabled(false);
        }
        PendingTextView.setText("0");
        SecureTextView.setText(globalVars.getUserBankAccountSecured() + "");
        TotalTextView.setText(SecureTextView.getText().toString());
    }

    /*
    This method is used to calculate the time remaining for the brute force to end
     */
    private int TimeDifference(Date now, Date ends) {
        final long milliseconds = TimeUnit.MILLISECONDS.convert(now.getTime() - ends.getTime(), TimeUnit.MILLISECONDS);
        final long hr = TimeUnit.MILLISECONDS.toHours(milliseconds);
        return (int) hr;
    }

    /*
    This method is load the database whether any attack already initiated
     */
    public void loadDB() {
        String CurrentID = globalVars.getUserID();
        if(!globalVars.isLocal()){
           CurrentID = globalVars.getTargetID();
        }
        final String finalCurrentID = CurrentID;
        globalVars.getUserDocument(CurrentID).collection("PendingAttacks").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    try {
                        String finishDateString = document.get("ClaimedTime") + "";
                        if ((document.get("ToolUsedLevel") + "").equals("null")) {
                            continue;
                        }
                        int toolLevel = Integer.parseInt(document.get("ToolUsedLevel") + ""); // My spyware level
                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy;MM;dd;HH;mm;ss", Locale.US);
                        sdf.setLenient(false);
                        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
                        Date current = new Date();
                        Date finishDate = sdf.parse(finishDateString);
                        int timeTobeAdded = TimeDifference(current, finishDate);
                        if (timeTobeAdded > 0) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(finishDate);
                            calendar.add(Calendar.HOUR, timeTobeAdded);
                            int addedValue = toolLevel * 50 * timeTobeAdded;
                            document.getReference().update("ClaimedTime", sdf.format(calendar.getTime()));
                            globalVars.getUserDocument(finalCurrentID).update("BankAccount.Pending", FieldValue.increment(addedValue));
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                int pending = globalVars.getUserBankAccountPending();
                int secured = globalVars.getUserBankAccountSecured();
                String total = pending + secured + "";
                if (PendingTextView != null) {
                    PendingTextView.setText(pending + "");
                    SecureTextView.setText(secured + "");
                    TotalTextView.setText(total + "");
                }
            }
        });
    }

    /*
    This method to check if the user successfully passed the social engineering
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SocialEngineeringRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getBooleanExtra("result", false)) {
                    noAccess();
                }
            }
        }
    }

    /**
     *  This method is used to attach the fragment
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /*
    This method to detach the fragment
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*
    This method is to resume working if the user left
     */
    @Override
    public void onResume() {
        super.onResume();
        loadDB();
    }

    public interface OnFragmentInteractionListener {
    }
}
