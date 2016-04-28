package com.example.stacjonarny.graulamki.fragments;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.example.stacjonarny.graulamki.Classes.AnswerButton;
import com.example.stacjonarny.graulamki.Classes.DifficultLevel;
import com.example.stacjonarny.graulamki.Classes.GameState;
import com.example.stacjonarny.graulamki.Classes.Questions.QuestionGenerator;
import com.example.stacjonarny.graulamki.Classes.Questions.Question;
import com.example.stacjonarny.graulamki.MainActivity;
import com.example.stacjonarny.graulamki.R;
import com.github.lzyzsd.circleprogress.DonutProgress;

import info.hoang8f.widget.FButton;

public class Game extends Fragment {

    public enum QuestionType {
        DIVIDE,
        MULTIPLY
    }

    private static final MediaPlayer GOOD_ANSWER_SOUND = MediaPlayer.create(MainActivity.mainContext, R.raw.game_correct_answer);
    private static final MediaPlayer WRONG_ANSWER_SOUND = MediaPlayer.create(MainActivity.mainContext, R.raw.game_wrong_answer);
    private static final MediaPlayer END_GAME_SOUND = MediaPlayer.create(MainActivity.mainContext, R.raw.end_game);

    private Button goBackButton;
    private TextView gameTaskProgress;
    private TextView gameQuestion;
    private CountDownTimer answerTimer;
    private LinearLayout answerLayout1;
    private LinearLayout answerLayout2;
    private AnswerButton[] answerButtons;
    private BootstrapProgressBar progressBar;
    private TextView verdictText;
    private boolean nextQuestion = true;
    private int random1, random2, random3, random4;
    boolean gameEnded = false;
    private DonutProgress circleTimer;

    private final int VERDICT_TIME = 3000; // DEFAULT 3000 MILISECONDS

    public Game() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        init(view);
        AddMenuButtonListener();
        LoadNextQuestionIfExist();

