package com.example.stacjonarny.graulamki.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.*;

import com.example.stacjonarny.graulamki.Classes.Achievement;
import com.example.stacjonarny.graulamki.Classes.AchievementAdapter;
import com.example.stacjonarny.graulamki.Classes.NonScrollListView;
import com.example.stacjonarny.graulamki.MainActivity;
import com.example.stacjonarny.graulamki.R;

import info.hoang8f.widget.FButton;

public class GameSummary extends Fragment {

    private TextView correctAnswers;
    private TextView textViewSummaries;
    private String[] gameTexTSummaries;
    private FButton goBackToMenuButton;
    private FButton exitGameButton;
    private LinearLayout unlockedLayout;
    private NonScrollListView unlockedAchievementsList;

    public GameSummary() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viev = inflater.inflate(R.layout.fragment_game_summary, container, false);
        init(viev);
        int answers = MainActivity.gameState.getCorrectAnswerCount();
        int allAnswers = MainActivity.gameState.getDifficultLevel().getQuestionCount();
        float percentAnswers = ((float) answers / (float) allAnswers) * 100f;
        String toDisplay = " " + String.format("%.2f", percentAnswers) + "%" + " (" + answers + "/" + allAnswers + ")";
        TextColorSummary(percentAnswers);
        correctAnswers.setText(toDisplay);
        AddMenuButtonListener();
        int correctAnswersRowCount = MainActivity.gameState.getCorrectAnswersRowCount();
        UnlockedAchievementsDuringGame(correctAnswersRowCount);
        return viev;
    }



    private void UnlockedAchievementsDuringGame(int correctAnswersRowCount) {
        if(MainActivity.unlockedAchievements != null && MainActivity.unlockedAchievements.size() > 0) {
            unlockedAchievementsList.setAdapter(new AchievementAdapter(getActivity(), MainActivity.unlockedAchievements));
            unlockedLayout.setVisibility(View.VISIBLE);
            return;
        }
        MainActivity.unlockedAchievements = new ArrayList<>();
        for (Achievement a : MainActivity.achievementList) {
            if(a.getDifficultLevel() == MainActivity.gameState.getDifficultLevel().getLevelNum())
            {
                if (a.Unlock(correctAnswersRowCount))
                {
                    MainActivity.unlockedAchievements.add(a);
                    unlockedLayout.setVisibility(View.VISIBLE);
                }
            }
        }
        unlockedAchievementsList.setAdapter(new AchievementAdapter(getActivity(), MainActivity.unlockedAchievements));
    }

    private void init(View viev) {
        gameTexTSummaries = getResources().getStringArray(R.array.game_summary);
        correctAnswers = (TextView) viev.findViewById(R.id.summaryGameStatistics);
        textViewSummaries = (TextView) viev.findViewById(R.id.textSummaries);
        goBackToMenuButton = (FButton) viev.findViewById(R.id.go_back_to_menu);
        exitGameButton = (FButton) viev.findViewById(R.id.exit_game);
        unlockedLayout = (LinearLayout) viev.findViewById(R.id.unlockedLayout);
        unlockedAchievementsList = (NonScrollListView) viev.findViewById(R.id.unlockedSummariesList);
        unlockedLayout.setVisibility(View.INVISIBLE);
    }

    private void AddMenuButtonListener() {
        goBackToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMenu();
            }
        });
        exitGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    private void TextColorSummary(float percentAnswers) {
        if (percentAnswers < 30) {
            correctAnswers.setTextColor(MainActivity.RED_COLOR);
            textViewSummaries.setText(gameTexTSummaries[0]);
        } else if (percentAnswers >= 30 && percentAnswers < 75) {
            correctAnswers.setTextColor(MainActivity.ORANGE_COLOR);
            textViewSummaries.setText(gameTexTSummaries[1]);
        } else {
            correctAnswers.setTextColor(MainActivity.GREEN_COLOR);
            textViewSummaries.setText(gameTexTSummaries[2]);
        }
    }
    public void BackToMenu(){
                getActivity().getSupportFragmentManager().beginTransaction().detach(this).commit();
                MainMenu main_menu_fragment = new MainMenu();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_container, main_menu_fragment);
                transaction.commit();
    }
}
