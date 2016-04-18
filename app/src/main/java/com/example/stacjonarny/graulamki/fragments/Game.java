package com.example.stacjonarny.graulamki.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.*;

import com.example.stacjonarny.graulamki.Classes.AnswerButton;
import com.example.stacjonarny.graulamki.Classes.DifficultLevel;
import com.example.stacjonarny.graulamki.Classes.GameState;
import com.example.stacjonarny.graulamki.Classes.QuestionGenerator;
import com.example.stacjonarny.graulamki.Classes.Question;
import com.example.stacjonarny.graulamki.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Game extends Fragment {

    public static GameState gameState;
    private Button goBackButton;
    private TextView gameTaskProgress;
    private TextView gameQuestion;
    private CountDownTimer answerTimer;
    private LinearLayout answerLayout1;
    private LinearLayout answerLayout2;
    private AnswerButton[] answerButtons;
    private ProgressBar progressBar;
    private TextView verdictText;

    public Game() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        goBackButton = (Button) view.findViewById(R.id.go_back_to_difficulty_levels);
        gameTaskProgress = (TextView) view.findViewById(R.id.taskProgress);
        gameQuestion = (TextView) view.findViewById(R.id.gameQuestion);
        answerLayout1 = (LinearLayout) view.findViewById(R.id.answerLayout1);
        answerLayout2 = (LinearLayout) view.findViewById(R.id.answerLayout2);
        progressBar = (ProgressBar) view.findViewById(R.id.timeRemainProgressBar);
        verdictText = (TextView) view.findViewById(R.id.verdictText);
        answerButtons = new AnswerButton[4];
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i] = new AnswerButton(getActivity());
            answerButtons[i].setOnClickListener(new AnswerButtonHandler());
        }
        answerLayout1.addView(answerButtons[0]);
        answerLayout1.addView(answerButtons[1]);
        answerLayout2.addView(answerButtons[2]);
        answerLayout2.addView(answerButtons[3]);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        TurnOffTimer();
    }

    // starting a game
    public void CreateGame() {
        String levelText = getArguments().getString(StartGameFragment.KEY_CHOSEN_LEVEL_TEXT);
        int levelCount = getArguments().getInt(StartGameFragment.KEY_CHOSEN_LEVEL_COUNT);
        float levelTime = getArguments().getFloat(StartGameFragment.KEY_CHOSEN_LEVEL_TIME);

        gameState = new GameState();
        gameState.setDifficultLevel(new DifficultLevel(levelText, levelTime, levelCount));
        gameTaskProgress.setText(getResources().getString(R.string.taskText) + ": " + gameState.getCurrentTask() + "/" + levelCount);
        GenerateQuestions();
        LoadNextQuestionIfExist();
    }

    public boolean LoadNextQuestionIfExist() {
        // if no more question go to gameSummary fragment
        if (gameState.getCurrentTask() - 1 >= gameState.questionsList.size()) {
            GameSummary summary_fragment = new GameSummary();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, summary_fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return false;
        }
        // else load next question
        verdictText.setVisibility(View.GONE);
        for(Button b : answerButtons)
        {
            b.setVisibility(View.VISIBLE);
            b.setEnabled(true);
        }
        progressBar.setVisibility(View.VISIBLE);
        gameQuestion.setVisibility(View.VISIBLE);

        Question question = gameState.questionsList.get(gameState.getCurrentTask() - 1);
        gameTaskProgress.setText(getResources().getString(R.string.taskText) + ": " + gameState.getCurrentTask() + "/" + gameState.getDifficultLevel().getQuestionCount());
        gameState.nextTask();
        gameQuestion.setText(question.questionDivideWithoutAnswer());
        RandomAnswerOnButtons(question);
        CreateTimer((int) gameState.getDifficultLevel().getTimeToAnswer() * 1000);
        return true;
    }

    // method to random answers on buttons
    public void RandomAnswerOnButtons(Question question) {
        Random r = new Random();
        int random1 = r.nextInt(4);
        int random2, random3, random4;
        random2 = random3 = random4 = random1;
        answerButtons[random1].setText(question.getDivideAnswer());
        Log.d("piotrek", answerButtons[random1].getText() + "");
        answerButtons[random1].setIsCorrect(true);
        while (random1 == random2) {
            random2 = r.nextInt(4);
        }
        while (random3 == random2 || random3 == random1) {
            random3 = r.nextInt(4);
        }
        while (random4 == random1 || random4 == random2 || random4 == random3) {
            random4 = r.nextInt(4);
        }

        answerButtons[random2].setText(question.getIncorrectDivideAnswer1());
        answerButtons[random3].setText(question.getIncorrectDivideAnswer2());
        answerButtons[random4].setText(question.getIncorrectDivideAnswer3());
        answerButtons[random2].setIsCorrect(false);
        answerButtons[random3].setIsCorrect(false);
        answerButtons[random4].setIsCorrect(false);

    }

    // method to generate question and adding it to list
    public void GenerateQuestions() {
        for (int i = 0; i < gameState.getDifficultLevel().getQuestionCount(); i++) {
            gameState.questionsList.add(QuestionGenerator.generateQuestion(Question.QUESTION_DIVIDE));
        }
    }

    // create time counter
    public void CreateTimer(int time) {
        progressBar.setProgress(100);
        TurnOffTimer();
        answerTimer = new CountDownTimer(time, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                float ppp = millisUntilFinished / gameState.getDifficultLevel().getTimeToAnswer() / 10;
                progressBar.setProgress((int) ppp);
            }

            @Override
            public void onFinish() {
                WrongAnswer();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoadNextQuestionIfExist();
                    }
                }, 3000);
            }
        };
        answerTimer.start();
    }

    private void TurnOffTimer() {
        if(answerTimer != null) {
            answerTimer.cancel();
            answerTimer = null;
        }
    }

    public void GoodAnswer()
    {
        verdictText.setText(getResources().getString(R.string.correctAnswer));
        verdictText.setVisibility(View.VISIBLE);
        for(AnswerButton b : answerButtons)
        {
            if(!b.isCorrect())
            {
                b.setVisibility(View.INVISIBLE);

            }
            else
                b.setEnabled(false);
        }
        progressBar.setVisibility(View.GONE);
    }

    public void WrongAnswer()
    {
        verdictText.setText(getResources().getString(R.string.incorrectAnswer));
        verdictText.setVisibility(View.VISIBLE);
        gameQuestion.setVisibility(View.INVISIBLE);
        for(Button b : answerButtons)
        {
            b.setVisibility(View.INVISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    // listener to answer buttons
    public class AnswerButtonHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            AnswerButton button = (AnswerButton) v;
            TurnOffTimer();
            if (button.isCorrect()) {
                GoodAnswer();
                gameState.questionsList.get(gameState.getCurrentTask() - 2).setIsCorrectAnswer(true);
            }
            else
            {
                WrongAnswer();
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoadNextQuestionIfExist();
                }
            }, 3000);
        }
    }
}


