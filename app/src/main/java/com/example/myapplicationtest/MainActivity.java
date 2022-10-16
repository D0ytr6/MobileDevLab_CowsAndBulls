package com.example.myapplicationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // First start
        if(savedInstanceState == null){
            StartFragment startFragment = new StartFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, startFragment).commit();
        }

    }

}