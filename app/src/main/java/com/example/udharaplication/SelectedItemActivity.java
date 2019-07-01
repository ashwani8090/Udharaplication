package com.example.udharaplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectedItemActivity extends AppCompatActivity {

    private   Integer amount=0;
    private static ColorStateList color = null;
    private TextView Assetname, Amount, Description,Amountwrite;
    private DatabaseItems databaseItems;
    private Intent intent;
    private Integer ID;
    private List<ConstructorItems> arraylist = new ArrayList<>();
    private ConstructorItems constructorItems;
    private Boolean c1 = false, c2 = false,c3=false;
    private Button DoneSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);

        Assetname = findViewById(R.id.Assetname);
        Amount = findViewById(R.id.AmountAsset);
        Description = findViewById(R.id.DescriptionEditAsset);
        Amountwrite=findViewById(R.id.Amountwrite);
        DoneSelected=findViewById(R.id.doneSelected);

        intent = getIntent();
        ID = intent.getIntExtra("ID", 0);
        databaseItems=new DatabaseItems(this);
        color=DoneSelected.getBackgroundTintList();



       Amountwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!c2) {

                    Amount.setFocusable(true);
                    Amount.setCursorVisible(true);
                    Amount.setFocusableInTouchMode(true);
                    Amount.setTextColor(Color.GRAY);
                    Amount.setHint("Change amount");
                    Amount.setText(""+0);
                    DoneSelected.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));

                    c2 = true;


                } else {
                    Amount.setFocusable(false);
                    Amount.setCursorVisible(false);
                    Amount.setFocusableInTouchMode(false);
                    Amount.setTextColor(color);
                    try {
                        Amount.setText(String.format("%s", arraylist.get(0).getAMOUNT().toString()));
                    }
                    catch (Exception e){

                    }
                    DoneSelected.setBackgroundTintList(color);

                    c2 = false;
                }

            }
        });


       DoneSelected.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               String value=Amount.getText().toString().trim();
               amount= Integer.valueOf(value);

               if (( ! value.isEmpty())&&(DoneSelected.getBackgroundTintList()==ColorStateList.valueOf(Color.BLUE)) )  {

                boolean check=   databaseItems.upDate(ID,amount);
                if (check){
                    onStart();
                    Toast.makeText(SelectedItemActivity.this, "Amount Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SelectedItemActivity.this, "try Again", Toast.LENGTH_SHORT).show();
                }


               }


           }
       });



    }

    @Override
    protected void onStart() {
        super.onStart();

        arraylist.clear();

        Cursor cursor=databaseItems.getUniqueAll(ID);
        while (cursor.moveToNext()){
           constructorItems=new ConstructorItems(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5)
            );
           arraylist.add(constructorItems);
        }



        try {
            Amount.setText(String.format("%s", arraylist.get(0).getAMOUNT().toString()));
            Assetname.setText(String.format("%s", arraylist.get(0).getITEM_NAME()));
            Description.setText(String.format("%s", arraylist.get(0).getDESCRIPTION()));

        }
        catch (Exception e){

            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
