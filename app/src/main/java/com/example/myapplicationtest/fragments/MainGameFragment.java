package com.example.myapplicationtest.fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.ResultAdapter;
import com.example.myapplicationtest.ResultState;

import java.util.ArrayList;
import java.util.Collections;

public class MainGameFragment extends Fragment {

    private Button submit_button, del_button, bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;
    private LayoutInflater inflater;
    private ViewGroup container;
    private ResultAdapter adapter;
    private ArrayList<ResultState> states = new ArrayList<ResultState>();
    private TextView input_view;
    static String ARG_PARAM_DIFFICULT;
    private static int number_count;

    private Button[] number_buttons = {bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9};
    private Integer[] id_buttons_res = {R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9};

    ListView try_list;

    private int try_count = 0;
    private ArrayList<Integer> generate;


    public static MainGameFragment newInstance(String difficult) {
        MainGameFragment fragment = new MainGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_DIFFICULT, difficult);
        fragment.setArguments(args);
        return fragment;
    }


    public View InitializeUserInterface(){
        View view;
        int orientation = getActivity().getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            view = inflater.inflate(R.layout.fragment_main_window, container, false);
        }
        else{
            view = inflater.inflate(R.layout.fragment_main_window_horizontal, container, false);
        }
        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", states);
        outState.putInt("try_num", try_count);
        outState.putString("input_val", input_view.getText().toString());
        outState.putIntegerArrayList("generate_numbers", generate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;

        View view = InitializeUserInterface();
        this.input_view = view.findViewById(R.id.pressed_view);

        if(savedInstanceState == null){
        }else {
            states = savedInstanceState.getParcelableArrayList("list");
            try_count = savedInstanceState.getInt("try_num");
            input_view.setText(savedInstanceState.getString("input_val"));
            generate = savedInstanceState.getIntegerArrayList("generate_numbers");
        }

        return view;
    }

    public void CheckText(EditText input_view, String number){
        if(input_view.getText() != null){
            String text = input_view.getText().toString();
            if(text.length() != number_count){
                input_view.append(number);
            }
        }
        else{
            input_view.append(number);
        }

    }

    public ArrayList<Integer> Generate_Numbers(int size){
        ArrayList<Integer> game_numbers = new ArrayList<Integer>();
        ArrayList<Integer> list_all_numbers = new ArrayList<Integer>();
        for(int i = 0 ; i < 10; i++){
            list_all_numbers.add(i);
        }

        Collections.shuffle(list_all_numbers);
        for(int k = 0; k < size; k++){
            game_numbers.add(list_all_numbers.get(k));
        }

        return game_numbers;
    }

    private void scrollToBottom() {
        try_list.post(new Runnable() {
            @Override
            public void run() {
                try_list.smoothScrollToPosition(adapter.getCount() - 1);
            }
        });
    }

    private void SetgameDifficult(){
        String t = getArguments().getString(ARG_PARAM_DIFFICULT);
        switch (t){
            case "Low":
                number_count = 4; break;
            case "Medium":
                number_count = 5; break;
            case "High":
                number_count = 6; break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SetgameDifficult();

        this.submit_button = view.findViewById(R.id.button_submit);
        this.del_button = view.findViewById(R.id.button_del);
        this.input_view = view.findViewById(R.id.pressed_view);

        for (int btn = 0; btn < 10; btn++) {
            number_buttons[btn] = view.findViewById(id_buttons_res[btn]);
        }

        this.try_list = view.findViewById(R.id.try_list);
        adapter = new ResultAdapter(getContext(), states);
        try_list.setAdapter(adapter);

        if(generate == null){
            this.generate = Generate_Numbers(number_count); // Generate a number
        }

        View.OnClickListener click_button_submit= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> temp = new ArrayList<Integer>(generate);
                String input_text = input_view.getText().toString();
                int Cows = 0;
                int Bulls = 0;
                if (input_text.length() == number_count){
                    for(int i = 0; i < number_count; i++){
                        String temp_char = Character.toString(input_text.charAt(i));
                        String temp_arr = Integer.toString(generate.get(i));
                        if(temp_char.equals(temp_arr)){
                            Bulls++;
                            temp.remove(generate.get(i));
                        }
                    }

                    for (int num: temp) {
                        if(input_text.contains(Integer.toString(num))){
                            Cows++;
                        }
                    }

                    try_count++;
                    String result_points = String.format("%d Bulls %d Cows", Bulls, Cows);
                    ResultState res = new ResultState (input_text, result_points, Integer.toString(try_count));
                    input_view.setText(""); // clear input

                    if(Bulls == number_count){
                        String congrat =  String.format("Congratulations you passed in %d tries!", try_count);
                        TextView congrat_view = view.findViewById(R.id.text_result);
                        congrat_view.setText(congrat);
                    }
                    Cows = 0;
                    Bulls = 0;
                    adapter.add(res);
                    scrollToBottom();
                }
                else {
                    input_view.setError("Must be 4 numbs");
                }

            }
        };

        View.OnClickListener click_button_del= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input_view = view.findViewById(R.id.pressed_view);
                if(input_view.getText().length() != 0){
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

        for (Button btn: number_buttons) {
            btn.setOnClickListener(click_numbers);
        }

    }

}