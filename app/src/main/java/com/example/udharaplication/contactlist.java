package com.example.udharaplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class contactlist extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterContact adapterContact;
    private CoordinatorLayout coordinatorLayout;
    private LinearLayoutManager linearLayoutManager;
    private List<ContactConstructorlList> arrayList = new ArrayList<>();
    private DataBaseHelperClass dataBaseHelperClass;
    private FirebaseAuth firebaseAuth;
    private Button logout, addContactButton;
    private LinearLayout linearLayoutBottomsheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private FloatingActionButton floatingActionButtonAddcontact;
    private EditText Name, PhoneNumber;
    private String PhoneString, NameString, DateString;
    private AlertDialog.Builder alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);

        logout = findViewById(R.id.logoutButton);
        Name = findViewById(R.id.nameofcontact);
        PhoneNumber = findViewById(R.id.phoneNumber);
        addContactButton = findViewById(R.id.add);
        recyclerView = findViewById(R.id.recycler);
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        floatingActionButtonAddcontact = findViewById(R.id.floatingActionButtonAddcontact);
        linearLayoutBottomsheet = findViewById(R.id.addcontactBottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutBottomsheet);

        arrayList.clear();
        firebaseAuth = FirebaseAuth.getInstance();
        linearLayoutManager = new LinearLayoutManager(this);


        dataBaseHelperClass = new DataBaseHelperClass(this);

        //database code


        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NameString=Name.getText().toString().trim();
                PhoneString = PhoneNumber.getText().toString().trim();
                DateString = DateFormat.getDateTimeInstance().format(new Date());

                if (NameString.isEmpty()){
                    Toast.makeText(contactlist.this, "Name must be there", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (PhoneString.length() < 10) {
                    Toast.makeText(contactlist.this, "Phone Number is badly formatted", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    NameString = Name.getText().toString().trim().substring(0, 1).toUpperCase() + Name.getText().toString().trim().substring(1);

                    boolean isInserted = dataBaseHelperClass.insertData(PhoneString, DateString, NameString);
                    if (isInserted) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            onStart();
                        }
                        Toast.makeText(contactlist.this, "Added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(contactlist.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        //SwipetoDelete Code
        enableSwipeToDeleteAndUndo();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(contactlist.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                firebaseAuth.signOut();

            }
        });


        /******************************************floating Button code is here*/

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

                if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    Name.setText("");
                    PhoneNumber.setText("");
                }


            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        floatingActionButtonAddcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Name.setText("");
                    PhoneNumber.setText("");

                }
            }
        });


        /******************************************adding Contact***************************************/


    }

    private void enableSwipeToDeleteAndUndo() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final ContactConstructorlList item = (ContactConstructorlList) adapterContact.getData().get(position);


                final Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dataBaseHelperClass.insertData(item.getPhone(), item.getDate(), item.getName());
                        adapterContact.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);

                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                adapterContact.removeItem(position);

                alert = new AlertDialog.Builder(contactlist.this);

                alert.setMessage("Do you want to delete?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Integer integer = dataBaseHelperClass.deleteData(item.getPhone());
                        if (integer > 0) {
                            Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(200);
                            snackbar.show();
                        } else {
                            Toast.makeText(mContext, "Not Deleted", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alert.setCancelable(true);
                        adapterContact.notifyDataSetChanged();
                        arrayList.add(position, item);
                        adapterContact.notifyDataSetChanged();
                    }
                }).show();


            }
        };


        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();

        arrayList.clear();

        Cursor cursor = dataBaseHelperClass.getAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "nothing to show", Toast.LENGTH_SHORT).show();
            return;
        } else {

            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext()) {

                ContactConstructorlList constructorlList = new ContactConstructorlList(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                arrayList.add(constructorlList);


            }


            arrayList.sort(new sortByName());

            linearLayoutManager = new LinearLayoutManager(this);
            adapterContact = new AdapterContact(arrayList, this);
            recyclerView.setAdapter(adapterContact);
            recyclerView.setLayoutManager(linearLayoutManager);


        }
    }


    public  class sortByName implements Comparator<ContactConstructorlList> {


        @Override
        public int compare(ContactConstructorlList o1, ContactConstructorlList o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

}
