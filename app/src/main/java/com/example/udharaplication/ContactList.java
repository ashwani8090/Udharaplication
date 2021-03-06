package com.example.udharaplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ContactList extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private static long position = 0;
    public ProgressBar progressBar;
    private BroadcastReceiver broadcastReceiver;
    private AlertDialog.Builder alert;
    private List<ContactConstructorlList> ContactList = new ArrayList<>();
    private List<ConstructorItems> itemsList = new ArrayList<>();
    private List<ConstructorDate> dateArraylist = new ArrayList<>();
    private DatabaseItems databaseItems;
    private DatabaseDates databaseDates;
    private DatabaseReference firebaseDatabaseContacts;
    private DatabaseReference firebaseDatabaseItems;
    private DatabaseReference firebaseDatabaseDates;
    private ImageView imageView;
    private TextView info;
    private SharedPreferences sharedPreferences;
    private PopupMenu popupMenu, popupMenu2;
    private List<String> stringList = new ArrayList<>();
    private List<ContactConstructorlList> listList;
    private ArrayAdapter<String> arrayAdapter;
    private Animation animation, animation2;
    private RelativeLayout Visible_layout;
    private TextView Search_icon, Upload_cloud;
    private AutoCompleteTextView Search_bar;
    private DatabaseItems datesPhone;
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
    private String PhoneString = "", NameString, DateString;
    private TextView downloadData;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);

        arrayList.clear();


        progressBar = findViewById(R.id.cloudprogress);
        Upload_cloud = findViewById(R.id.cloud);
        info = findViewById(R.id.info);
        Visible_layout = findViewById(R.id.searching_relative);
        Search_bar = findViewById(R.id.search_bar);
        Search_icon = findViewById(R.id.seach_icon);
        logout = findViewById(R.id.logoutButton);
        Name = findViewById(R.id.nameofcontact);
        PhoneNumber = findViewById(R.id.phoneNumber);
        addContactButton = findViewById(R.id.add);
        recyclerView = findViewById(R.id.recycler);
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        floatingActionButtonAddcontact = findViewById(R.id.floatingActionButtonAddcontact);
        linearLayoutBottomsheet = findViewById(R.id.addcontactBottomsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutBottomsheet);
        downloadData=findViewById(R.id.download_cloud);




        startService(new Intent(this, NotificationService.class));



        arrayList.clear();
        firebaseAuth = FirebaseAuth.getInstance();
        linearLayoutManager = new LinearLayoutManager(this);

        animation = AnimationUtils.loadAnimation(this, R.anim.scaling_anime);
        animation = AnimationUtils.loadAnimation(this, R.anim.serach_close);

        datesPhone = new DatabaseItems(this);
        dataBaseHelperClass = new DataBaseHelperClass(this);

        databaseDates = new DatabaseDates(this);
        databaseItems = new DatabaseItems(this);

        firebaseDatabaseContacts = FirebaseDatabase.getInstance().getReference("" + firebaseAuth.getUid()).child("CONTACTS");
        firebaseDatabaseDates = FirebaseDatabase.getInstance().getReference("" + firebaseAuth.getUid()).child("DATES");
        firebaseDatabaseItems = FirebaseDatabase.getInstance().getReference("" + firebaseAuth.getUid()).child("ITEMS");


        AlertDialog.Builder alert = new AlertDialog.Builder(com.example.udharaplication.ContactList.this, R.style.Alert);
        alert.setTitle("Info");
        alert.setIcon(R.drawable.ic_cloud_upload_black_24dp);
        alert.setMessage("Before closing application upload your data online by clicking cloud so that it can access from anywhere.").show();


        //database code

        try {
            stringList.clear();
        } catch (Exception e) {

        }



        //dowload data code is here
        downloadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadData();
            }
        });


        //popupcode

        logout.setVisibility(View.VISIBLE);

        Context wrapper = new ContextThemeWrapper(this, R.style.PopupMenu);
        popupMenu = new PopupMenu(wrapper, logout);
        popupMenu2 = new PopupMenu(wrapper, info);
        popupMenu2.inflate(R.menu.action_menu_hint);
        popupMenu.inflate(R.menu.action_online_logout);


        //Add contact from device

        Name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= Search_bar.getRight() - Search_bar.getTotalPaddingRight()) {


                        if (ContextCompat.checkSelfPermission(com.example.udharaplication.ContactList.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
                            }
                        }


                        Intent intent = new Intent(Intent.ACTION_PICK,
                                ContactsContract.Contacts.CONTENT_URI).addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                        startActivityForResult(intent, REQUEST_CODE);

                    }
                }


                return false;
            }
        });


