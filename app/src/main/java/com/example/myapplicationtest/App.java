package com.example.myapplicationtest;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class App extends Application {

    // use for working with threads
    private Handler handler = new Handler(Looper.getMainLooper());

    private Set<TaskListener> listeners = new HashSet<>();

    // Use for Activities
    public void addListener(TaskListener listener){

        this.listeners.add(listener);
    }

    public void removeListener(TaskListener listener){

        this.listeners.add(listener);
    }

    // Use for Service
    public void publishComplete(ArrayList<Integer> result){
        // Service always work in another thread from activity
        // виконати в потоці Handler операцію, описану об'єктом Runnable
        // в наступній ітерації циклу обробки повідомлень (~18 мс)
        handler.post(() ->{
            for(TaskListener listener : listeners){
                listener.onComplete(result);
            }
        });
    }

    public void gettingScoresComplete(ResultState res, String congrat){
        handler.post(()->{
            for(TaskListener listener : listeners){
                listener.onGetScoresComplete(res, congrat);
            }
        });
    }

}
