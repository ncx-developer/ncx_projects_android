package com.ncx.dms.adapter;

import android.content.Context;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ncx.dms.R;

import java.util.ArrayList;

public class SpinnerAdapter<T> extends ArrayAdapter {

    Context context;

    ArrayList<T> keys;
    ArrayList<String> values;

    LayoutInflater inflater;

    public SpinnerAdapter(@NonNull Context context, int resource, ArrayList<T> keys, ArrayList<String> values) {
        super(context, resource);
        this.context = context;
        this.keys = keys;
        this.values = values;
        this.inflater = (LayoutInflater.from(context));
    }


    /*public SpinnerAdapter(Context applicationContext, ArrayMap<T, T> keyValuePairs){
        this.context = applicationContext;
        this.keyValuePairs = keyValuePairs;
        this.inflater = (LayoutInflater.from(applicationContext));
    }*/

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.custom_spinner, null);
        view.setTag(keys.get(position));
        ((TextView)view).setText(values.get(position));
        /*TextView key = (TextView)view.findViewById(R.id.key);
        TextView value = (TextView)view.findViewById(R.id.value);

        key.setTag(keyValuePairs.keyAt(position));
        value.setText (keyValuePairs.valueAt(position).toString());*/
        return view;
    }
}
/*public class SpinnerAdapter<T> extends BaseAdapter {

    Context context;
    ArrayMap<T, T> keyValuePairs;
    LayoutInflater inflater;

    public SpinnerAdapter(Context applicationContext, ArrayMap<T, T> keyValuePairs){
        this.context = applicationContext;
        this.keyValuePairs = keyValuePairs;
        this.inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return keyValuePairs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.custom_key_value_spinner, null);
        TextView key = (TextView)view.findViewById(R.id.key);
        TextView value = (TextView)view.findViewById(R.id.value);

        key.setTag(keyValuePairs.keyAt(position));
        value.setText (keyValuePairs.valueAt(position).toString());
        return view;
    }
}*/
