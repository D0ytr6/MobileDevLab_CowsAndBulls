package com.example.myapplicationtest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainGameFragment extends Fragment {

    private Button submit_button, del_button, bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;

    private ResultAdapter adapter;
    private ArrayList<ResultState> states = new ArrayList<ResultState>();

    private static final String ARG_PARAM = "VALUE";
    private List <Button> number_buttons;
    ListView try_list;

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

        View view = inflater.inflate(R.layout.fragment_main_window, container, false);

        // Inflate the layout for this fragment
        // return view
        return view;
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

//    private ArrayList<Integer> shuffle_add_data(int size){
//        ArrayList<Integer> list_all_numbers = new ArrayList<Integer>();
//        for(int i = 0 ; i < 10; i++){
//            list_all_numbers.add(i);
//        }
//
//    }

    public ArrayList<Integer> Generate_Numbers(int size){
        ArrayList<Integer> game_numbers = new ArrayList<Integer>();
        ArrayList<Integer> list_all_numbers = new ArrayList<Integer>();
        for(int i = 0 ; i < 10; i++){
            list_all_numbers.add(i);
        }
        if(size == 4){
            Collections.shuffle(list_all_numbers);
            for(int k = 0; k < size; k++){
                game_numbers.add(list_all_numbers.get(k));
            }
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

    private void setInitialData(){
        states.add(new ResultState ("9789", "0 Bulls 0 Cows"));
        states.add(new ResultState ("9789", "0 Bulls 0 Cows"));
        states.add(new ResultState ("9789", "0 Bulls 0 Cows"));
        states.add(new ResultState ("9789", "0 Bulls 0 Cows"));
        states.add(new ResultState ("9789", "0 Bulls 0 Cows"));
        states.add(new ResultState ("9789", "0 Bulls 0 Cows"));
        states.add(new ResultState ("9789", "0 Bulls 0 Cows"));
        states.add(new ResultState ("9789", "0 Bulls 0 Cows"));
        states.add(new ResultState ("9789", "0 Bulls 0 Cows"));
        states.add(new ResultState ("9789", "0 Bulls 1 Cows"));
        states.add(new ResultState ("9789", "0 Bulls 1 Cows"));
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

        this.try_list = view.findViewById(R.id.try_list);
        setInitialData();
        adapter = new ResultAdapter(getContext(), states);
        try_list.setAdapter(adapter);

        ArrayList<Integer> generate = Generate_Numbers(4); // Generate a number

//        ArrayList<String> TestList = new ArrayList<String>();
//
//        for(int i = 0; i < 10; i++){
//            TestList.add("Test");
//        }

//        // создаем адаптер
//        this.adapter = new ArrayAdapter(getContext(),
//                android.R.layout.simple_list_item_1, TestList);
//
//        // устанавливаем для списка адаптер
//        countriesList.setAdapter(adapter);


        View.OnClickListener click_button_submit= new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Integer> temp = generate;
                EditText input_view = view.findViewById(R.id.pressed_view);
                String input_text = input_view.getText().toString();
                int Cows = 0;
                int Bulls = 0;
                if (input_text.length() == 4){
                    for(int i = 0; i < 4; i++){
                        if(Character.toString(input_text.charAt(i)) == Integer.toString(temp.get(i))){
                            Bulls++;
                            temp.remove(i);
                        }
                    }

                    for (int num: temp) {
                        if(input_text.contains(Integer.toString(num))){
                            Cows++;
                        }
                    }

                    String result_points = String.format("%d Bulls %d Cows", Bulls, Cows);
                    ResultState res = new ResultState (input_text, result_points);
                    Cows = 0;
                    Bulls = 0;
                    adapter.add(res);
                    scrollToBottom();
                }

//                EditText input_view = view.findViewById(R.id.pressed_view);
//                ArrayList<Integer> generate = Generate_Numbers(4);
//                for (int n: generate) {
//                    input_view.append(Integer.toString(n));
//
//                }
//                adapter.add(new ResultState("2211", "1 Bulls 0 Cows"));
//                scrollToBottom();



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