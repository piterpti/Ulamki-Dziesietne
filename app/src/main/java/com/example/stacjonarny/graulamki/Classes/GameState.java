package com.example.stacjonarny.graulamki.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.*;

public class GameState {

    private DifficultLevel difficultLevel;
    private int currentTask;
    private int goodAnswers;
    public ArrayList<Question> questionsList;

    public GameState() {
        currentTask = 1;
        questionsList = new ArrayList<Question>();
    }

    public void setDifficultLevel(DifficultLevel difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public void setCurrentTask(int currentTask) {
        this.currentTask = currentTask;
    }

    public int getCurrentTask() {
        return currentTask;
    }

    public  void nextTask()
    {
        currentTask++;
    }

    public DifficultLevel getDifficultLevel() {
        return difficultLevel;
    }

    public int getCorrectAnswerCount()
    {
        int correctAnswers = 0;
        for(Question q : questionsList)
        {
            if(q.isCorrectAnswer())
            {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    public int getCorrectAnswersRowCount()
    {
        int maxCorrectAnswers = 0;
        int correctAnswers = 0;
        for(Question q : questionsList)
        {
            if(q.isCorrectAnswer())
            {
                correctAnswers++;
            }
            else
            {
                if(maxCorrectAnswers < correctAnswers)
                {
                    maxCorrectAnswers = correctAnswers;
                }
                correctAnswers = 0;
            }
        }
        return maxCorrectAnswers;
    }
}
