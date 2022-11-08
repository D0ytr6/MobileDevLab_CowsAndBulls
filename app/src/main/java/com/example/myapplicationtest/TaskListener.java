package com.example.myapplicationtest;

import java.util.ArrayList;

public interface TaskListener {
    void onComplete(ArrayList<Integer> result);
    void onGetScoresComplete(ResultState res, String congratulation);
}
