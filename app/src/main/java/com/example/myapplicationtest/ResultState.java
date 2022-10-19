package com.example.myapplicationtest;

import java.util.concurrent.atomic.AtomicInteger;

public class ResultState {

    private String input_value;
    private String result;
    private int child_num;
    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    public ResultState(String input, String res) {
        this.input_value = input;
        this.result = res;
        this.child_num = COUNTER.getAndIncrement();
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

    public int getChild_num() {
        return child_num;
    }

    public void setChild_num(int child_num) {
        this.child_num = child_num;
    }
}
