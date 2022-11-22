package com.example.myapplicationtest.tasks;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplicationtest.activities.MainGameActivity;

import java.util.ArrayList;

public class RetainFragment  extends Fragment {

    public static final String TAG = RetainFragment.class.getSimpleName();
    private MainStateListener listener;
    private Task<ArrayList<Integer>> currentTask;
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

                //Log.d("result", Integer.toString(generate.size()));
            }

            @Override
            public void onError(Throwable error) {
                Log.d("error", "error");
            }
        });
    }

    private void getNumberState(){
        if (listener != null){
            listener.onGenerate(generate);
        }
    }

    private Task<ArrayList<Integer>> createGenerateNumberTask(int numb){
        return new LooperThreadTask<>(new GenerateNumberCallable(numb));
    }

    public void setListener(MainStateListener listener){
        this.listener = listener;
    }

    public interface MainStateListener{
        void onGenerate(ArrayList<Integer> res);
    }
}