/********************* profile pic selection code is here**********************************/


        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NameString = Name.getText().toString().trim();

                PhoneString = PhoneNumber.getText().toString().trim();

                String newPhone = "";
                for (int i = 0; i < PhoneString.length(); i++) {

                    if (PhoneString.charAt(i) != ' ')
                        newPhone = newPhone + PhoneString.charAt(i);

                }
                if (newPhone.length() == 10) {
                    newPhone = "+91" + newPhone;
                }
                PhoneString = newPhone.trim();
                DateString = DateFormat.getDateTimeInstance().format(new Date());


                for (ContactConstructorlList constructorlList : arrayList) {

                    if (constructorlList.getPhone().equals(PhoneString) && PhoneString.length() == 13) {
                        Toast.makeText(com.example.udharaplication.ContactList.this,
                                "Phone Number Already exist for " + constructorlList.getPhone(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                }


                if (NameString.isEmpty()) {
                    Toast.makeText(com.example.udharaplication.ContactList.this, "Name must be there", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((!PhoneString.isEmpty()) && (PhoneString.length() != 13)) {


                    Toast.makeText(com.example.udharaplication.ContactList.this, "Phone Number is badly formatted", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    String DateString = DateFormat.getDateTimeInstance().format(new Date());

                    NameString = Name.getText().toString().trim().substring(0, 1).toUpperCase() + Name.getText().toString().trim().substring(1);

                    boolean isInserted = dataBaseHelperClass.insertData(DateString, PhoneString, NameString, null);
                    if (isInserted) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            onStart();
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(200);
                            Name.setText("");
                            PhoneNumber.setText("");

                        }
                        Toast.makeText(com.example.udharaplication.ContactList.this, "Added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(com.example.udharaplication.ContactList.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        //Swipe to Delete Code
        enableSwipeToDeleteAndUndo();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();

            }
        });


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                if (R.id.logout_button == item.getItemId()) {

                    final AlertDialog.Builder alert = new AlertDialog.Builder(com.example.udharaplication.ContactList.this, R.style.Alert);
                    alert.setTitle("Warning");
                    alert.setIcon(R.drawable.ic_warning_black_24dp);
                    alert.setMessage("Logging out  will delete your data don't forget to backup your data to download it again!");
                    alert.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            dataBaseHelperClass.onUpgrade(dataBaseHelperClass.getWritableDatabase(), 1, 2);
                            databaseDates.onUpgrade(databaseDates.getWritableDatabase(), 1, 2);
                            databaseItems.onUpgrade(databaseItems.getReadableDatabase(), 1, 2);

                            startActivity(new Intent(com.example.udharaplication.ContactList.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            firebaseAuth.signOut();
                            finish();
                        }
                    }).setNegativeButton("nope", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            alert.setCancelable(true);


                        }
                    });

                    alert.show();

                    return true;


                }

                if (R.id.download == item.getItemId()) {
                    progressBar.setVisibility(View.VISIBLE);

                    DatesTable();
                    ItemsTable();
                    BackUpTheData();


                    OnlineMode();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        Handler handler = new Handler();
                        if (isNetworkConnected()) {
                            Toast.makeText(com.example.udharaplication.ContactList.this, "loading..", Toast.LENGTH_SHORT).show();
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onStart();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }, 7000);
                    }
                    return true;
                }


                return false;
            }
        });


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu2.show();

            }
        });


        /******************************************floating Button code is here*/

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

                if (i == BottomSheetBehavior.STATE_COLLAPSED) {
                    floatingActionButtonAddcontact.setImageResource(R.drawable.addcontact);

                }

                if (i == BottomSheetBehavior.STATE_EXPANDED) {
                    floatingActionButtonAddcontact.setImageResource(R.drawable.ic_close_black_24dp);

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


        //Search view


        Search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                animation = AnimationUtils.loadAnimation(com.example.udharaplication.ContactList.this, R.anim.scaling_anime);
                Visible_layout.setVisibility(View.VISIBLE);
                logout.setVisibility(View.INVISIBLE);
                info.setVisibility(View.INVISIBLE);
                Search_bar.setCursorVisible(true);
                Search_bar.setFocusableInTouchMode(true);
                Search_icon.setVisibility(View.INVISIBLE);
                Visible_layout.setAnimation(animation);
                Upload_cloud.setVisibility(View.INVISIBLE);
                downloadData.setVisibility(View.INVISIBLE);


            }
        });


        Search_bar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= Search_bar.getRight() - Search_bar.getTotalPaddingRight()) {

                        animation2 = AnimationUtils.loadAnimation(com.example.udharaplication.ContactList.this, R.anim.serach_close);
                        Visible_layout.setVisibility(View.INVISIBLE);
                        logout.setVisibility(View.VISIBLE);
                        Search_bar.setText("");
                        info.setVisibility(View.VISIBLE);
                        Search_bar.setCursorVisible(false);
                        Upload_cloud.setVisibility(View.VISIBLE);
                        Search_icon.setVisibility(View.VISIBLE);
                        downloadData.setVisibility(View.VISIBLE);
                        Visible_layout.setAnimation(animation2);
                        return true;
                    }
                }

                return false;
            }
        });


        Search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                FilterSearch(s.toString().toLowerCase());


            }
        });


        //Add contact


        // cloud th information
        Upload_cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressBar.setVisibility(View.VISIBLE);
                DatesTable();
                ItemsTable();
                BackUpTheData();
                Toast.makeText(com.example.udharaplication.ContactList.this, "backing up data..Requested to on internet", Toast.LENGTH_SHORT).show();


            }
        });


    }


    public void downloadData(){

        progressBar.setVisibility(View.VISIBLE);

        DatesTable();
        ItemsTable();
        BackUpTheData();


        OnlineMode();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Handler handler = new Handler();
            if (isNetworkConnected()) {
                Toast.makeText(com.example.udharaplication.ContactList.this, "loading..", Toast.LENGTH_SHORT).show();
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onStart();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, 7000);
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:

                if (resultCode == Activity.RESULT_OK) {

                    try {
                        int IDresultHolder, TempContactID;


                        Uri uri = data.getData();

                        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

                        if (cursor.moveToFirst()) {

                            Name.setText(String.format("%s", cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))));

                            IDresultHolder = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                            TempContactID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));


                            if (IDresultHolder == 1) {

                                Cursor cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                                        , null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + TempContactID, null, null);

                                while (cursor2.moveToNext()) {

                                    PhoneNumber.setText(String.format("%s", cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
                                }
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                            }

                        }
                    } catch (Exception e) {

                        Toast.makeText(this, "Change permission allow to read contacts", Toast.LENGTH_SHORT).show();
                    }

                }

                break;

        }


    }

    public void showSelectedNumber(int type, String number) {
        Toast.makeText(this, type + ": " + number, Toast.LENGTH_LONG).show();
    }


    private void FilterSearch(String search) {

        listList = new ArrayList<>();
        listList.clear();

        for (ContactConstructorlList constructorlList : arrayList) {

            if ((constructorlList.getName().toLowerCase().contains(search)) || (constructorlList.getPhone().toLowerCase().contains(search))) {
                listList.add(constructorlList);

            }
        }

        adapterContact = new AdapterContact(listList, this);
        recyclerView.setAdapter(adapterContact);


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

                        dataBaseHelperClass.insertData(item.getPk(), item.getPhone(), item.getName(), item.getUri());
                        adapterContact.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);

                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                adapterContact.removeItem(position);

                alert = new AlertDialog.Builder(com.example.udharaplication.ContactList.this);

                alert.setMessage("Do you want to delete?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Integer integer = dataBaseHelperClass.deleteData(item.getPk());
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


    @Override
    protected void onStart() {
        super.onStart();


        arrayList.clear();

        try {
            listList.clear();
            stringList.clear();
        } catch (Exception e) {

        }

        Cursor cursor = dataBaseHelperClass.getAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "nothing to show", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {

                ContactConstructorlList constructorlList = new
                        ContactConstructorlList(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
                arrayList.add(constructorlList);
            }
            for (ContactConstructorlList constructorlList : arrayList) {

                stringList.add(constructorlList.getPhone());
                stringList.add(constructorlList.getName());

            }

            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    arrayList.sort(new sortByName());
                }
            } catch (Exception e) {

            }

            linearLayoutManager = new LinearLayoutManager(this);
            adapterContact = new AdapterContact(arrayList, this);
            recyclerView.setAdapter(adapterContact);
            recyclerView.setLayoutManager(linearLayoutManager);

            arrayAdapter = new ArrayAdapter<String>(com.example.udharaplication.ContactList.this,
                    android.R.layout.simple_list_item_1, stringList);
            Search_bar.setAdapter(arrayAdapter);


            //animation on search bar

            Visible_layout.setVisibility(View.INVISIBLE);
            logout.setVisibility(View.VISIBLE);
            Search_icon.setVisibility(View.VISIBLE);

            sharedPreferences = getSharedPreferences("position", MODE_PRIVATE);
            position = sharedPreferences.getLong("position1", 0);

            linearLayoutManager.scrollToPositionWithOffset((int) position, (int) position);


        }
        broadcastReceiver = new InternetBroadcast(com.example.udharaplication.ContactList.this);
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, intentFilter);


    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            stringList.clear();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            logout.setVisibility(View.VISIBLE);
            info.setVisibility(View.VISIBLE);
            Upload_cloud.setVisibility(View.VISIBLE);
            downloadData.setVisibility(View.VISIBLE);


        } catch (Exception e) {

        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

        sharedPreferences = getSharedPreferences("position", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong("position1", position).apply();

        stopService(new Intent(this,NotificationService.class));



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        position = 0;
        sharedPreferences = getSharedPreferences("position", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong("position1", position).apply();


    }

    public void BackUpTheData() {
        if (!isNetworkConnected()) {
            AlertDialog.Builder error = new AlertDialog.Builder(com.example.udharaplication.ContactList.this, R.style.Alert);
            error.setTitle("Error");
            error.setIcon(R.drawable.ic_error_outline_black_24dp);

            error.setMessage("Check your internet connection");
            error.show();
            progressBar.setVisibility(View.INVISIBLE);

        } else {
            for (ContactConstructorlList constructorlList : arrayList) {


                firebaseDatabaseContacts.child("" + constructorlList.getPk()).
                        setValue(constructorlList).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()) {

                                    progressBar.setVisibility(View.INVISIBLE);

                                }


                            }
                        });


            }


            for (ConstructorDate constructorDate : dateArraylist) {

                firebaseDatabaseDates.child("" + constructorDate.getDATE()).setValue(constructorDate).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);

                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(com.example.udharaplication.ContactList.this, "Check internet", Toast.LENGTH_SHORT).show();


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                });


            }

            for (ConstructorItems constructorItems : itemsList) {

                firebaseDatabaseItems.child("" + constructorItems.getID()).
                        setValue(constructorItems).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(com.example.udharaplication.ContactList.this, " Uploading..", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        AlertDialog.Builder error = new AlertDialog.Builder(com.example.udharaplication.ContactList.this, R.style.Alert);
                        error.setTitle("Error");
                        error.setIcon(R.drawable.ic_error_outline_black_24dp);
                        error.setMessage("" + e.getMessage()).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });


            }

        }
    }


    // ONLINE THE DATA :

    public void ItemsTable() {

        itemsList.clear();
        Cursor cursor = databaseItems.AllDataFirabaseItems();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Nothing to take backup", Toast.LENGTH_SHORT).show();
            return;
        } else {

            while (cursor.moveToNext()) {

                ConstructorItems constructorItems = new ConstructorItems(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6));
                itemsList.add(constructorItems);
            }


        }


    }

    public void DatesTable() {

        dateArraylist.clear();
        Cursor cursor1 = databaseDates.AllDataFirabaseDates();
        if (cursor1.getCount() == 0) {
            Toast.makeText(this, "Nothing to take backup", Toast.LENGTH_SHORT).show();
        } else {

            while (cursor1.moveToNext()) {

                ConstructorDate constructorDate = new ConstructorDate(cursor1.getString(0),
                        cursor1.getString(1),
                        cursor1.getInt(2),
                        cursor1.getInt(3),
                        cursor1.getInt(4),
                        cursor1.getString(5),
                        cursor1.getString(6),
                        cursor1.getString(7));

                dateArraylist.add(constructorDate);
            }


        }


    }

    public void OnlineMode() {


        if (!isNetworkConnected()) {
            AlertDialog.Builder error = new AlertDialog.Builder(com.example.udharaplication.ContactList.this, R.style.Alert);
            error.setTitle("Error");
            error.setIcon(R.drawable.ic_error_outline_black_24dp);

            error.setMessage("Check your internet connection");

            error.show();
            progressBar.setVisibility(View.INVISIBLE);

        } else {


            ContactList.clear();
            itemsList.clear();
            dateArraylist.clear();
            firebaseDatabaseContacts.child("").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        ContactConstructorlList constructorlList = dataSnapshot1.getValue(ContactConstructorlList.class);

                        ContactList.add(constructorlList);

                    }


                    for (ContactConstructorlList constructorlList : ContactList) {

                        dataBaseHelperClass.InsertFirebase(
                                constructorlList.getPk(),
                                constructorlList.getPhone(),
                                constructorlList.getName(),
                                constructorlList.getUri());


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });


            firebaseDatabaseItems.child("").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        ConstructorItems constructorItems = dataSnapshot1.getValue(ConstructorItems.class);

                        itemsList.add(constructorItems);

                    }

                    for (ConstructorItems constructorlList : itemsList) {
                        databaseItems.InsertAfterDeleteItem(
                                constructorlList.getID(),
                                constructorlList.getDATES(),
                                constructorlList.getITEM_NAME(),
                                constructorlList.getPHONE(),
                                constructorlList.getAMOUNT(),
                                constructorlList.getDESCRIPTION(),
                                constructorlList.getPk()
                        );


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    progressBar.setVisibility(View.INVISIBLE);

                }
            });


            firebaseDatabaseDates.child("").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        ConstructorDate constructorDate = dataSnapshot1.getValue(ConstructorDate.class);

                        dateArraylist.add(constructorDate);

                    }

                    for (ConstructorDate constructorDate : dateArraylist) {
                        databaseDates.InsertAfterDelete(constructorDate.getDATE(),
                                constructorDate.getPHONE(),
                                constructorDate.getRECIEVED(),
                                constructorDate.getLEFTP(),
                                constructorDate.getTOTAL(),
                                constructorDate.getPAID(),
                                constructorDate.getPK(),
                                constructorDate.getTRANSACTIONS());


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    try {
                        AlertDialog.Builder error = new AlertDialog.Builder(com.example.udharaplication.ContactList.this, R.style.Alert);
                        error.setTitle("Error");
                        error.setIcon(R.drawable.ic_error_outline_black_24dp);
                        error.setMessage("" + databaseError.getMessage()).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    } catch (Exception e) {

                    }
                }
            });


        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public class sortByName implements Comparator<ContactConstructorlList> {


        @Override
        public int compare(ContactConstructorlList o1, ContactConstructorlList o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

}

