package com.cybersectnt.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.globalVars;
import com.cybersectnt.data.ToolsUpgradeListData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class ToolsFragment extends Fragment {
    View view;
    private OnToolsFragmentInteractionListener mListener;
    RecyclerView ToolsListRecyclerView;
    CustomToolsUpgradeAdapter adapter;
    ArrayList<ToolsUpgradeListData> ToolsUpgradeArrayList;

    public ToolsFragment() {
        // Required empty public constructor
    }

    /**
     * Initializing the view and starting it
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tools, container, false);
        LinkDesignWithCode();
        initVars();
        loadDB();
        return view;
    }

    /*
    Load the possible tools with their description and their initial prices
     */
    public void loadDB() {
        if (ToolsListRecyclerView == null) {
            return;
        }
        String[] Names = {"Anti Virus", "Brute Force", "Bypass", "Firewall", "Password", "Phishing", "Scanner", "Social Engineering", "Spyware"};
        String[] Descriptions = {"Antivirus software helps protect your computer against malware and cybercriminals",
                "Brute Force attack consists of an attacker submitting many passwords or passphrases with the hope of eventually guessing correctly",
                "Bypass attack is the action of performing activities on a computer system in an unauthorized manner",
                "Firewall is a network security system that monitors and controls incoming and outgoing network traffic based on predetermined security rules",
                "Password is a secret word or phrase that must be used to gain admission to a place",
                "Phishing is the fraudulent attempt to obtain sensitive information such as usernames, passwords and credit card details by disguising oneself as a trustworthy entity in an electronic communication.",
                "A network scanner is a software tool used for diagnostic and investigative purposes to find and categorize what devices are running on a network",
                "Social engineering, in the context of information security, is the psychological manipulation of people into performing actions or divulging confidential information.",
                "Spyware software that enables a user to obtain covert information about another's computer activities by transmitting data covertly from their hard drive."};
        int[] Prices = {50, 50, 50, 50, 50, 50, 50, 50, 50};
        int[] IconIDs = {R.drawable.antivirus_icon, R.drawable.brute_force_icon, R.drawable.bypass_icon, R.drawable.firewall_icon,
                R.drawable.password_icon, R.drawable.phishing_icon, R.drawable.scan_icon, R.drawable.social_engineering_icon,
                R.drawable.spyware_icon};
        HashMap<String, Long> ToolsLevels = globalVars.getMyToolsLevel();

        ToolsUpgradeArrayList = new ArrayList<>();
        for (int i = 0; i < Names.length; i++) {
            ToolsUpgradeListData data = new ToolsUpgradeListData();
            data.setName(Names[i]);
            data.setIconID(IconIDs[i]);
            data.setDescription(Descriptions[i]);
            data.setLevel(ToolsLevels.get(Names[i].replace(" ", "") + "Level"));
            data.setPrice(Prices[i]);
            ToolsUpgradeArrayList.add(data);
        }
        adapter = new CustomToolsUpgradeAdapter(getContext(), ToolsUpgradeArrayList);
        ToolsListRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /*
    Initializing the variables
     */
    private void initVars() {
        float width = 200 * getResources().getDisplayMetrics().density;
        int numberOfColumns = (int) (getResources().getDisplayMetrics().widthPixels / width);
        int remaining = (int) (getResources().getDisplayMetrics().widthPixels - width * numberOfColumns);
        if (remaining > width - 15)
            numberOfColumns++;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), numberOfColumns);

        ToolsListRecyclerView.setLayoutManager(gridLayoutManager);
        ToolsUpgradeArrayList = new ArrayList<>();

        ToolsListRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        ToolsListRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.HORIZONTAL));
    }

    /**
     * This method link the xml views with objects to allow us to control it.
     */
    private void LinkDesignWithCode() {
        ToolsListRecyclerView = view.findViewById(R.id.RecyclerViewToViewTools);
    }

    /**
     * Attaching the listener to the list
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnToolsFragmentInteractionListener) {
            mListener = (OnToolsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /*
    This method is used to detach
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*
    This method is used to resume and load the database if the application stopped
     */
    @Override
    public void onResume() {
        super.onResume();
        loadDB();
    }

    public interface OnToolsFragmentInteractionListener {

        void refreshToolsFragment();
    }

    /*
    Custom adapter
     */
    public class CustomToolsUpgradeAdapter extends RecyclerView.Adapter<CustomToolsUpgradeAdapter.ViewHolder> {
        public Context context;
        public ArrayList<ToolsUpgradeListData> arr;

        public CustomToolsUpgradeAdapter(Context context, ArrayList<ToolsUpgradeListData> arr) {
            this.context = context;
            this.arr = arr;
        }

        /**
         * Attaching a custom row to the list
         *
         * @param parent
         * @param viewType
         * @return
         */
        @NonNull
        @Override
        public CustomToolsUpgradeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.row_tools, parent, false);
            return new ViewHolder(view);
        }

        /**
         * Attaching the information to the list and enabling onClick for the items to show an alert dialog
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(@NonNull final CustomToolsUpgradeAdapter.ViewHolder holder, final int position) {

            String name = arr.get(position).getName();
            final String price = (arr.get(position).getPrice()) + "";
            long level = arr.get(position).getLevel();
            long iconID = arr.get(position).getIconID();
            holder.ToolName.setText(name);
            holder.ToolLevel.setText(level + "");
            Picasso.get().load((int) (iconID)).into(holder.ToolIcon);
            holder.UpgradeBtn.setText("Upgrade\n(" + price + ")");

            if (globalVars.getUserBankAccountPending() + globalVars.getUserBankAccountSecured() < arr.get(position).getPrice()) {
                holder.UpgradeBtn.setEnabled(false);
            }

            if (globalVars.isLocal()) {
                holder.UpgradeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        upgradeTool(position,holder.UpgradeBtn);
                    }
                });
            } else {
                holder.UpgradeBtn.setEnabled(false);
            }

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder aBuilder = new AlertDialog.Builder(getActivity());
                    View dialogView = getLayoutInflater().inflate(R.layout.popup_tool_description_and_upgrade, null);
                    aBuilder.setView(dialogView);
                    final AlertDialog alertDialog = aBuilder.create();
                    alertDialog.show();
                    final TextView ToolNameAndLevelTextView = dialogView.findViewById(R.id.TextViewToShowToolName);
                    TextView ToolDescriptionTextView = dialogView.findViewById(R.id.TextViewToShowToolDescription);
                    Button CloseDialogButton = dialogView.findViewById(R.id.ButtonForClosingThePopUp);
                    final Button UpgradeToolButton = dialogView.findViewById(R.id.ButtonForUpgradeTool);
                    ImageView ToolIconImageView = dialogView.findViewById(R.id.ImageViewToShowToolIcon);

                    ToolDescriptionTextView.setText(arr.get(position).getDescription());
                    ToolIconImageView.setImageResource((int) arr.get(position).getIconID());

                    ToolNameAndLevelTextView.setText(arr.get(position).getName() + " (" + holder.ToolLevel.getText().toString() + ")");
                    UpgradeToolButton.setText(holder.UpgradeBtn.getText().toString());

                    if (globalVars.isLocal()) {
                        UpgradeToolButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                upgradeTool(position, UpgradeToolButton);
                                alertDialog.cancel();
                            }
                        });
                    } else {
                        UpgradeToolButton.setEnabled(false);
                    }
                    CloseDialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.cancel();
                        }
                    });
                }
            });
        }


        /**
         * This method allows the user to upgrade their tool
         *
         * @param position
         */
        private void upgradeTool(int position, View view) {
            view.setEnabled(false);
            if (globalVars.getUserBankAccountPending() >= arr.get(position).getPrice()) {
                globalVars.getUserDocument(globalVars.getUserID()).update("BankAccount.Pending", FieldValue.increment(-1 * arr.get(position).getPrice())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        globalVars.setUserBankAccountPending(globalVars.getUserBankAccountPending() + (-1 * globalVars.getUserBankAccountPending()));
                    }
                });
            } else if (globalVars.getUserBankAccountPending() + globalVars.getUserBankAccountSecured() >= arr.get(position).getPrice()) {
                final int remaining = (int) arr.get(position).getPrice() - globalVars.getUserBankAccountPending();
                globalVars.getUserDocument(globalVars.getUserID()).update("BankAccount.Pending", FieldValue.increment(-1 * globalVars.getUserBankAccountPending())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        globalVars.setUserBankAccountPending(globalVars.getUserBankAccountPending() + (-1 * globalVars.getUserBankAccountPending()));
                    }
                });
                globalVars.getUserDocument(globalVars.getUserID()).update("BankAccount.Secured", FieldValue.increment(-1 * remaining)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        globalVars.setUserBankAccountSecured(globalVars.getUserBankAccountSecured() + (-1 * remaining));
                    }
                });
            } else {
                return;
            }
            globalVars.updateLog(arr.get(position).getName() + ": " + (arr.get(position).getLevel() + 1), globalVars.getUserID());
            globalVars.getUserDocument(globalVars.getUserID()).update("UserTools." + arr.get(position).getName().replace(" ", "") + "Level", FieldValue.increment(1));
            HashMap<String, Long> toolsLevel = globalVars.getMyToolsLevel();
            String str = arr.get(position).getName().replace(" ", "") + "Level";
            toolsLevel.put(str, toolsLevel.get(str) + 1);
            globalVars.setMyToolsLevel(toolsLevel);

        }

        @Override
        public int getItemCount() {
            return arr.size();
        }

        /*
        This class is the recycler view used to display the tools
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView ToolName, ToolLevel;
            public ImageView ToolIcon;
            public Button UpgradeBtn;
            public View view;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                ToolName = view.findViewById(R.id.TextViewToToolName);
                ToolLevel = view.findViewById(R.id.TextViewToToolLevel);
                ToolIcon = view.findViewById(R.id.ImageViewToShowToolIcon);
                UpgradeBtn = view.findViewById(R.id.ButtonForUpgradeTool);
            }
        }
    }


}
