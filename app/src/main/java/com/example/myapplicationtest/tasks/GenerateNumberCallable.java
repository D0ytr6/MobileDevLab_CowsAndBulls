package com.example.myapplicationtest.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;

public class GenerateNumberCallable implements Callable<ArrayList<Integer>> {

    private int size;

    public GenerateNumberCallable(int size) {
        this.size = size;
    }

    @Override
    public ArrayList<Integer> call() throws Exception {

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
}
