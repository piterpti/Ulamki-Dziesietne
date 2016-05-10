package com.example.stacjonarny.graulamki.Classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.*;
import java.util.*;

import com.example.stacjonarny.graulamki.MainActivity;
import com.example.stacjonarny.graulamki.R;


public class AchievementAdapter extends ArrayAdapter {

    private ImageView image;

    public AchievementAdapter(Context context, ArrayList<Achievement> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Achievement achievement = (Achievement) getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_achievement_adapter, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.simpleAchievement);
        TextView diffLevelView = (TextView) convertView.findViewById(R.id.achievementDifficultLevel);
        image = (ImageView) convertView.findViewById(R.id.achievementListImage);
        ImageChange(achievement.isLocked(), achievement.getDifficultLevel());
        String achievementDiffLevel = MainActivity.difficultLevels[achievement.getDifficultLevel()-1].getLevel();
        textView.setText(achievement.getName());
        diffLevelView.setText(achievementDiffLevel);
        return convertView;
    }

    public void ImageChange(boolean locked, int difficultLevel) {
        if(!locked) {
            if(difficultLevel <= 2) {
                image.setImageResource(R.drawable.achievement_bronze);
            }
            else if(difficultLevel <=4) {
                image.setImageResource(R.drawable.achievement_silver);
            }
            else {
                image.setImageResource(R.drawable.achievement_gold);
            }
        }
        else {
            image.setImageResource(R.drawable.achievement_locked);
        }
    }
}
