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
import android.widget.LinearLayout;
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

    private GameState gameState;
    private TextView timeRemain;
    private Button goBackButton;
    private TextView gameTaskProgress;
    private TextView gameQuestion;
    private ArrayList<Question> questionsList;
    private int timeToAnswer = 0;


    private LinearLayout answerLayout1;
    private LinearLayout answerLayout2;
    private AnswerButton[] answerButtons;

    public Game() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        timeRemain = (TextView) view.findViewById(R.id.time_remain);
        goBackButton = (Button) view.findViewById(R.id.go_back_to_difficulty_levels);
        gameTaskProgress = (TextView) view.findViewById(R.id.taskProgress);
        gameQuestion = (TextView) view.findViewById(R.id.gameQuestion);
        questionsList = new ArrayList<>();
        answerLayout1 = (LinearLayout) view.findViewById(R.id.answerLayout1);
        answerLayout2 = (LinearLayout) view.findViewById(R.id.answerLayout2);
        answerButtons = new AnswerButton[4];
        for(int i = 0; i < answerButtons.length; i++)
        {
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

    public void CreateGame()
    {
        String levelText = getArguments().getString(StartGameFragment.KEY_CHOSEN_LEVEL_TEXT);
        int levelCount = getArguments().getInt(StartGameFragment.KEY_CHOSEN_LEVEL_COUNT);
        float levelTime = getArguments().getFloat(StartGameFragment.KEY_CHOSEN_LEVEL_TIME);

        gameState = new GameState();
        gameState.setDifficultLevel(new DifficultLevel(levelText, levelTime, levelCount));
        gameTaskProgress.setText(getResources().getString(R.string.taskText) + ": " + gameState.getCurrentTask() + "/" + levelCount);
        CreateTimer((int) gameState.getDifficultLevel().getTimeToAnswer() * 1000);
        GenerateQuestions();
        LoadNextQuestionIfExist();
    }

    public boolean LoadNextQuestionIfExist()
    {
        if(gameState.getCurrentTask() - 1 >= questionsList.size())
        {
            return false;
        }
        Question question = questionsList.get(gameState.getCurrentTask() - 1);
        gameTaskProgress.setText(getResources().getString(R.string.taskText) + ": " + gameState.getCurrentTask() + "/" + gameState.getDifficultLevel().getQuestionCount());
        gameState.nextTask();
        gameQuestion.setText(question.questionDivideWithoutAnswer());
        RandomAnswerOnButtons(question);
        return true;
    }

    public void RandomAnswerOnButtons(Question question)
    {
        Random r = new Random();
        int random1 = r.nextInt(4);
        int random2, random3, random4;
        random2 = random3 = random4 = random1;
        answerButtons[random1].setText(question.getDivideAnswer());
        answerButtons[random1].setIsCorrect(true);
        while(random1 == random2)
            random2 = r.nextInt(4);
        answerButtons[random2].setText(question.getIncorrectDivideAnswer1());
        while(random3 == random2 || random3 == random1)
            random3 = r.nextInt(4);
        answerButtons[random3].setText(question.getIncorrectDivideAnswer2());
        while(random4 == random1 || random4 == random2 || random4 == random3)
            random4 = r.nextInt(4);
        answerButtons[random4].setText(question.getIncorrectDivideAnswer3());
    }

    public void GenerateQuestions()
    {
        for(int i = 0; i < gameState.getDifficultLevel().getQuestionCount(); i++)
        {
            questionsList.add(QuestionGenerator.generateQuestion(Question.QUESTION_DIVIDE));
        }
        Log.d("alamakota",questionsList.size() + "");
    }

    public void CreateTimer(int time)
    {
        new CountDownTimer(time + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeRemain.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timeRemain.setText("time end!");
            }
        }.start();
    }

    public class AnswerButtonHandler implements View.OnClickListener{

        @Override
        public void onClick(View v)
        {
            AnswerButton button = (AnswerButton) v;

            if(button.isCorrect())
            {
                questionsList.get(gameState.getCurrentTask() - 2).setIsCorrectAnswer(true);
            }
            LoadNextQuestionIfExist();
            printListAnswers();
        }
    }

    public void printListAnswers()
    {
        Log.d("piotrek", "START");
        for(int i = 0; i < questionsList.size(); i++)
        {
            String s = questionsList.get(i).isCorrectAnswer() ? "true" : "false";
            Log.d("piotrek", s);
        }
    }

}


