package com.cybersectnt.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.globalVars;
import com.cybersectnt.data.PossibleTargetUserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;


public class ScanFragment extends Fragment {

    private OnScanFragmentInteractionListener mListener;
    RecyclerView PossibleTargetsListRecyclerView;
    EditText QueryUsernameEditText;
    TextView NoItemTextView;
    private ArrayList<PossibleTargetUserData> PossibleTargetsArrayList;
    private CustomPossibleTargetsAdapter adapter;

    public ScanFragment() {
    }

    /*
    This method  create and initialized the view
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        NoItemTextView = view.findViewById(R.id.TextViewToShowNoItems);
        QueryUsernameEditText = view.findViewById(R.id.EditTextForUsername);
        PossibleTargetsListRecyclerView = view.findViewById(R.id.RecyclerViewToViewPossibleTargets);
        PossibleTargetsListRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        final ImageButton scanningBtn = view.findViewById(R.id.ButtonForScanningUsers);
        scanningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScannerBtn();
            }
        });
        if (!globalVars.isLocal()) {
            scanningBtn.setEnabled(false);
        }
        initVars();
        return view;
    }

    /*
    This method initlize the layout and the recycler view that will be used to display the information
     */

    private void initVars() {
        PossibleTargetsArrayList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        PossibleTargetsListRecyclerView.setLayoutManager(layoutManager);
        adapter = new CustomPossibleTargetsAdapter(getContext(), PossibleTargetsArrayList);
        PossibleTargetsListRecyclerView.setAdapter(adapter);

    }

    /*
    Scanner button that would retrieve all the users that it's possible for the attacker to bypass it can and search for user with username
     */
    public void ScannerBtn() {
        Query query = globalVars.getUsersCollection().whereLessThanOrEqualTo("UserTools.FirewallLevel", globalVars.getMyToolsLevel().get("FirewallLevel"));
        final String textQuery = globalVars.getEditTextString(QueryUsernameEditText);
        if (!textQuery.isEmpty() && !textQuery.toLowerCase().equals("all")) {
            query = query.whereEqualTo("UserName", QueryUsernameEditText.getText().toString());
        }
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    PossibleTargetsArrayList.clear();
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        String UserID = documentSnapshot.getId();
                        String UserName = documentSnapshot.get("UserName") + "";
                        String Points = documentSnapshot.get("UserPoints") + "";
                        if (UserID.equals(globalVars.getUserID()) || globalVars.getPendingAttacksHashMap().get(UserID) != null) {
                            continue;
                        }
                        if (Points.equals("null")) {
                            Points = "0";
                        }
                        PossibleTargetUserData target = new PossibleTargetUserData(UserID, UserName, Integer.parseInt(Points));
                        if (UserName.equals(textQuery)) {
                            PossibleTargetsArrayList.add(0, target);
                        } else {
                            PossibleTargetsArrayList.add(target);
                        }
                    }
                    if (PossibleTargetsArrayList.size() == 0) {
                        PossibleTargetsListRecyclerView.setVisibility(View.GONE);
                        NoItemTextView.setVisibility(View.VISIBLE);
                    } else {
                        Collections.shuffle(PossibleTargetsArrayList);
                        adapter.notifyDataSetChanged();
                        PossibleTargetsListRecyclerView.setVisibility(View.VISIBLE);
                        NoItemTextView.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /*
    attaching the listener to the list
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnScanFragmentInteractionListener) {
            mListener = (OnScanFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLogFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /*
    This is to attach the array to list shown with a customized row
     */

    public class CustomPossibleTargetsAdapter extends RecyclerView.Adapter<CustomPossibleTargetsAdapter.ViewHolder> {
        Context context;
        ArrayList<PossibleTargetUserData> arr;

        /*
        Initializing the adapter
         */
        public CustomPossibleTargetsAdapter(Context context, ArrayList<PossibleTargetUserData> arr) {
            this.context = context;
            this.arr = arr;
        }

        /*
        Attach the customized row
         */
        @NonNull
        @Override
        public CustomPossibleTargetsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row_single_text, parent, false);
            return new ViewHolder(view);
        }

        /*
        Attach the information to the row, and enable the onclick to bypass the target
         */

        @Override
        public void onBindViewHolder(@NonNull final CustomPossibleTargetsAdapter.ViewHolder holder, final int position) {
            holder.UserName.setText(arr.get(position).getUsername());
            holder.Level.setText("Level: " + arr.get(position).getLevel());
            holder.Level.setVisibility(View.VISIBLE);
            if (position % 2 == 0) {
                holder.view.setBackgroundColor(context.getColor(R.color.colorOffWhite));
            } else {
                holder.view.setBackgroundColor(Color.TRANSPARENT);
            }
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Initiating Attack on " + arr.get(position).getUsername(), Toast.LENGTH_SHORT).show();
                    final String TargetID = arr.get(position).getUserID();
                    view.setEnabled(false);
                    view.setBackgroundColor(Color.parseColor("#9E9E9E"));
                    globalVars.getUserDocument(TargetID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            holder.view.setEnabled(false);
                            HashMap<String, Object> attackHM = new HashMap<>();
                            long MyBypassLevel = globalVars.getMyToolsLevel().get("BypassLevel");
                            int TargetFirewallLevel = Integer.parseInt(task.getResult().get("UserTools.FirewallLevel") + "");

                            double TimeCalcInMinutes = 0;
                            if (MyBypassLevel >= TargetFirewallLevel) {
                                TimeCalcInMinutes = (MyBypassLevel - TargetFirewallLevel + 1) * 80;
                            } else {
                                TimeCalcInMinutes = (TargetFirewallLevel - MyBypassLevel + 1) * 140;
                            }

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy;MM;dd;HH;mm;ss", Locale.US);
                            sdf.setTimeZone(TimeZone.getTimeZone("EST"));
                            Date date = new Date();
                            String currentTime = sdf.format(date);
                            Calendar now = Calendar.getInstance();
                            now.add(Calendar.MINUTE, (int) TimeCalcInMinutes);
                            String FormulaTime = sdf.format(now.getTime());

                            attackHM.put("Started", currentTime);
                            attackHM.put("Finish", FormulaTime);
                            attackHM.put("ToolUsed", "Bypass");
                            attackHM.put("UserName", task.getResult().get("UserName") + "");
                            attackHM.put("ToolUsedLevel", MyBypassLevel);
                            attackHM.put("ID", TargetID);

                            globalVars.getUserDocument(globalVars.getUserID()).collection("PendingAttacks").document().set(attackHM, SetOptions.merge());
                            globalVars.getUserDocument(globalVars.getUserID()).update("AchievementsData.Scanned_Users", FieldValue.increment(1));

                            Toast.makeText(context, "Started Attacking " + arr.get(position).getUsername(), Toast.LENGTH_SHORT).show();
                            globalVars.updateLog("Started Attacking " + arr.get(position).getUsername(), globalVars.getUserID());
                        }
                    });
                }
            });
        }

        /*
        Return the size of the array
         */
        @Override
        public int getItemCount() {
            return arr.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView UserName, Level;
            public View view;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                UserName = view.findViewById(R.id.TextViewToShowSingleText);
                Level = view.findViewById(R.id.TextViewToShowUserLevel);
            }
        }
    }

    public interface OnScanFragmentInteractionListener {

    }
}
