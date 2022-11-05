package com.example.myapplicationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_start_window);
        Button button_start = findViewById(R.id.start_btn);
//        button_about = view.findViewById(R.id.about_button);
//        button_close = view.findViewById(R.id.close_button);


    }

}