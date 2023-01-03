package com.example.myapplicationtest;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class StartFragment extends Fragment {

    private Button button_start, button_about, button_close;

    private String[] difficulties = {"Low", "Medium", "High"};
    private String game_difficult = "Low";
    private LayoutInflater inflater;
    private  ViewGroup container;

    AutoCompleteTextView drop_menu;
    ArrayAdapter<String> menu_items_adapter;


    public View InitializeUserInterface(){
        View view;
        int orientation = getActivity().getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            view = inflater.inflate(R.layout.fragment_start_game_menu, container, false);
        }
        else{
            view = inflater.inflate(R.layout.fragment_start_game_menu_horizontal, container, false);
        }
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;

        if(savedInstanceState == null){
        }else {
            String Difficult = savedInstanceState.getString("dif");
            game_difficult = Difficult;
        }
        return InitializeUserInterface();
    }

    @Override
    public void onResume() {
        super.onResume();
        menu_items_adapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_item, difficulties);
        this.drop_menu.setAdapter(menu_items_adapter);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("dif", game_difficult);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.button_start = view.findViewById(R.id.start_button);
        this.button_about = view.findViewById(R.id.about_button);
        this.button_close = view.findViewById(R.id.close_button);
        this.drop_menu = view.findViewById(R.id.dropdown_menu_modes);
        menu_items_adapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_item, difficulties);
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
                MainGameFragment main_game_fragment = MainGameFragment.newInstance(game_difficult);
                getParentFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, main_game_fragment).commit();
            }
        };

        View.OnClickListener click_button_about = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutFragment fragment = AboutFragment.newInstance("test_value");
                getParentFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit();
            }

        };

        View.OnClickListener click_button_close = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Closing application...",   Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

        };

        button_start.setOnClickListener(click_button_start);
        button_about.setOnClickListener(click_button_about);
        button_close.setOnClickListener(click_button_close);

    }
}