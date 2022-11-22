package com.example.myapplicationtest.tasks;

import com.example.myapplicationtest.App;
import com.example.myapplicationtest.ResultState;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class GetScoreCallable implements Callable<ResultState> {

    private ArrayList<Integer> generate;
    private int number_count;
    private String input_text;
    private int try_count;

    public GetScoreCallable(ArrayList<Integer> generate, int number_count, String input_text, int try_count) {
        this.generate = generate;
        this.number_count = number_count;
        this.input_text = input_text;
        this.try_count = try_count;
    }

    @Override
    public ResultState call() throws Exception {
        ArrayList<Integer> temp = new ArrayList<Integer>(generate);
        int Cows = 0;
        int Bulls = 0;
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
        return res;
    }
}
