package com.example.myapplicationtest.tasks;

import android.os.Handler;
import android.os.Looper;

public abstract class BaseTask<T> implements Task<T>{

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private TaskListenerThread<T> tasklistener;
    private boolean executed;
    private boolean cancel;

    @Override
    public void Execute(TaskListenerThread<T> listener) {
        if (executed){
            throw new RuntimeException("Task have been already executed");
        }
        if(cancel){
            return;
        }
        executed = true;
        this.tasklistener = listener;
        start();

    }

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
            HANDLER.post(action);
        }
    }

    protected final void publishSuccess(T result){
        RunOnMainThread(() -> {
            if(tasklistener != null){
                tasklistener.onSuccess(result);
                tasklistener = null;
            }
        });
    }

    protected final void publishError(Throwable error){
        RunOnMainThread(() -> {
            if(tasklistener != null){
                tasklistener.onError(error);
                tasklistener = null;
            }
        });
    }
    protected abstract void start();
    protected abstract void onCancel();

}
