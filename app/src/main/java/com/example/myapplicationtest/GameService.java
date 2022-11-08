package com.example.myapplicationtest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class GameService extends IntentService {

    public static final String ACTION_GENERATE = BuildConfig.APPLICATION_ID + ".GENERATE_NUMBER";
    public static final String ACTION_PARSE_SCORES = BuildConfig.APPLICATION_ID + ".PARSE_SCORES";

    public static final String SIZE_NUMBER = "size";
    public static final String GENERATED_NUMBERS = "list_num";
    public static final String INPUT_NUMBERS = "input_value";
    public static final String NUMBER_COUNT = "number_cnt";
    public static final String TRY_COUNT = "try_cnt";


    public GameService() {

        super(GameService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_GENERATE)){
            int size = intent.getIntExtra(SIZE_NUMBER, 0);
            Generate_Numbers(size);

        }
        else if(action.equals(ACTION_PARSE_SCORES)){
            ArrayList<Integer> gen = intent.getIntegerArrayListExtra(GENERATED_NUMBERS);
            Log.d("lenght", Integer.toString(gen.size()));
            String input = intent.getStringExtra(INPUT_NUMBERS);
            Log.d("input_str", input);
            int num_count = intent.getIntExtra(NUMBER_COUNT, 0);
            Log.d("count", Integer.toString(num_count));
            int try_count = intent.getIntExtra(TRY_COUNT, 0);
            GetScores(gen, input, num_count, try_count);

        }
    }

    public void Generate_Numbers(int size){
        App app = (App) getApplication();
        ArrayList<Integer> game_numbers = new ArrayList<Integer>();
        ArrayList<Integer> list_all_numbers = new ArrayList<Integer>();
        for(int i = 0 ; i < 10; i++){
            list_all_numbers.add(i);
        }
        Collections.shuffle(list_all_numbers);
        for(int k = 0; k < size; k++){
            game_numbers.add(list_all_numbers.get(k));
        }
        app.publishComplete(game_numbers);
    }

    public void GetScores(ArrayList<Integer> generate, String input_text, int number_count, int try_count){
        ArrayList<Integer> temp = new ArrayList<Integer>(generate);
        App app = (App) getApplication();
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

            String result_points = String.format("%d Bulls %d Cows", Bulls, Cows);
            ResultState res = new ResultState (input_text, result_points, Integer.toString(try_count));

            if(Bulls == number_count){
                String congrat =  String.format("Congratulations you passed in %d tries!", try_count);
                app.gettingScoresComplete(res, congrat);
            }
            else {
                app.gettingScoresComplete(res, null);
            }
            Cows = 0;
            Bulls = 0;

        }
    }
}
