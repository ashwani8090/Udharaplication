package com.example.udharaplication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class ItemsTaken extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private RelativeLayout relativeLayout;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_taken);


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        relativeLayout=findViewById(R.id.bottomNavigationsheet);
        bottomSheetBehavior=BottomSheetBehavior.from(relativeLayout);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {


                    case R.id.additemtolist:
                        if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED)
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        else if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED)
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                    break;
                    case R.id.searchN:
                    break;

                }


                return true;
            }
        });

    }
}
