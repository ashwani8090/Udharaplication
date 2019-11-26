package com.example.udharaplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    private Intent intentContactList;
    private Boolean isVerified = false;
    private ProgressBar progressBarSignup, progressBarLogin;
    private ImageView imageViewTick;
    private FirebaseUser firebaseAuth;
    private FirebaseAuth firebaseAuthAccount, firebaseAuthEmailVerify;
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView signup, back, forgetPassword;
    private ScrollView bottomsheet;
    private EditText Email, Password, Confirm, EmailLogin, PasswordLogin;
    private String EmailString, PasswordString, ConfirmString, EmailStringLogin, PasswordStringLogin;
    private Button SignUpButton, LoginButton;
    private RelativeLayout relativeLayout;
    private FirebaseUser getFirebaseAuthAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup = findViewById(R.id.signupbutton);
        bottomsheet = findViewById(R.id.design_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        back = findViewById(R.id.back);
        Email = findViewById(R.id.emailSignup);
        Password = findViewById(R.id.passwordSignup);
        Confirm = findViewById(R.id.confirmpassword);
        SignUpButton = findViewById(R.id.Signupfirebase);
        imageViewTick = findViewById(R.id.greentick);
        progressBarSignup = findViewById(R.id.progressBarSignup);
        LoginButton = findViewById(R.id.loginbutton);
        EmailLogin = findViewById(R.id.username);
        PasswordLogin = findViewById(R.id.password);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        forgetPassword = findViewById(R.id.forgetPassword);


        progressBarSignup.setVisibility(View.INVISIBLE);
        progressBarLogin.setVisibility(View.INVISIBLE);


        /**************  firebase Authentication by email and password  ************/

        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuthAccount = FirebaseAuth.getInstance();
        firebaseAuthEmailVerify = FirebaseAuth.getInstance();


        if (firebaseAuth != null) {
            if (firebaseAuth.isEmailVerified()) {
                intentContactList = new Intent(this, ContactList.class);
                intentContactList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentContactList);
                finishAndRemoveTask();
            }
        }

/*****************************************Bottom sheet **********************************/
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                }


            }
        });


        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {


                if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    Email.setText("");
                    Password.setText("");
                    Confirm.setText("");
                    progressBarSignup.setVisibility(View.INVISIBLE);
                    imageViewTick.setVisibility(View.INVISIBLE);
                }

                if (i == BottomSheetBehavior.STATE_DRAGGING) {
                    back.setVisibility(View.INVISIBLE);

                } else {
                    back.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });


        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isFilled();
            }
        });


