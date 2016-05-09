package com.example.stacjonarny.graulamki.fragments;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class Game extends Fragment {

    public enum QuestionType {
        DIVIDE,
        MULTIPLY
    }

    public static final String GAME_SUMMARY_TAG = "GAME_SUMMARY";

    private static final MediaPlayer GOOD_ANSWER_SOUND = MediaPlayer.create(MainActivity.mainContext, R.raw.game_correct_answer);
    private static final MediaPlayer WRONG_ANSWER_SOUND = MediaPlayer.create(MainActivity.mainContext, R.raw.game_wrong_answer);
    private static final MediaPlayer END_GAME_SOUND = MediaPlayer.create(MainActivity.mainContext, R.raw.end_game);

    private ImageView soundIcon;
    private Button goBackButton;
    private TextView gameQuestion;
    private CountDownTimer answerTimer;
    private LinearLayout answerLayout1;
    private LinearLayout answerLayout2;
    private AnswerButton[] answerButtons;
    private BootstrapProgressBar progressBar;
    private TextView verdictText;
    private boolean nextQuestion = true;
    boolean gameEnded = false;
    private DonutProgress circleTimer;
    private int [] randoms;

    private final int VERDICT_TIME = 3000; // DEFAULT 3000 MILI SECONDS

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
                BackPressed();
            }
        });
    }

    private void init(View view) {
        soundIcon = (ImageView) view.findViewById(R.id.gameSound);
        goBackButton = (Button) view.findViewById(R.id.go_back_to_menu);
        gameQuestion = (TextView) view.findViewById(R.id.gameQuestion);
        answerLayout1 = (LinearLayout) view.findViewById(R.id.answerLayout1);
        answerLayout2 = (LinearLayout) view.findViewById(R.id.answerLayout2);
        progressBar = (BootstrapProgressBar) view.findViewById(R.id.timeRemainProgressBar2);
        progressBar.setAnimated(false);
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

        soundIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.SOUND = !MainActivity.SOUND;
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(MainActivity.SOUND_KEY, MainActivity.SOUND);
                editor.commit();
                ChangeSoundIcon();
            }
        });
        ChangeSoundIcon();
        LoadNextQuestionIfExist();
    }

    private void ChangeSoundIcon() {
        if(MainActivity.SOUND) {
            soundIcon.setImageResource(R.drawable.sound_on);
        } else {
            soundIcon.setImageResource(R.drawable.sound_off);
        }
    }

    // starting a game
    public void CreateGame() {
        TurnOffTimer();
        String levelText = MainActivity.gameDifficultLevel.getLevel();
        int levelCount =  MainActivity.gameDifficultLevel.getQuestionCount();
        int levelNum =  MainActivity.gameDifficultLevel.getLevelNum();
        float levelTime =  MainActivity.gameDifficultLevel.getTimeToAnswer();
        MainActivity.unlockedAchievements = null;
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
        try {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, summary_fragment, GAME_SUMMARY_TAG);
            transaction.commit();
        } catch (NullPointerException e) {
            Log.w("NullPointerException", "Controlled NullPointerException: " + e);
        }
        if (MainActivity.SOUND) {
            END_GAME_SOUND.start();
        }
    }

    private void ShuffleArray(int[] array)
    {
        int index, temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    // method to random answers on buttons
    public void RandomAnswerOnButtons() {
        Random r = new Random();
        randoms = new int[4];
        for(int i = 0; i < 4; i++) {
            randoms[i] = i;
        }
        ShuffleArray(randoms);
    }

    public void UpdateButtonsText(Question question)
    {
        answerButtons[randoms[0]].setText(question.getAnswer());
        answerButtons[randoms[1]].setText(question.getIncorrectAnswer1());
        answerButtons[randoms[2]].setText(question.getIncorrectAnswer2());
        answerButtons[randoms[3]].setText(question.getIncorrectAnswer3());
        answerButtons[randoms[0]].setIsCorrect(true);
        answerButtons[randoms[1]].setIsCorrect(MainActivity.DEBUG_MODE);
        answerButtons[randoms[2]].setIsCorrect(MainActivity.DEBUG_MODE);
        answerButtons[randoms[3]].setIsCorrect(MainActivity.DEBUG_MODE);
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
        TurnOffTimer();
        progressBar.setProgress(100);
        answerTimer = new CountDownTimer(time, 100) {
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
        if(MainActivity.SOUND){
            GOOD_ANSWER_SOUND.start();
        }
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
        if(MainActivity.SOUND) {
            WRONG_ANSWER_SOUND.start();
        }
        verdictText.setText(getResources().getString(R.string.inCorrectAnswer));
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
            final AnswerButton button = (AnswerButton) v;
            TurnOffTimer();
            if (button.isCorrect()) {
                GoodAnswer();
                MainActivity.gameState.questionsList.get(MainActivity.gameState.getCurrentTask() - 2).setIsCorrectAnswer(true);
            }
            else
            {
                WrongAnswer();
            }
            button.setEnabled(false);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!gameEnded)
                    {
                        LoadNextQuestionIfExist();
                        button.setEnabled(true);
                    }
                }
            }, VERDICT_TIME);
        }
    }

    public boolean BackPressed(){

        AlertDialog show = new AlertDialog.Builder(getContext())
                .setTitle(R.string.alert_dialog_game)
                .setMessage(R.string.end_game)
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
        return true;
    }
    public void EndGame(){
        gameEnded = true;
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


