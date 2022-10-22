package com.example.myapplicationtest;

import java.util.concurrent.atomic.AtomicInteger;

public class ResultState {

    private String input_value;
    private String result;
    private int counter;


    public ResultState(String input, String res, int count) {
        this.input_value = input;
        this.result = res;
        this.counter = count;
    }

    public String getInput_value() {
        return input_value;
    }

    public void setInput_value(String input_value) {
        this.input_value = input_value;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