/*******************************login and check email verified or not*********************************/
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isVerified();
            }
        });


        /***************************************************************************forget Password code*/

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmailLogin.getText().toString().trim().isEmpty() || PasswordLogin.getText().toString().trim().isEmpty()) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this, R.style.Alert);
                    alert.setTitle("Info");
                    alert.setIcon(R.drawable.ic_info_outline_black_24dp);
                    alert.setMessage("please fill entries").show();

                } else {
                    progressBarLogin.setVisibility(View.VISIBLE);

                    firebaseAuthEmailVerify.signInWithEmailAndPassword(EmailLogin.getText().toString().trim(), PasswordLogin.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {

                                firebaseAuthEmailVerify.sendPasswordResetEmail(EmailLogin.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        try {


                                            if (task.isSuccessful()) {


                                                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this, R.style.Alert);
                                                alert.setTitle("Info");
                                                alert.setIcon(R.drawable.ic_info_outline_black_24dp);
                                                alert.setMessage("Follow link sent to your email to change password").show();
                                                progressBarLogin.setVisibility(View.INVISIBLE);

                                            } else {

                                                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this, R.style.Alert);
                                                progressBarLogin.setVisibility(View.INVISIBLE);
                                                alert.setIcon(R.drawable.ic_info_outline_black_24dp);
                                                alert.setTitle("Info");
                                                alert.setMessage("" + Objects.requireNonNull(task.getException()).getMessage()).setTitle("Error").show();


                                            }
                                        }
                                        catch (Exception p){

                                        }
                                    }
                                });


                                firebaseAuthEmailVerify.signOut();

                            } else {

                                try {


                                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this, R.style.Alert);
                                    alert.setIcon(R.drawable.ic_info_outline_black_24dp);
                                    alert.setTitle("Info");
                                    alert.setMessage("Password is Already Correct").show();

                                    progressBarLogin.setVisibility(View.INVISIBLE);
                                }
                                catch (Exception p){

                                }
                            }

                        }
                    });


                }


            }
        });





    }

    private void isVerified() {

        EmailStringLogin = EmailLogin.getText().toString().trim();
        PasswordStringLogin = PasswordLogin.getText().toString().trim();


        if (EmailStringLogin.isEmpty()) {
            EmailLogin.setError("empty");
            return;
        } else if (PasswordStringLogin.isEmpty()) {
            PasswordLogin.setError("empty");
            return;
        } else {

            EmailStringLogin = EmailLogin.getText().toString().trim();
            PasswordStringLogin = PasswordLogin.getText().toString().trim();
            progressBarLogin.setVisibility(View.VISIBLE);

            firebaseAuthEmailVerify.signInWithEmailAndPassword(EmailStringLogin, PasswordStringLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()) {
                        isVerified = Objects.requireNonNull(firebaseAuthEmailVerify.getCurrentUser()).isEmailVerified();

                        if (isVerified) {

                            progressBarLogin.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(LoginActivity.this, ContactList.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();

                        } else {

                            try {

                                final AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this, R.style.Alert);
                                alert.setTitle("Verify link");
                                alert.setTitle("Info");
                                alert.setIcon(R.drawable.ic_info_outline_black_24dp);
                                alert.setMessage("First verify the link... sent to your Email!!" + "\n" + "Do you want to resend verification link? ").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        SendVerificationlink();

                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        alert.setCancelable(true);
                                    }
                                }).show();
                                progressBarLogin.setVisibility(View.INVISIBLE);
                            }
                            catch (Exception p){

                            }

                        }
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    try {


                        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this, R.style.Alert);
                        alert.setTitle("Info");
                        alert.setIcon(R.drawable.ic_info_outline_black_24dp);
                        alert.setMessage("" + e.getMessage()).show();
                        progressBarLogin.setVisibility(View.INVISIBLE);
                    }
                    catch (Exception p){

                    }

                }
            });
        }


    }

    private void isFilled() {

        EmailString = Email.getText().toString();
        PasswordString = Password.getText().toString().trim();
        ConfirmString = Confirm.getText().toString().trim();


        if (EmailString.isEmpty()) {
            Email.setError("Fill Email");
            return;
        } else if (PasswordString.isEmpty()) {
            Password.setError("Fill Password");
            return;
        } else if (PasswordString.length() < 6) {
            Password.setError("At least 6 character..");
            return;

        } else if (ConfirmString.isEmpty()) {
            Confirm.setError("Fill Password");
            return;
        } else if (!Objects.equals(ConfirmString, PasswordString)) {
            Confirm.setError("Password is not same");
            return;
        } else {
            LoginFirebase();

        }


    }

    private void LoginFirebase() {

        EmailString = Email.getText().toString();
        PasswordString = Password.getText().toString();

        progressBarSignup.setVisibility(View.VISIBLE);
        firebaseAuthAccount.createUserWithEmailAndPassword(EmailString, PasswordString).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                try {


                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this, R.style.Alert);
                    alert.setIcon(R.drawable.ic_error_outline_black_24dp);
                    alert.setTitle("Error");
                    alert.setMessage("" + e.getMessage()).show();
                    progressBarSignup.setVisibility(View.INVISIBLE);
                }
                catch (Exception p){

                }


            }
        }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {


                    firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();


                    firebaseAuth.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                imageViewTick.setVisibility(View.VISIBLE);
                                imageViewTick.setImageResource(R.drawable.green_tick);
                                progressBarSignup.setVisibility(View.INVISIBLE);
                                firebaseAuthAccount.signOut();
                                try {


                                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this, R.style.Alert);
                                    alert.setTitle("Info");
                                    alert.setIcon(R.drawable.ic_info_outline_black_24dp);
                                    alert.setMessage("Verification Link sent to your email").show();
                                }
                                catch (Exception p){

                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Recreate Account", Toast.LENGTH_SHORT).show();
                                progressBarSignup.setVisibility(View.INVISIBLE);
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            try {


                                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this, R.style.Alert);
                                alert.setIcon(R.drawable.ic_error_outline_black_24dp);
                                alert.setTitle("Error");
                                alert.setMessage("" + e.getMessage()).show();
                            }
                            catch (Exception p){

                            }
                        }
                    });
                }

            }
        });


    }

    public  void SendVerificationlink(){


        getFirebaseAuthAccount = FirebaseAuth.getInstance().getCurrentUser();
        assert getFirebaseAuthAccount != null;

        if (!getFirebaseAuthAccount.isEmailVerified()) {

            getFirebaseAuthAccount.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    try {


                        if (task.isSuccessful()) {

                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this, R.style.Alert);
                            alert.setTitle("Verify link");
                            alert.setIcon(R.drawable.ic_info_outline_black_24dp);
                            alert.setMessage("Successfully sent..").show();
                            progressBarLogin.setVisibility(View.INVISIBLE);

                        } else {

                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this, R.style.Alert);
                            alert.setTitle("Verify link");

                            alert.setIcon(R.drawable.ic_error_outline_black_24dp);
                            alert.setMessage("Something went wrong..").show();
                            progressBarLogin.setVisibility(View.INVISIBLE);

                        }
                    }
                    catch (Exception p){

                    }

                }
            });


        } else {
            try {


                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this, R.style.Alert);
                alert.setTitle("Verify link");

                alert.setIcon(R.drawable.ic_info_outline_black_24dp);
                alert.setMessage("Already verified").show();
                progressBarLogin.setVisibility(View.INVISIBLE);

            }
            catch (Exception l){

            }

        }



    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!isTimeAutomatic(this)){
            AlertDialog.Builder error = new AlertDialog.Builder(LoginActivity.this, R.style.Alert);
            error.setTitle("Error");
            error.setIcon(R.drawable.ic_error_outline_black_24dp);
            error.setMessage("Set Automatic time from setting...").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    onStart();

                }
            }).show();

        }
    }

    public static boolean isTimeAutomatic(Context c) {
        return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
    }


}
