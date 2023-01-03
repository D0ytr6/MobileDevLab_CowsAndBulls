package com.example.myapplicationtest;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends ArrayAdapter<ResultState> {

    Context context;
    LayoutInflater inflater;
    ArrayList<ResultState> result_list_data;

    public ResultAdapter(Context context, @NonNull ArrayList<ResultState> objects) {
        super(context, 0, objects);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.result_list_data = objects;

    }

    public ArrayList<ResultState> getValueList(){
        return result_list_data;
    }

    @Override
    public int getCount() {
        return result_list_data.size();
    }

    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = inflater.inflate(R.layout.result_list_item, parent, false);
        }
        ResultState result = result_list_data.get(position);

        TextView inc = (TextView) convertView.findViewById(R.id.first_try);
        TextView input_v = (TextView) convertView.findViewById(R.id.input_number);
        TextView result_v = (TextView) convertView.findViewById(R.id.result);

        inc.setText(result.getCounter());
        input_v.setText(result.getInput_value());
        result_v.setText(result.getResult());

        return convertView;
    }

}
