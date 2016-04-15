package com.example.stacjonarny.graulamki.fragments;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.stacjonarny.graulamki.Classes.DifficultLevel;
import com.example.stacjonarny.graulamki.Classes.GameState;
import com.example.stacjonarny.graulamki.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Game extends Fragment {

    private GameState gameState;
    private TextView timeRemain;
    private Button goBackButton;
    private TextView gameTaskProgress;

    public Game() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        timeRemain = (TextView) view.findViewById(R.id.time_remain);
        goBackButton = (Button) view.findViewById(R.id.go_back_to_difficulty_levels);
        gameTaskProgress = (TextView) view.findViewById(R.id.taskProgress);

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenu main_menu_fragment = new MainMenu();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, main_menu_fragment);
                transaction.commit();
            }
        });
        CreateGame();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void CreateGame()
    {
        String levelText = getArguments().getString(StartGameFragment.KEY_CHOSEN_LEVEL_TEXT);
        int levelCount = getArguments().getInt(StartGameFragment.KEY_CHOSEN_LEVEL_COUNT);
        float levelTime = getArguments().getFloat(StartGameFragment.KEY_CHOSEN_LEVEL_TIME);
        Log.d("alamakota", levelText);

        gameState = new GameState();
        gameState.setDifficultLevel(new DifficultLevel(levelText, levelTime, levelCount));

        gameTaskProgress.setText(getResources().getString(R.string.taskText) + ": " + gameState.getCurrentTask() + "/" + levelCount);

        CreateTimer((int) gameState.getDifficultLevel().getTimeToAnswer() * 1000);
    }

    public void CreateTimer(int time)
    {
        new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                timeRemain.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timeRemain.setText("time end!");
            }
        }.start();
    }

}
