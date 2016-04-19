package com.example.stacjonarny.graulamki.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.stacjonarny.graulamki.MainActivity;
import com.example.stacjonarny.graulamki.R;

import java.text.DecimalFormat;

public class GameSummary extends Fragment {


    private TextView correctAnswers;
    private TextView textViewSummaries;
    private String[] gameTexTSummaries;
    private Button goBackButton;

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
        goBackButton = (Button) viev.findViewById(R.id.go_back_to_menu);
        int answers = MainActivity.gameState.getCorrectAnswerCount();
        int allAnswers = MainActivity.gameState.getDifficultLevel().getQuestionCount();
        float percentAnswers = ((float)answers / (float)allAnswers) * 100f;
        String toDisplay = " " + String.format("%.2f",percentAnswers) + "%" + " (" + answers + "/" + allAnswers + ")";
        if(percentAnswers < 30) {
            correctAnswers.setTextColor(MainActivity.RED_COLOR);
            textViewSummaries.setText(gameTexTSummaries[0]);
        }
        else if(percentAnswers >= 30 && percentAnswers < 75) {
            correctAnswers.setTextColor(MainActivity.ORANGE_COLOR);
            textViewSummaries.setText(gameTexTSummaries[1]);
        }
        else {
            correctAnswers.setTextColor(MainActivity.GREEN_COLOR);
            textViewSummaries.setText(gameTexTSummaries[2]);
        }
        correctAnswers.setText(toDisplay);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenu main_menu_fragment = new MainMenu();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, main_menu_fragment);
                transaction.commit();
            }
        });
        return viev;
    }
}
