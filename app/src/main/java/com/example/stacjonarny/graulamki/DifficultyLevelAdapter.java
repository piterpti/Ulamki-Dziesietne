package com.example.stacjonarny.graulamki;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import  java.util.*;

public class DifficultyLevelAdapter extends ArrayAdapter {

    public DifficultyLevelAdapter(Context context, String[] list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String s = (String) getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.difficulty_levels_adapter, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.diffLevelText);
        textView.setText(s);
        return convertView;
    }
}
