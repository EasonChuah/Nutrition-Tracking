package com.stucial.trackfood;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    int code = 1;
    String table;

    private boolean exit = false;

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        table = intent.getStringExtra("tableName");
        code = intent.getIntExtra("code", 0);


        initViews();

        bottomNavigationView.setSelectedItemId(R.id.navTrack);
    }

    @Override
    public void onBackPressed() {

        if (exit) {
            super.onBackPressed();
        }

        exit = true;
        Toast.makeText(this, "Tap/Slide again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                exit = false;
            }
        }, 2000);
    }
}