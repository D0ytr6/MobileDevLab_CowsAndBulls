package com.example.myapplicationtest.tasks;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;

public class LooperThreadTask<T> extends BaseTask<T>{

    private static Handler taskHandler;

    static {
        new LooperThread(handler -> taskHandler = handler).start();
    }

    private Callable<T> callable;

    public LooperThreadTask(Callable<T> callable) {
        this.callable = callable;
    }

    @Override
    protected void start() {

    }

    @Override
    protected void onCancel() {

    }

    static class LooperThread extends Thread{

        private HandlerListener listener;

        public LooperThread(HandlerListener listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            Looper.prepare();
            Looper looper = Looper.myLooper();
            Handler handler = new Handler(looper);
            listener.onHandler(handler);
            listener = null;
            Looper.loop();
        }
    }
    private interface HandlerListener{
        void onHandler(Handler handler);

    }

}
