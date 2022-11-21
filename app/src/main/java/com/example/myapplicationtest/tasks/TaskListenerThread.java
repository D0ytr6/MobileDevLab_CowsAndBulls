package com.example.myapplicationtest.tasks;

public interface TaskListenerThread <T>{

    void onSuccess(T result);

    void onError(Throwable error);
}
