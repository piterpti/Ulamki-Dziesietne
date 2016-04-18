package com.example.stacjonarny.graulamki.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stacjonarny.graulamki.R;

import java.text.DecimalFormat;

public class GameSummary extends Fragment {


    private TextView correctAnswers;
    private TextView textViewSummaries;
    private String[] gameTexTSummaries;

    public GameSummary() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viev = inflater.inflate(R.layout.fragment_game_summary, container, false);
        gameTexTSummaries = getResources().getStringArray(R.array.game_summary);
        correctAnswers = (TextView) viev.findViewById(R.id.summaryGameStatistics);
        textViewSummaries = (TextView) viev.findViewById(R.id.textSummaries);
        int answers = Game.gameState.getCorrectAnswerCount();
        int allAnswers = Game.gameState.getDifficultLevel().getQuestionCount();
        float percentAnswers = ((float)answers / (float)allAnswers) * 100f;
        String toDisplay = " " + String.format("%.2f",percentAnswers) + "%" + " (" + answers + "/" + allAnswers + ")";
        if(percentAnswers < 30) {
            correctAnswers.setTextColor(Color.argb(255, 230, 21, 21));
            textViewSummaries.setText(gameTexTSummaries[0]);
        }
        else if(percentAnswers >= 30 && percentAnswers < 75) {
            correctAnswers.setTextColor(Color.argb(255,245,133,5));
            textViewSummaries.setText(gameTexTSummaries[1]);
        }
        else {
            correctAnswers.setTextColor(Color.argb(255,5,245,13));
            textViewSummaries.setText(gameTexTSummaries[2]);
        }
        correctAnswers.setText(toDisplay);
        return viev;
    }
}
