package com.example.myapplicationtest.tasks;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;

// Послідовне виконання задач
public class LooperThreadTask<T> extends BaseTask<T>{

    // working in our custom thread
    private static Handler taskHandler = new Handler();

    // static constructor
    // create thread, get handler and run thread
    static {
        new LooperThread(handler -> taskHandler = handler).start();
    }

    // Runnable analog, return obj on result
    private Callable<T> callable;
    private Runnable action;

    public LooperThreadTask(Callable<T> callable) {

        this.callable = callable;
    }

    @Override
    protected void start() {
        action = () -> {
            try {
                T result = callable.call();
                publishSuccess(result);
            }
            catch (Exception e){
                publishError(e);
            }
        };
        taskHandler.post(action);
    }

    @Override
    protected void onCancel() {
        if(action != null){
            taskHandler.removeCallbacks(action);
        }
    }

    // Вкладений клас потоку, наслідується від Thread
    static class LooperThread extends Thread{

        private HandlerListener listener;

        public LooperThread(HandlerListener listener) {
            this.listener = listener;
        }

        // work in other thread
        @Override
        public void run() {

            // Використовуємо Looper для обробки MessageQueue, в яку Handler відправляє повідомлення.
            // Базовий Looper створєються при запуску програми, перевіряє чергу змін в інтерфейсі.
            // Створюємо власний для власного потоку

            Looper.prepare(); // Create looper. Sync with thread in which created
            Looper looper = Looper.myLooper();
            Handler handler = new Handler(looper);
            listener.onHandler(handler);
            listener = null;
            Looper.loop(); // start loop
        }
    }
    // використовується для відслідковування створення handler
    private interface HandlerListener{
        void onHandler(Handler handler);

    }

}
