package com.example.udharaplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ItemsOnDate extends AppCompatActivity {

    private AlertDialog.Builder alert;
    private CoordinatorLayout coordinatorLayout;
    private Integer recieved_amount = 0;
    private List<ConstructorDate> dateArraylist = new ArrayList<>();
    private Integer getTotal = 0, Credit_amount = 0, Left = 0;
    private DatabaseDates databaseDates;
    private Dialog dialog;
    private TextView Credit, Total_amount;
    private List<ConstructorItems> arraylist = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseItems databaseItems;
    private AdapterItems adapterItems;
    private LinearLayoutManager linearLayoutManager;
    private EditText ItemName, ProductAmount, Description, pop_credit;
    private TextView Left_Data, Received__Data;
    private String ItemNameString, ProductAmountString, DescriptionString, date, phone;
    private Button Done,button;
    private BottomSheetBehavior bottomSheetBehavior;
    private RelativeLayout bottomsheet;
    private Intent intent;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_on_date);
        floatingActionButton = findViewById(R.id.Addondate);
        bottomsheet = findViewById(R.id.bottomNavigationsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        recyclerView = findViewById(R.id.itemsRecycler);

        dialog = new Dialog(this);
        databaseItems = new DatabaseItems(this);
        databaseDates = new DatabaseDates(this);


        Left_Data = findViewById(R.id.left_data);
        Received__Data = findViewById(R.id.recieved_data);
        ItemName = findViewById(R.id.itemedittext);
        ProductAmount = findViewById(R.id.AmountEdit);
        Description = findViewById(R.id.DescriptionEdit);
        Credit = findViewById(R.id.credit_change);
        Done = findViewById(R.id.done);
        coordinatorLayout=findViewById(R.id.itemsondatelayout);
        Total_amount = findViewById(R.id.total_Amount);

        intent = getIntent();
        date = intent.getStringExtra("date");
        phone = intent.getStringExtra("phone");

        enableSwipe();

        //   arraylist.clear();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                } else {

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    ItemName.setText("");
                    ProductAmount.setText("0");
                    Description.setText("");


                }
            }
        });

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemNameString = ItemName.getText().toString().trim();
                ProductAmountString = ProductAmount.getText().toString().trim();
                DescriptionString = Description.getText().toString().trim();

                if (ItemNameString.isEmpty()) {
                    ItemName.setError("Item Name must be mentioned");
                    Toast.makeText(ItemsOnDate.this, "Item Name must be mentioned", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ProductAmountString.isEmpty()) {
                    ProductAmount.setError("Product amount must be filled");
                    Toast.makeText(ItemsOnDate.this, "Product amount must be filled", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    SaveToDataBase();
                }


            }
        });


//Credit Amount changes

        Credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.setContentView(R.layout.popup_credit_layout);
                button = dialog.findViewById(R.id.popup_done);
                pop_credit = dialog.findViewById(R.id.pop_credit_edit);



                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        recieved_amount = Integer.valueOf(pop_credit.getText().toString().trim());

                        if (recieved_amount > getTotal) {

                            recieved_amount=0;
                            Toast.makeText(ItemsOnDate.this, "Check Received Amount is greater than total", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            onStart();
                            dialog.dismiss();


                        }
                    }

                });


                dialog.show();

            }
        });


    }

    private void SaveToDataBase() {

        boolean insert = databaseItems.InsertDates(date, ItemNameString, phone, Integer.parseInt(ProductAmountString), DescriptionString);



        if (insert) {
            onStart();
            databaseDates.upDateTotal(date,getTotal);
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not Inserted", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();


        //Recycler Adapter

        ItemsTable();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterItems = new AdapterItems(this, arraylist);
        recyclerView.setAdapter(adapterItems);

        DatesTable();

        Left=getTotal-Credit_amount;
        databaseDates.UpdateLeft(date,Left);
        Received__Data.setText(""+Credit_amount);
        Left_Data.setText(""+Left);
        Total_amount.setText(""+getTotal);






    }






    public void ItemsTable()  {

        arraylist.clear();
        getTotal=0;
        Cursor cursor = databaseItems.getAllDataDate(date);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "nothing to show", Toast.LENGTH_SHORT).show();
            return;
        } else {

            while (cursor.moveToNext()) {

                ConstructorItems constructorItems = new ConstructorItems(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5)
                );
                arraylist.add(constructorItems);
            }


            for (ConstructorItems constructorItems:arraylist)
            {
                getTotal+=constructorItems.getAMOUNT();
            }

        }




    }

    public void DatesTable() {

        dateArraylist.clear();
        Cursor  cursor1=databaseDates.GetUnique(date);
        if (cursor1.getCount()==0){
            Toast.makeText(this, "Notthing to show", Toast.LENGTH_SHORT).show();
        }
        else{

            while (cursor1.moveToNext()){

                ConstructorDate constructorDate=new ConstructorDate(cursor1.getString(0),
                        cursor1.getString(1),
                        cursor1.getInt(2),
                        cursor1.getInt(3),
                        cursor1.getInt(4),
                        cursor1.getString(5));

                dateArraylist.add(constructorDate);
            }

          for (ConstructorDate constructorDate:dateArraylist){

              Credit_amount=recieved_amount+constructorDate.getRECIEVED();

          }

          databaseDates.upDateRecieve(date,Credit_amount);


        }



    }


    private void enableSwipe() {


        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final ConstructorItems item = (ConstructorItems) adapterItems.getData().get(position);


                final Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        databaseItems.InsertAfterDeleteItem(item.getID(),
                                item.getDATES(),
                                item.getITEM_NAME(),
                                item.getPHONE(),
                                item.getAMOUNT(),
                                item.getDESCRIPTION());
                        adapterItems.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                        onStart();

                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                adapterItems.removeItem(position);

                alert = new AlertDialog.Builder(ItemsOnDate.this);

                alert.setMessage("Do you want to delete?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Integer integer = databaseItems.deleteData(item.getID());
                        if (integer > 0) {
                            Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            onStart();
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
                        adapterItems.notifyDataSetChanged();
                        arraylist.add(position, item);
                        adapterItems.notifyDataSetChanged();
                    }
                }).show();


            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }


}