package com.example.myapplicationtest.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplicationtest.App;
import com.example.myapplicationtest.GameService;
import com.example.myapplicationtest.R;
import com.example.myapplicationtest.ResultAdapter;
import com.example.myapplicationtest.ResultState;
import com.example.myapplicationtest.TaskListener;
import com.example.myapplicationtest.tasks.GenerateNumberCallable;
import com.example.myapplicationtest.tasks.LooperThreadTask;
import com.example.myapplicationtest.tasks.RetainFragment;
import com.example.myapplicationtest.tasks.Task;
import com.example.myapplicationtest.tasks.TaskListenerThread;

import java.util.ArrayList;
import java.util.Collections;

public class MainGameActivity extends AppCompatActivity implements TaskListener, RetainFragment.MainStateListener {

    App app;
    private RetainFragment retainFragment;

    private Button submit_button, del_button, bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;
    private LayoutInflater inflater;
    private ViewGroup container;
    private ResultAdapter adapter;
    private ArrayList<ResultState> states = new ArrayList<ResultState>();
    private TextView input_view;
    private String ARG_PARAM_DIFFICULT;
    private static int number_count;

    private Button[] number_buttons = {bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9};
    private Integer[] id_buttons_res = {R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9};

    ListView try_list;

    private int try_count = 0;
    private ArrayList<Integer> generate;

    public void setGenerate(ArrayList<Integer> generate) {
        this.generate = generate;
    }

    @Override
    protected void onStart() {
        super.onStart();
        app.addListener(this); // this because activity implements task listener interface
    }

    @Override
    protected void onStop() {
        super.onStop();
        app.removeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        retainFragment.setListener(null);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list", states);
        outState.putInt("try_num", try_count);
        outState.putString("input_val", input_view.getText().toString());
        outState.putIntegerArrayList("generate_numbers", generate);
        outState.putInt("num_count", number_count);
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
        switch (ARG_PARAM_DIFFICULT){
            case "Low":
                number_count = 4; break;
            case "Medium":
                number_count = 5; break;
            case "High":
                number_count = 6; break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplication();

        this.retainFragment = (RetainFragment) getSupportFragmentManager().findFragmentByTag(RetainFragment.TAG);
        if(retainFragment == null){
            retainFragment = new RetainFragment();
            getSupportFragmentManager().beginTransaction().add(retainFragment, RetainFragment.TAG).commit();
        }

        retainFragment.setListener(this);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_relative_main_window);
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_relative_main_horizontal);
        }

        if(savedInstanceState == null){
            Bundle arguments = getIntent().getExtras();
            this.ARG_PARAM_DIFFICULT = arguments.get("difficult").toString(); // Get Difficult param
            SetgameDifficult();
        }
        else {
            this.input_view = findViewById(R.id.pressed_view);
            states = savedInstanceState.getParcelableArrayList("list");
            try_count = savedInstanceState.getInt("try_num");
            input_view.setText(savedInstanceState.getString("input_val"));
            generate = savedInstanceState.getIntegerArrayList("generate_numbers");
            number_count = savedInstanceState.getInt("num_count");
        }

        this.submit_button = findViewById(R.id.button_submit);
        this.del_button = findViewById(R.id.button_del);
        this.input_view = findViewById(R.id.pressed_view);

        for (int btn = 0; btn < 10; btn++) {
            number_buttons[btn] = findViewById(id_buttons_res[btn]);
        }

        this.try_list = findViewById(R.id.try_list);
        adapter = new ResultAdapter(MainGameActivity.this, states);
        try_list.setAdapter(adapter);




        View.OnClickListener click_button_submit= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_view.getText().toString().length() == number_count ){
                    try_count++;
                    Intent intent = new Intent(getBaseContext(), GameService.class);
                    intent.setAction(GameService.ACTION_PARSE_SCORES);
                    intent.putIntegerArrayListExtra(GameService.GENERATED_NUMBERS, generate);
                    intent.putExtra(GameService.INPUT_NUMBERS, input_view.getText().toString());
                    intent.putExtra(GameService.NUMBER_COUNT, number_count);
                    intent.putExtra(GameService.TRY_COUNT, try_count);
                    startService(intent);
                }
                else {
                    input_view.setError("Must be 4 numbs");
                }
            }
        };

        View.OnClickListener click_button_del= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input_view = findViewById(R.id.pressed_view);
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
                EditText input_view = findViewById(R.id.pressed_view);
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

        if(generate == null){
            retainFragment.generateNumber(number_count);
           // Log.d("size", Integer.toString(generate.size()));
            //currentTask = createGenerateNumberTask(number_count);

//            //this.generate = Generate_Numbers(number_count); // Generate a number
//            Intent intent = new Intent(this, GameService.class);
//            intent.setAction(GameService.ACTION_GENERATE);
//            intent.putExtra(GameService.SIZE_NUMBER, number_count);
//            startService(intent);
        }
    }

    @Override
    public void onComplete(ArrayList<Integer> result) {
        this.generate = result;
        String t = Integer.toString(generate.get(0));
        Log.d("Activity Game", Integer.toString(generate.size()));
    }

    @Override
    public void onGetScoresComplete(ResultState res, String congratulation) {
        if (congratulation == null){
            adapter.add(res);
            input_view.setText("");
            scrollToBottom();
        }
        else {
            adapter.add(res);
            scrollToBottom();
            TextView congrat_view = findViewById(R.id.text_result);
            congrat_view.setText(congratulation);
        }
    }

    @Override
    public void onGenerate(ArrayList<Integer> res) {
        this.generate = res;
    }
}