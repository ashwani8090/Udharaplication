package com.example.udharaplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemsTaken extends AppCompatActivity {

    private DatabaseItems databaseItems2;
    private AlertDialog.Builder getAlert;
    private static long position = 0;
    private SharedPreferences sharedPreferences;
    private List<ConstructorDate> listlist = new ArrayList<>();
    private List<ConstructorItems> itemsList = new ArrayList<>();
    private DatabaseItems databaseItems;
    private List<String> stringList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private long Left = 0;
    private List<ConstructorDate> dateArraylist = new ArrayList<>();
    private AlertDialog.Builder alert;
    private CoordinatorLayout coordinatorLayout;
    private AdapterDates adapterDates;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<ConstructorDate> arraylist = new ArrayList<>();
    private Intent intent;
    private String phone;
    private DatabaseDates itemDatesPhone, databaseDates;
    private Boolean isSearch = false;
    private Integer PK;
    private AutoCompleteTextView Searching;
    private TextView TotalAmountLeft, DeleteAll;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_taken);


        //   arraylist.clear();

        databaseItems = new DatabaseItems(this);
        Searching = findViewById(R.id.searching);
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        TotalAmountLeft = findViewById(R.id.TotalAmountItem);
        recyclerView = findViewById(R.id.recyclerDated);
        DeleteAll = findViewById(R.id.deleteall);
        linearLayoutManager = new LinearLayoutManager(this);

        Searching.setVisibility(View.INVISIBLE);


        intent = getIntent();
        phone = intent.getStringExtra("PhoneNumber");
        PK=intent.getIntExtra("pk",0);

        databaseDates = itemDatesPhone = new DatabaseDates(this);
        databaseItems2=new DatabaseItems(this);



        enableSwipe();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {


                    case R.id.additemtolist:
                        String DateString = DateFormat.getDateTimeInstance().format(new Date());


                        /***************correct this problem*****************************************************/
                        boolean b = itemDatesPhone.insertDate(DateString, phone,PK);
                        onStart();
                        break;


                    case R.id.searchN:
                        if (!isSearch) {
                            Searching.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(ItemsTaken.this, R.anim.scaling_anime);
                            Searching.setAnimation(animation);
                            isSearch = true;
                        } else {
                            Searching.setVisibility(View.INVISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(ItemsTaken.this, R.anim.returnscaling_anim);
                            Searching.setAnimation(animation);

                            isSearch = false;
                        }

                        break;

                }


                return true;
            }
        });


        Searching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                FilterByDate(s.toString().toLowerCase().trim());


            }
        });


        //delete All code

        DeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              alert=new AlertDialog.Builder(ItemsTaken.this,R.style.Alert);
              alert.setIcon(R.drawable.ic_delete_black_24dp);

              alert.setTitle("Trash");
              alert.setMessage("Really want to delete all or swipe left to delete single date?");

              alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                     Integer integer= itemDatesPhone.DeleteAllofPhone(PK);
                       databaseItems2.DeleteAllofPhone(PK);


                     if (integer>0){
                         Toast.makeText(ItemsTaken.this, "Deleted", Toast.LENGTH_SHORT).show();

                         Vibrator vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                         vibrator.vibrate(200);
                         onStart();
                     }
                     else{
                         Toast.makeText(ItemsTaken.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                     }



                  }
              }).setNegativeButton("nope", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {

                      alert.setCancelable(true);
                  }
              });

              alert.show();

            }
        });


    }

    private void FilterByDate(String trim) {


        listlist.clear();

        for (ConstructorDate constructorItems : arraylist) {

            if (constructorItems.getDATE().contains(trim)) {

                listlist.add(constructorItems);
            }


        }

        adapterDates = new AdapterDates(this, listlist);
        recyclerView.setAdapter(adapterDates);

    }


    @Override
    protected void onStart() {
        super.onStart();

        arraylist.clear();
        try {

            stringList.clear();

        } catch (Exception e) {

        }
        Cursor cursor = itemDatesPhone.GetALlDate(PK);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "nothing to show", Toast.LENGTH_SHORT).show();

        } else {

            while (cursor.moveToNext()) {

                ConstructorDate constructorlList = new
                        ConstructorDate(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getInt(6));

                arraylist.add(constructorlList);


            }


        }

        adapterDates = new AdapterDates(this, arraylist);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterDates);


        DatesTable();
        TotalAmountLeft.setText(String.format("Rs. %d", Left));


        arrayAdapter = new ArrayAdapter<String>(ItemsTaken.this, android.R.layout.simple_list_item_1, stringList);
        Searching.setAdapter(arrayAdapter);

        sharedPreferences = getSharedPreferences("position", MODE_PRIVATE);
        position = sharedPreferences.getLong("position3", 0);

        linearLayoutManager.scrollToPositionWithOffset((int) position, (int) position);


    }


    private void enableSwipe() {


        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final ConstructorDate item = (ConstructorDate) adapterDates.getData().get(position);


                final Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        itemDatesPhone.InsertAfterDelete(item.getDATE(),
                                item.getPHONE(),
                                item.getRECIEVED(),
                                item.getLEFTP(),
                                item.getTOTAL(),
                                item.getPAID(),
                                item.getPK());
                        adapterDates.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                        onStart();

                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                adapterDates.removeItem(position);

                alert = new AlertDialog.Builder(ItemsTaken.this);

                alert.setMessage("Do you want to delete?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Integer integer = itemDatesPhone.DeleteDate(item.getDATE());
                        if (integer > 0) {
                            Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(200);
                            snackbar.show();
                            onStart();
                        } else {
                            Toast.makeText(mContext, "Not Deleted", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alert.setCancelable(true);
                        adapterDates.notifyDataSetChanged();
                        arraylist.add(position, item);
                        adapterDates.notifyDataSetChanged();
                    }
                }).show();


            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }


    public void DatesTable() {
        Cursor cursor1 = databaseDates.GetALlDate(PK);
        Left = 0;
        dateArraylist.clear();
        if (cursor1.getCount() == 0) {
            Left = 0;
            Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
            return;
        } else {

            while (cursor1.moveToNext()) {

                ConstructorDate constructorDate = new ConstructorDate(cursor1.getString(0),
                        cursor1.getString(1),
                        cursor1.getInt(2),
                        cursor1.getInt(3),
                        cursor1.getInt(4),
                        cursor1.getString(5),
                        cursor1.getInt(6));

                dateArraylist.add(constructorDate);
            }


            for (ConstructorDate date : dateArraylist) {
                Left += date.getLEFTP();
                stringList.add(date.getDATE());



            }

        }


    }


    public void ItemsTable() {

        itemsList.clear();

        Cursor cursor = databaseItems.GetAllwithPhone(PK);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "nothing to show", Toast.LENGTH_SHORT).show();
        } else {

            while (cursor.moveToNext()) {

                ConstructorItems constructorItems = new ConstructorItems(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getInt(6)
                );
                itemsList.add(constructorItems);
            }


            for (ConstructorItems constructorItems : itemsList) {


            }

        }


    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            stringList.clear();
        } catch (Exception b) {

        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

        sharedPreferences = getSharedPreferences("position", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong("position3", position).apply();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        position = 0;
        sharedPreferences = getSharedPreferences("position", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong("position3", position).apply();


    }
}
