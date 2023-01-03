package com.example.myapplicationtest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.concurrent.atomic.AtomicInteger;

public class ResultState implements Parcelable {
    private String input_value;
    private String result;
    private String counter;


    public ResultState(String input, String res, String count) {
        this.input_value = input;
        this.result = res;
        this.counter = count;
    }

    protected ResultState(Parcel in) {
        input_value = in.readString();
        result = in.readString();
        counter = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(input_value);
        parcel.writeString(result);
        parcel.writeString(counter);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultState> CREATOR = new Creator<ResultState>() { // Interface object
        @Override
        public ResultState createFromParcel(Parcel in) {
            return new ResultState(in);
        }

        @Override
        public ResultState[] newArray(int size) {
            return new ResultState[size];
        }
    };

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

    public String getCounter() {
        return counter;
    }
    public void setCounter(String counter) {
        this.counter = counter;
    }



}
