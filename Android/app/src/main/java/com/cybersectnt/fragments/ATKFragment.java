package com.cybersectnt.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cybersectnt.activities.SplashScreenActivity;
import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.globalVars;
import com.cybersectnt.data.PendingAttackListData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.escape.ArrayBasedUnicodeEscaper;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class ATKFragment extends Fragment {

    private OnATKFragmentInteractionListener mListener;
    private RecyclerView PendingAttacksListRecyclerView;
    private TextView NoItemTextView;
    private CustomPendingAttacksAdapter adapter;
    Date nowToRemove;

    /*
        This to initialize the layout that will be used along with the recycler view to show the attacks
   */
    private void initVars() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        Collections.sort(globalVars.getPendingAttacksArrayList());
        adapter = new CustomPendingAttacksAdapter(getContext(), globalVars.getPendingAttacksArrayList());
        PendingAttacksListRecyclerView.setAdapter(adapter);
        PendingAttacksListRecyclerView.setLayoutManager(layoutManager);
        nowToRemove = new Date();
    }

    public ATKFragment() {
        // Required empty public constructor
    }

    /*
   This to initialize and start the view
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_atk, container, false);
        NoItemTextView = view.findViewById(R.id.TextViewToShowNoItems);
        PendingAttacksListRecyclerView = view.findViewById(R.id.RecyclerViewToViewPendingAndCompleteAttacks);
        PendingAttacksListRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        initVars();
        loadDB();
        return view;
    }

    /*
    This to show the item on the list of attacks
     */
    public void loadDB() {
        if (adapter == null) {
            return;
        }
        if (globalVars.getPendingAttacksArrayList().size() == 0) {
            PendingAttacksListRecyclerView.setVisibility(View.GONE);
            NoItemTextView.setVisibility(View.VISIBLE);
        } else {
            PendingAttacksListRecyclerView.setVisibility(View.VISIBLE);
            NoItemTextView.setVisibility(View.GONE);
        }
        Collections.sort(globalVars.getPendingAttacksArrayList());
        adapter.notifyDataSetChanged();
    }


    /**
     * This method is used To attach the fragment to the layout
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnATKFragmentInteractionListener) {
            mListener = (OnATKFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnATKFragmentInteractionListener");
        }
    }

    /*
      This method is used to detach the fragment from the layout
       */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnATKFragmentInteractionListener {

    }

    /**
     * This adapter is used to link the array of information to the list
     */
    public class CustomPendingAttacksAdapter extends RecyclerView.Adapter<CustomPendingAttacksAdapter.ViewHolder> {
        Context context;
        ArrayList<PendingAttackListData> arr;

        public CustomPendingAttacksAdapter(Context context, ArrayList<PendingAttackListData> arr) {
            this.context = context;
            this.arr = arr;
        }


        /**
         * This method is to inflate the list with a customized row and setup the long click listener for deleting
         *
         * @param parent
         * @param viewType
         * @return
         */
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row_pending_attack, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            if (globalVars.isLocal()) {
                holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        globalVars.getUserDocument(globalVars.getUserID()).collection("PendingAttacks").document(arr.get(holder.getAdapterPosition()).getDocumentID()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                globalVars.getPendingAttacksHashMap().remove(arr.get(holder.getAdapterPosition()).getUserID());
                                                arr.remove(holder.getAdapterPosition());
                                                loadDB();
                                            }
                                        });
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("").setPositiveButton("Delete", dialog)
                                .setNegativeButton("Cancel", dialog).show();

                        return false;
                    }
                });
            }
            return holder;
        }


        /**
         * This method is used to fill the proper information for every row in the list and calculate the time needed and to allow the
         * user to go the victim's device if the bypass was successful
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            if (position % 2 == 0) {
                holder.view.setBackgroundColor(context.getColor(R.color.colorOffWhite));
            } else {
                holder.view.setBackgroundColor(Color.TRANSPARENT);
            }
            if (holder.runnable != null) {
                holder.handler.removeCallbacks(holder.runnable);

            }
            if (arr.get(position).getState().equals("Success")) {
                holder.progressBar.setProgress(100);
                holder.Complete.setVisibility(View.VISIBLE);
                holder.Time.setVisibility(View.GONE);
                if (globalVars.isLocal() &&  arr.get(position).getToolUsed().equals("Bypass")) {
                    holder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(), SplashScreenActivity.class);
                            intent.putExtra("TargetID", arr.get(position).getUserID());
                            startActivity(intent);
                        }
                    });
                }
            } else {
                holder.Complete.setVisibility(View.INVISIBLE);
                holder.Time.setVisibility(View.VISIBLE);
            }

            final PendingAttackListData data = arr.get(position);
            holder.User.setText(data.getUserName() + " (" + data.getToolUsed() + ")");
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy;MM;dd;HH;mm;ss", Locale.US);
            sdf.setLenient(false);
            sdf.setTimeZone(TimeZone.getTimeZone("EST"));

            if (!arr.get(position).getState().equals("Success")) {
                holder.runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Date now = new Date();
                            now.setTime(nowToRemove.getTime() + 9000);
                            nowToRemove = now;
                            Date started = sdf.parse(data.getStartTime());
                            Date ends = sdf.parse(data.getEndTime());
                            long[] TimeDifference = TimeDifference(now, ends);

                            String[] strings = {"Year", "Month", "Week", "Day", "Hour", "Minute", "Second"};
                            String str = "";
                            for (int j = 0; j < TimeDifference.length; j++) {
                                if (TimeDifference[j] > 0) {
                                    str += TimeDifference[j] + " " + strings[j];
                                    if (TimeDifference[j] > 1) {
                                        str += "s";
                                    }
                                    if (TimeDifference[j] == 0) {
                                        str+= TimeDifference[j] + " " + strings[j];
                                    }
                                    str += " ";
                                }
                            }
                            holder.Time.setText(str);
                            holder.progressBar.setProgress((int) (100 * (1 - ((double) now.getTime() - ends.getTime()) / ((double) started.getTime() - ends.getTime()))));
                            if (holder.progressBar.getProgress() == 100) {
                                holder.Complete.setVisibility(View.VISIBLE);
                                holder.Time.setVisibility(View.GONE);
                                HashMap<String, Object> stateMap = new HashMap<>();
                                stateMap.put("State", "Success");
                                if (!arr.get(position).getState().equals("Success") && globalVars.isLocal()) {
                                    arr.get(position).setState("Success");
                                    globalVars.getPendingAttacksArrayList().get(position).setState("Success");
                                    globalVars.getUserDocument(globalVars.getUserID()).collection("PendingAttacks").document(arr.get(position).getDocumentID()).set(stateMap, SetOptions.merge());
                                    if (arr.get(position).getToolUsed().equals("Bypass")) {
                                        globalVars.getUserDocument(globalVars.getUserID()).update("AchievementsData.Exploited_Users", FieldValue.increment(1));
                                        globalVars.getUserDocument(globalVars.getUserID()).update("AchievementsData.Brute_Force_Used", FieldValue.increment(1));
                                        globalVars.updateLog(arr.get(position).getUserName() + " bypassed successfully", globalVars.getUserID());
                                        long points = globalVars.getMyToolsLevel().get("BypassLevel") * 20;
                                        globalVars.getUserDocument(globalVars.getUserID()).update("UserPoints", FieldValue.increment(points));
                                    } else if (arr.get(position).getToolUsed().equals("BruteForce")) {
                                        globalVars.getUserDocument(globalVars.getUserID()).update("AchievementsData.Bank_Access_Users", FieldValue.increment(1));
                                        globalVars.updateLog(arr.get(position).getUserName() + " Bank account is cracked successfully", globalVars.getUserID());
                                        long points = globalVars.getMyToolsLevel().get("BruteForceLevel") * 30;
                                        globalVars.getUserDocument(globalVars.getUserID()).update("UserPoints", FieldValue.increment(points));
                                    }
                                    loadDB();
                                }

                                holder.handler.removeCallbacks(this);
                                if (globalVars.isLocal()) {
                                    holder.view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(getContext(), SplashScreenActivity.class);
                                            intent.putExtra("TargetID", arr.get(position).getUserID());
                                            startActivity(intent);
                                        }
                                    });
                                }
                            } else {
                                holder.handler.postDelayed(this, 10);
                            }
                        } catch (ParseException e) {
                            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                };
                holder.handler.post(holder.runnable);

            }
        }

        /**
         * This method is used calculate the time difference
         *
         * @param now
         * @param ends
         * @return
         */
        private long[] TimeDifference(Date now, Date ends) {
            final long milliseconds = TimeUnit.MILLISECONDS.convert(ends.getTime() - now.getTime(), TimeUnit.MILLISECONDS);
            long dy = TimeUnit.MILLISECONDS.toDays(milliseconds);
            final long yr = dy / 365;
            dy %= 365;
            final long mn = dy / 30;
            dy %= 30;
            final long wk = dy / 7;
            dy %= 7;
            final long hr = TimeUnit.MILLISECONDS.toHours(milliseconds)
                    - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milliseconds));
            final long min = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
                    - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds));
            final long sec = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds));
            long[] array = {
                    yr, mn, wk, dy, hr, min, sec
            };
            return array;
        }

        /*
            Return the number of rows needed (number of attacks)
       */
        @Override
        public int getItemCount() {
            return arr.size();
        }

        /*
            linking the code with the layout of the row
       */
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView User, Time;
            ProgressBar progressBar;
            ImageView Complete;
            public View view;
            Runnable runnable;
            Handler handler;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                User = view.findViewById(R.id.TextViewToCurrentPage);
                Complete = view.findViewById(R.id.ImageViewToShowCompleted);
                Time = view.findViewById(R.id.TextViewToShowRemainingTime);
                progressBar = view.findViewById(R.id.ProgressBarToVisualizeTimeLeft);
                handler = new Handler();
            }
        }
    }

}
