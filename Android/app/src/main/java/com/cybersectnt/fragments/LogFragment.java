package com.cybersectnt.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.globalVars;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;


public class LogFragment extends Fragment {

    private OnLogFragmentInteractionListener mListener;
    private EditText LogEditText;

    public LogFragment() {
    }

    /*
    Creating an initializing the view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        LogEditText = view.findViewById(R.id.EditTextForLog);
        Button saveLogButton = view.findViewById(R.id.ButtonForSavingLog);
        saveLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveLog();
            }
        });
        loadDB();
        return view;
    }

    /*
    Retrieving the log from the database and showing it
     */
    public void loadDB() {
        if (LogEditText != null) {
            if(globalVars.isLocal()){
                LogEditText.setText(globalVars.getLog());
            } else{
                LogEditText.setText(globalVars.getTargetLog());
            }
        }
    }

    /*
    Save the log if the user did any changes to it
     */
    private void SaveLog() {
        HashMap<String, String> data = new HashMap<>();
        data.put("Log", LogEditText.getText().toString());
        String CurrentID = globalVars.getUserID();
        if(!globalVars.isLocal()){
            CurrentID = globalVars.getTargetID();
        }
        globalVars.getUserDocument(CurrentID).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
    This method is to load the database if the application stopped
     */
    @Override
    public void onResume() {
        super.onResume();
        loadDB();
    }

    /*
    This method is used to attach the fragment
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnLogFragmentInteractionListener) {
            mListener = (OnLogFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLogFragmentInteractionListener");
        }
    }

    /*
    This method is used to detach the fragment
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnLogFragmentInteractionListener {
    }
}
