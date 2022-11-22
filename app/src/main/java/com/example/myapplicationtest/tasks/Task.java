package com.example.myapplicationtest.tasks;

// Basic interface for task functionality
public interface Task <T>{

    void Cancel();

    void Execute(TaskListenerThread<T> listener);
}
