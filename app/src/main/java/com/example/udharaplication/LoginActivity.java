package com.example.udharaplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    private RelativeLayout bottomsheet;
    private EditText Email, Password, Confirm, EmailLogin, PasswordLogin;
    private String EmailString, PasswordString, ConfirmString, EmailStringLogin, PasswordStringLogin;
    private Button SignUpButton, LoginButton;
    private RelativeLayout relativeLayout;

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
            intentContactList = new Intent(this, contactlist.class);
            intentContactList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentContactList);
            finishAndRemoveTask();
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

                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
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
                                        if (task.isSuccessful()) {


                                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                                            alert.setMessage("Password Succesfully sent to your email").show();

                                            progressBarLogin.setVisibility(View.INVISIBLE);

                                        } else {

                                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                                            alert.setMessage("" + Objects.requireNonNull(task.getException()).getMessage()).setTitle("Error").show();
                                            progressBarLogin.setVisibility(View.INVISIBLE);


                                        }
                                    }
                                });


                                firebaseAuthEmailVerify.signOut();

                            } else {

                                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                                alert.setMessage("Password is Already Correct").show();
                                progressBarLogin.setVisibility(View.INVISIBLE);
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
                            startActivity(new Intent(LoginActivity.this, contactlist.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                        } else {

                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                            alert.setMessage("Verify link sent to your Email").show();
                            progressBarLogin.setVisibility(View.INVISIBLE);


                        }
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setMessage("" + e.getMessage()).show();
                    progressBarLogin.setVisibility(View.INVISIBLE);

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
        firebaseAuthAccount.createUserWithEmailAndPassword(EmailString, PasswordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                                alert.setMessage("Verification Link sent to your email").show();

                            } else {
                                Toast.makeText(LoginActivity.this, "Recreate Account", Toast.LENGTH_SHORT).show();
                                progressBarSignup.setVisibility(View.INVISIBLE);
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                            alert.setMessage("" + e.getMessage()).show();
                        }
                    });
                }

            }
        });


    }
}