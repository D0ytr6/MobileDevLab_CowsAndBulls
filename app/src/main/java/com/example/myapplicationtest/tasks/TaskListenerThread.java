package com.example.myapplicationtest.tasks;

// callback functionality, return on success and on error
public interface TaskListenerThread <T>{

    // works only in main thread, can to edit UI
    void onSuccess(T result);

    void onError(Throwable error);
}