        return view;
    }

    private void AddMenuButtonListener() {
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackPresed();
            }
        });
    }

    private void init(View view) {
        goBackButton = (Button) view.findViewById(R.id.go_back_to_menu);
        gameQuestion = (TextView) view.findViewById(R.id.gameQuestion);
        answerLayout1 = (LinearLayout) view.findViewById(R.id.answerLayout1);
        answerLayout2 = (LinearLayout) view.findViewById(R.id.answerLayout2);
        progressBar = (BootstrapProgressBar) view.findViewById(R.id.timeRemainProgressBar2);
        circleTimer = (DonutProgress) view.findViewById(R.id.donutProgressDon);
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
        //test button
        final FButton testButton = (FButton) view.findViewById(R.id.asw00);
        testButton.setButtonColor(ContextCompat.getColor(getActivity(), R.color.bootstrap_brand_danger));
        testButton.setShadowColor(ContextCompat.getColor(getActivity(), R.color.bootstrap_gray_lighter));
        testButton.setShadowEnabled(true);
        testButton.setShadowHeight(5);
        testButton.setCornerRadius(20);
        testButton.setText("odpowiedź");
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testButton.setButtonColor(ContextCompat.getColor(getActivity(), R.color.holo_green_light));
                testButton.setShadowColor(ContextCompat.getColor(getActivity(), R.color.mb_green_dark));
            }
        });
        //

        circleTimer.setProgress(0);
        circleTimer.setMax((MainActivity.gameState.getDifficultLevel().getQuestionCount()));
        circleTimer.setSuffixText(" / " + String.valueOf(MainActivity.gameState.getDifficultLevel().getQuestionCount()));

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog show = new AlertDialog.Builder(getContext())
                        .setTitle(R.string.alertMessageGame)
                        .setMessage(R.string.alertMessageExit)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                EndGame();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
        LoadNextQuestionIfExist();
    }

    // starting a game
    public void CreateGame() {
        TurnOffTimer();
        String levelText = MainActivity.gameDifficultLevel.getLevel();
        int levelCount =  MainActivity.gameDifficultLevel.getQuestionCount();
        int levelNum =  MainActivity.gameDifficultLevel.getLevelNum();
        float levelTime =  MainActivity.gameDifficultLevel.getTimeToAnswer();
        MainActivity.gameState = new GameState();
        MainActivity.gameState.setDifficultLevel(new DifficultLevel(levelText, levelTime, levelCount, levelNum));
        GenerateQuestions();
        nextQuestion = true;
    }

    public boolean LoadNextQuestionIfExist() {
        // if no more question go to gameSummary fragment
        if (MainActivity.gameState.getCurrentTask() - 1 >= MainActivity.gameState.questionsList.size()) {
            GoToGameSummary();
            return false;
        }
        // else load next question
        verdictText.setVisibility(View.GONE);
        for(Button b : answerButtons)
        {
            b.getBackground().setColorFilter(MainActivity.BUTTON_DEFAULT_COLOR, PorterDuff.Mode.MULTIPLY);
            b.setVisibility(View.VISIBLE);
            b.setClickable(true);
        }
        progressBar.setVisibility(View.VISIBLE);
        gameQuestion.setVisibility(View.VISIBLE);
        Question question = MainActivity.gameState.questionsList.get(MainActivity.gameState.getCurrentTask() - 1);
        //gameTaskProgress.setText(getResources().getString(R.string.taskText) + ": " + MainActivity.gameState.getCurrentTask() + "/" + MainActivity.gameState.getDifficultLevel().getQuestionCount());
        circleTimer.setProgress(MainActivity.gameState.getCurrentTask());
        gameQuestion.setText(question.questionWithoutAnswer());
        if(nextQuestion)
        {
            RandomAnswerOnButtons();
            CreateTimer((int) MainActivity.gameState.getDifficultLevel().getTimeToAnswer() * 1000);
            nextQuestion = false;
        }
        UpdateButtonsText(question);
        return true;
    }

    private void GoToGameSummary() {
        GameSummary summary_fragment = new GameSummary();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, summary_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        END_GAME_SOUND.start();
    }

    // method to random answers on buttons
    public void RandomAnswerOnButtons() {
        Random r = new Random();
        random1 = r.nextInt(4);
        random2 = random3 = random4 = random1;
        while (random1 == random2) {
            random2 = r.nextInt(4);
        }
        while (random3 == random2 || random3 == random1) {
            random3 = r.nextInt(4);
        }
        while (random4 == random1 || random4 == random2 || random4 == random3) {
            random4 = r.nextInt(4);
        }
    }

    public void UpdateButtonsText(Question question)
    {
        answerButtons[random1].setText(question.getAnswer());
        answerButtons[random2].setText(question.getIncorrectAnswer1());
        answerButtons[random3].setText(question.getIncorrectAnswer2());
        answerButtons[random4].setText(question.getIncorrectAnswer3());
        answerButtons[random1].setIsCorrect(true);
        answerButtons[random2].setIsCorrect(false);
        answerButtons[random3].setIsCorrect(false);
        answerButtons[random4].setIsCorrect(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TurnOffTimer();
    }

    // method to generate question and adding it to list
    public void GenerateQuestions() {
        Random random = new Random();
        QuestionType type;
        for (int i = 0; i < MainActivity.gameState.getDifficultLevel().getQuestionCount(); i++) {
            if(random.nextInt(2) == 0)
            {
                type = QuestionType.MULTIPLY;
            }
            else
            {
                type = QuestionType.DIVIDE;
            }
            MainActivity.gameState.questionsList.add(QuestionGenerator.generateQuestion(type));
        }
    }

    // create time counter
    public void CreateTimer(int time) {
        progressBar.setProgress(100);
        TurnOffTimer();
        answerTimer = new CountDownTimer(time, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                float ppp = millisUntilFinished / MainActivity.gameState.getDifficultLevel().getTimeToAnswer() / 10;
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
                }, VERDICT_TIME);
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
        GOOD_ANSWER_SOUND.start();
        verdictText.setText(getResources().getString(R.string.correctAnswer));
        verdictText.setVisibility(View.VISIBLE);
        verdictText.setTextColor(MainActivity.GREEN_COLOR);
        for(AnswerButton b : answerButtons)
        {
            if(!b.isCorrect())
            {
                b.setVisibility(View.INVISIBLE);
            }
            else
            {
                b.getBackground().setColorFilter(MainActivity.GREEN_COLOR, PorterDuff.Mode.MULTIPLY);
                b.setClickable(false);
            }
        }
        progressBar.setVisibility(View.GONE);
        MainActivity.gameState.nextTask();
        nextQuestion = true;
    }

    public void WrongAnswer()
    {
        WRONG_ANSWER_SOUND.start();
        verdictText.setText(getResources().getString(R.string.incorrectAnswer));
        verdictText.setVisibility(View.VISIBLE);
        gameQuestion.setVisibility(View.INVISIBLE);
        verdictText.setTextColor(MainActivity.RED_COLOR);
        for(Button b : answerButtons)
        {
            b.setVisibility(View.INVISIBLE);
        }
        progressBar.setVisibility(View.GONE);
        MainActivity.gameState.nextTask();
        nextQuestion = true;
    }

    // listener to answer buttons
    public class AnswerButtonHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            AnswerButton button = (AnswerButton) v;
            TurnOffTimer();
            if (button.isCorrect()) {
                GoodAnswer();
                MainActivity.gameState.questionsList.get(MainActivity.gameState.getCurrentTask() - 2).setIsCorrectAnswer(true);
            }
            else
            {
                WrongAnswer();
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!gameEnded)
                    {
                        LoadNextQuestionIfExist();
                    }
                }
            }, VERDICT_TIME);
        }
    }

    public boolean BackPresed(){

        AlertDialog show = new AlertDialog.Builder(getContext())
                .setTitle("Rozgrywka")
                .setMessage("Zakończy grę?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("log", "click zamknij");
                        gameEnded = true;
                        EndGame();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("log","click cancel");
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return true;
    }
    public void EndGame(){
        getActivity().getSupportFragmentManager().beginTransaction().detach(this).commit();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        MainMenu main_menu_fragment = new MainMenu();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, main_menu_fragment);
        transaction.commit();
        TurnOffTimer();
    }
}


