package com.example.stacjonarny.graulamki.Classes;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.stacjonarny.graulamki.Classes.DifficultLevel;
import com.example.stacjonarny.graulamki.R;


public class DifficultyLevelAdapter extends ArrayAdapter {

    public DifficultyLevelAdapter(Context context, DifficultLevel[] list) {
        super(context, 0, list);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DifficultLevel lvl = (DifficultLevel) getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.difficulty_levels_adapter, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.diffLevelText);
        TextView countView = (TextView) convertView.findViewById(R.id.diffLevelCount);
        TextView timeView = (TextView) convertView.findViewById(R.id.diffLevelTime);
        textView.setText(lvl.getLevel());
        timeView.setText(convertView.getResources().getString(R.string.timeToAnswer) + " " + lvl.getTimeToAnswer() + " sekund");
        countView.setText(convertView.getResources().getString(R.string.questionsCount) + " " +lvl.getQuestionCount());
        convertView.setBackgroundColor(Color.parseColor(lvl.getBackgroundColor()));
        return convertView;
    }

}
