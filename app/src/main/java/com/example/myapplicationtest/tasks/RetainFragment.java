package com.example.myapplicationtest.tasks;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplicationtest.ResultState;
import com.example.myapplicationtest.activities.MainGameActivity;

import java.util.ArrayList;

public class RetainFragment  extends Fragment {

    public static final String TAG = RetainFragment.class.getSimpleName();
    private MainStateListener listener;
    private Task<ArrayList<Integer>> currentTask;
    private Task<ResultState> resultTask;
    private ArrayList<Integer> generate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void generateNumber(int size){
        currentTask = createGenerateNumberTask(size);
        currentTask.Execute(new TaskListenerThread<ArrayList<Integer>>(){

            @Override
            public void onSuccess(ArrayList<Integer> result) {
                generate = result;
                getNumberState();
            }

            @Override
            public void onError(Throwable error) {

                Log.d("error", "Get generate number error");
            }
        });
    }

    public void getScoresResult(ArrayList<Integer> generated, int number_cnt, String input, int try_cnt){
        resultTask = createGetScoreTask(generated, number_cnt, input, try_cnt);
        resultTask.Execute(new TaskListenerThread<ResultState>() {
            @Override
            public void onSuccess(ResultState result) {
                getResultObject(result);
            }

            @Override
            public void onError(Throwable error) {
                Log.d("error", "Get resultset value error");
            }
        });
    }

    private void getNumberState(){
        if (listener != null){
            listener.onGenerate(generate);
        }
    }

    private void getResultObject(ResultState result){
        if(listener != null){
            listener.onResultState(result);
        }
    }

    private Task<ArrayList<Integer>> createGenerateNumberTask(int numb){

        return new LooperThreadTask<>(new GenerateNumberCallable(numb));
    }

    private Task<ResultState> createGetScoreTask(ArrayList<Integer> generate, int numb_cont, String input_text, int try_count){

        return new LooperThreadTask<>(new GetScoreCallable(generate, numb_cont, input_text, try_count));
    }


    public void setListener(MainStateListener listener){

        this.listener = listener;
    }

    public interface MainStateListener{

        void onGenerate(ArrayList<Integer> res);
        void onResultState(ResultState result);
    }
}
