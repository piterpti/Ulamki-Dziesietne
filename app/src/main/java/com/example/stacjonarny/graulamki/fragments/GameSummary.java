package com.example.stacjonarny.graulamki.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stacjonarny.graulamki.MainActivity;
import com.example.stacjonarny.graulamki.R;

import java.text.DecimalFormat;

public class GameSummary extends Fragment {


    private TextView correctAnswers;

    public GameSummary() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viev = inflater.inflate(R.layout.fragment_game_summary, container, false);

         correctAnswers = (TextView) viev.findViewById(R.id.summaryGameStatistics);
        int answers = MainActivity.gameState.getCorrectAnswerCount();
        int allAnswers = MainActivity.gameState.getDifficultLevel().getQuestionCount();
        float percentAnswers = ((float)answers / (float)allAnswers) * 100f;
        String toDisplay = " " + String.format("%.2f",percentAnswers) + "%" + " (" + answers + "/" + allAnswers + ")";
        correctAnswers.setText(toDisplay);
        return viev;
    }

}
