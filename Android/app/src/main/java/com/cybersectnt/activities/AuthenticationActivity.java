package com.cybersectnt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.globalVars;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// This Activity is for logging-in and Registering

public class AuthenticationActivity extends AppCompatActivity {
    TextInputLayout UsernameOrEmailEditText, EmailEditText, PasswordEditText, ConfirmPasswordEditText;
    TextView CurrentStateTextView, ForgetPasswordButton, SwitchCurrentStateButton;
    Button LoginOrRegisterButton;
    private boolean isLogin = true;
    ProgressDialog progressDialog;

    /**
     * Setup and initiate the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        getSupportActionBar().hide();
        LinkDesignWithCode();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }

    /**
     * This method link the xml views with objects to allow us to control it.
     */
    private void LinkDesignWithCode() {
        UsernameOrEmailEditText = findViewById(R.id.EditTextForEmailOrUsername);
        EmailEditText = findViewById(R.id.EditTextForEmail);
        PasswordEditText = findViewById(R.id.EditTextForPassword);
        ConfirmPasswordEditText = findViewById(R.id.EditTextForConfirmPassword);
        ForgetPasswordButton = findViewById(R.id.ButtonForForgetPassword);
        LoginOrRegisterButton = findViewById(R.id.ButtonForLoginOrRegister);
        SwitchCurrentStateButton = findViewById(R.id.ButtonForSwitchingBetweenLoginOrRegister);
        CurrentStateTextView = findViewById(R.id.TextViewToShowCurrentState);
    }

    /**
     * This method will Send a reset password link to the user
     *
     * @param view XML TextView as a button
     * @input Email
     */
    public void ForgetMyPassword(View view) {
        String Email = globalVars.getTextInputLayoutString(UsernameOrEmailEditText);
        globalVars.getmAuth().sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AuthenticationActivity.this, "Email has been sent!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * This method will control current view if it's a login or register
     *
     * @param view XML Button
     */
    public void SwitchBetweenLoginAndRegister(View view) {
        if (isLogin) {
            //Do Register
            UsernameOrEmailEditText.setHint("Username");
            LoginOrRegisterButton.setText("Register");
            CurrentStateTextView.setText("Register");
            SwitchCurrentStateButton.setText("Already have an account?");
            EmailEditText.setVisibility(View.VISIBLE);
            ConfirmPasswordEditText.setVisibility(View.VISIBLE);
            ForgetPasswordButton.setVisibility(View.GONE);

        } else {
            //Do Login
            UsernameOrEmailEditText.setHint("Email/Username");
            LoginOrRegisterButton.setText("Login");
            CurrentStateTextView.setText("Login");
            SwitchCurrentStateButton.setText("New user?");
            EmailEditText.setVisibility(View.GONE);
            ConfirmPasswordEditText.setVisibility(View.GONE);
            ForgetPasswordButton.setVisibility(View.VISIBLE);

        }
        isLogin = !isLogin;
    }

    /**
     * This method will allow the user to login or register to the app
     * if Login:
     * @param view XML Button
     * @input Email/Username and password
     * if Register:
     * @input Display Name, Email, Password, Confirm Password
     */
    public void LoginOrRegister(View view) {
        if (globalVars.isEmptyTextInputLayout(UsernameOrEmailEditText) || globalVars.isEmptyTextInputLayout(PasswordEditText)) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isLogin) {
            progressDialog.setTitle("Signing in...");
            progressDialog.show();
            final String Email = globalVars.getTextInputLayoutString(UsernameOrEmailEditText);
            final String Password = globalVars.getTextInputLayoutString(PasswordEditText);

            if (Password.length() < 7) {
                Toast.makeText(this, "Password length should be 8 and above", Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
                return;
            }


            globalVars.getmAuth().signInWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    onSuccessMove();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    globalVars.getUsersCollection().whereEqualTo("UserName", Email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.size() != 0) {
                                String Email = queryDocumentSnapshots.getDocuments().get(0).get("Email") + "";
                                globalVars.getmAuth().signInWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        onSuccessMove();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AuthenticationActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(AuthenticationActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.cancel();
                        }
                    });
                }
            });
        } else {
            progressDialog.setTitle("Signing Up...");
            if (globalVars.isEmptyTextInputLayout(EmailEditText) || globalVars.isEmptyTextInputLayout(ConfirmPasswordEditText)) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.show();
            final String Email = globalVars.getTextInputLayoutString(EmailEditText);
            final String Password = globalVars.getTextInputLayoutString(PasswordEditText);
            String ConfirmPassword = globalVars.getTextInputLayoutString(ConfirmPasswordEditText);
            if (!Password.equals(ConfirmPassword)) {
                Toast.makeText(this, "Passwords does not match", Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
                return;
            } else if (Password.length() < 7) {
                Toast.makeText(this, "Password length should be 8 and above", Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
                return;
            } else if (!isEmailValid(Email)) {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
                return;
            }
            globalVars.getUsersCollection().whereEqualTo("UserName", globalVars.getTextInputLayoutString(UsernameOrEmailEditText)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (queryDocumentSnapshots.size() == 0) {
                        globalVars.getmAuth().createUserWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                HashMap<String, Object> tools = new HashMap<>();
                                tools.put("PasswordLevel", 1);
                                tools.put("BruteForceLevel", 1);
                                tools.put("ScannerLevel", 1);
                                tools.put("SpywareLevel", 1);
                                tools.put("AntiVirusLevel", 1);
                                tools.put("FirewallLevel", 1);
                                tools.put("SocialEngineeringLevel", 1);
                                tools.put("PhishingLevel", 1);
                                tools.put("BypassLevel", 1);
                                HashMap<String, Object> bank = new HashMap<>();
                                bank.put("Pending", 0);
                                bank.put("Secured", 0);
                                final HashMap<String, Object> information = new HashMap<>();
                                information.put("UserName", globalVars.getTextInputLayoutString(UsernameOrEmailEditText));
                                information.put("Email", globalVars.getTextInputLayoutString(EmailEditText));
                                information.put("UserPoints", 0);
                                information.put("UserTools", tools);
                                information.put("BankAccount", bank);
                                information.put("Log", "");
                                globalVars.getUserDocument(globalVars.getUserID()).set(information);
                                onSuccessMove();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(AuthenticationActivity.this, "Failed to register, username already taken", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        progressDialog.cancel();
                        Toast.makeText(AuthenticationActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    /**
     * This method allows the user to move to the splash screen
     */
    private void onSuccessMove() {
        Intent intent = new Intent(getBaseContext(), SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        progressDialog.cancel();
    }

    /**
     * This method will check if the email pattern is valid or not
     * @param email to be checked
     * @return boolean valid or not valid
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
