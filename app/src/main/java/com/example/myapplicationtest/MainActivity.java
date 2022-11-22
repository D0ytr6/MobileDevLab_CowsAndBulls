package com.example.myapplicationtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplicationtest.activities.AboutAuthorActivity;
import com.example.myapplicationtest.activities.MainGameActivity;

public class MainActivity extends AppCompatActivity {

    private Button button_start, button_about, button_close;

    private String[] difficulties = {"Low", "Medium", "High"};
    private String game_difficult = "Low";
    AutoCompleteTextView drop_menu;
    ArrayAdapter<String> menu_items_adapter;

    @Override
    protected void onResume() {
        super.onResume();
        menu_items_adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.dropdown_item, difficulties);
        this.drop_menu.setAdapter(menu_items_adapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("dif", game_difficult);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.fragment_start_game_menu);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.fragment_start_game_menu_horizontal);
        }

        if(savedInstanceState == null){
        }else {
            String Difficult = savedInstanceState.getString("dif");
            game_difficult = Difficult;
        }

        button_start = findViewById(R.id.start_btn);
        button_about = findViewById(R.id.about_btn);
        button_close = findViewById(R.id.close_btn);

        drop_menu = findViewById(R.id.dropdown_menu_modes);
        menu_items_adapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, difficulties);
        drop_menu.setAdapter(menu_items_adapter);

        drop_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(drop_menu.getText().toString().equals("Low")){
                    game_difficult = "Low";
                }
                if(drop_menu.getText().toString().equals("Medium")){
                    game_difficult = "Medium";
                }
                if(drop_menu.getText().toString().equals("High")){
                    game_difficult = "High";
                }
            }
        });

        View.OnClickListener click_button_start = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainGameActivity.class);
                intent.putExtra("difficult", game_difficult);
                startActivity(intent);
            }
        };

        View.OnClickListener click_button_about = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutAuthorActivity.class);
                startActivity(intent);
            }

        };

        View.OnClickListener click_button_close = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Closing application...",   Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }

        };

        button_start.setOnClickListener(click_button_start);
        button_about.setOnClickListener(click_button_about);
        button_close.setOnClickListener(click_button_close);

    }

}