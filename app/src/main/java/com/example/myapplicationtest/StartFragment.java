package com.example.myapplicationtest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StartFragment extends Fragment {

    private Button button_start, button_about, button_close;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_game_menu, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.button_start = view.findViewById(R.id.start_button);
        this.button_about = view.findViewById(R.id.about_button);

        View.OnClickListener click_button_start = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainGameFragment main_game_fragment = MainGameFragment.newInstance("test_value");
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


        button_start.setOnClickListener(click_button_start);
        button_about.setOnClickListener(click_button_about);

    }
}