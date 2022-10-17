package com.example.myapplicationtest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainGameFragment extends Fragment {

    private Button submit_button, del_button, bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;
    private static final String ARG_PARAM = "VALUE";
    private List <Button> number_buttons;

    public static MainGameFragment newInstance(String param1) {
        MainGameFragment fragment = new MainGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_window, container, false);
    }

    public void CheckText(EditText input_view, String number){
        if(input_view.getText() != null){
            String text = input_view.getText().toString();
            if(text.length() != 4){
                input_view.append(number);
            }
        }
        else{
            input_view.append(number);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.submit_button = view.findViewById(R.id.button_submit);
        this.del_button = view.findViewById(R.id.button_del);
        this.bt0 = view.findViewById(R.id.button_0);
        this.bt1 = view.findViewById(R.id.button_1);
        this.bt2 = view.findViewById(R.id.button_2);
        this.bt3 = view.findViewById(R.id.button_3);
        this.bt4 = view.findViewById(R.id.button_4);
        this.bt5 = view.findViewById(R.id.button_5);
        this.bt6 = view.findViewById(R.id.button_6);
        this.bt7 = view.findViewById(R.id.button_7);
        this.bt8 = view.findViewById(R.id.button_8);
        this.bt9 = view.findViewById(R.id.button_9);

        View.OnClickListener click_button_submit= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input_view = view.findViewById(R.id.pressed_view);
                input_view.setText("");
            }
        };

        View.OnClickListener click_button_del= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input_view = view.findViewById(R.id.pressed_view);
                if(input_view.getText().toString() == ""){
                    String text = input_view.getText().toString();
                    text = text.substring(0, (text.length() - 1));
                    input_view.setText(text);
                }
            }
        };

        View.OnClickListener click_numbers = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input_view = view.findViewById(R.id.pressed_view);
                switch (v.getId()){
                    case R.id.button_0:
                        CheckText(input_view, "0"); break;

                    case R.id.button_1:
                        CheckText(input_view, "1"); break;

                    case R.id.button_2:
                        CheckText(input_view, "2"); break;

                    case R.id.button_3:
                        CheckText(input_view, "3"); break;

                    case R.id.button_4:
                        CheckText(input_view, "4"); break;

                    case R.id.button_5:
                        CheckText(input_view, "5"); break;

                    case R.id.button_6:
                        CheckText(input_view, "6"); break;

                    case R.id.button_7:
                        CheckText(input_view, "7"); break;

                    case R.id.button_8:
                        CheckText(input_view, "8"); break;

                    case R.id.button_9:
                        CheckText(input_view, "9"); break;
                }

            }
        };

        submit_button.setOnClickListener(click_button_submit);
        del_button.setOnClickListener(click_button_del);
        bt0.setOnClickListener(click_numbers);
        bt1.setOnClickListener(click_numbers);
        bt2.setOnClickListener(click_numbers);
        bt3.setOnClickListener(click_numbers);
        bt4.setOnClickListener(click_numbers);
        bt5.setOnClickListener(click_numbers);
        bt6.setOnClickListener(click_numbers);
        bt7.setOnClickListener(click_numbers);
        bt8.setOnClickListener(click_numbers);
        bt9.setOnClickListener(click_numbers);
    }
}