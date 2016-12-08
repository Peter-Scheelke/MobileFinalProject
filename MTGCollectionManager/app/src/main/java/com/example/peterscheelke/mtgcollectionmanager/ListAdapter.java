package com.example.peterscheelke.mtgcollectionmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.Tuple;

import java.util.List;

import android.app.Activity;


// http://kb4dev.com/tutorial/android-listview/custom-adapters-in-android
public class ListAdapter extends ArrayAdapter<Tuple<String, Integer>>{

    private List<Tuple<String, Integer>> dataItems = null;
    private Context context;

    public ListAdapter(Context context, List<Tuple<String, Integer>> objects) {
        super(context, R.layout.listview_row, objects);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.dataItems = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.listview_row, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView quantity = (TextView) convertView.findViewById(R.id.quantity);
        name.setText(dataItems.get(position).first);
        quantity.setText(String.valueOf(dataItems.get(position).last));
        return convertView;
    }
}