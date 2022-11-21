package com.example.myapplicationtest.tasks;

public interface Task <T>{

    void Cancel();

    void Execute(TaskListenerThread<T> listener);
}
