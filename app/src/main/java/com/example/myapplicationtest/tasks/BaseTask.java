package com.example.myapplicationtest.tasks;

import android.os.Handler;
import android.os.Looper;

// Base task functionality
public abstract class BaseTask<T> implements Task<T>{

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private TaskListenerThread<T> tasklistener;
    private boolean executed;
    private boolean cancel;

    // execute task
    @Override
    public void Execute(TaskListenerThread<T> listener) {
        if (executed){ // task is executed
            throw new RuntimeException("Task have been already executed");
        }
        if(cancel){
            return;
        }
        executed = true;
        this.tasklistener = listener; // save listener, callback realization
        start();

    }

    // cancel task
    @Override
    public void Cancel() {
        if(!cancel){
            cancel = true;
            tasklistener = null;
            onCancel();
        }
    }

    private void RunOnMainThread(Runnable action){
        if(Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()){
            action.run();
        }
        else {
            HANDLER.post(action);  // run through handler on main loop
        }
    }

    protected final void publishSuccess(T result){
        // run callback listener in MainThread
        RunOnMainThread(() -> {
            if(tasklistener != null){
                // send result to callback
                tasklistener.onSuccess(result);
                tasklistener = null; // clear callback
            }
        });
    }

    protected final void publishError(Throwable error){
        // run callback listener in MainThread
        RunOnMainThread(() -> {
            if(tasklistener != null){
                tasklistener.onError(error);
                tasklistener = null;
            }
        });
    }

    // реалізовуються в наслідуваних классах
    protected abstract void start();
    protected abstract void onCancel();

}
