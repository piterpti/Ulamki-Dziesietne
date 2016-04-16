package com.example.stacjonarny.graulamki.Classes;

import android.util.Log;

import java.io.RandomAccessFile;
import java.util.Random;

/**
 * Created by Piter on 15/04/2016.
 */
public class Question
{
    public static int QUESTION_MULTIPLY = 1;
    public static int QUESTION_DIVIDE = 2;

    private MyNumber firstExpression;
    private MyNumber secondExpression;
    private int questionType; // 1 - multiply QUESTION || 2 - divide QUESTION
    private boolean isCorrectAnswer;
    private int lossNumberToAnswer;




    public Question(MyNumber firstExpression, MyNumber secondExpression, int questionType) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.questionType = questionType;
        isCorrectAnswer = false;
        lossNumberToAnswer = 0;
        while(lossNumberToAnswer == 0)
        {
            lossNumberToAnswer = new Random().nextInt(5) - 2;
        }
    }

    public MyNumber getCorrectAnswer()
    {
        return null;
    }

    public void setIsCorrectAnswer(boolean isCorrectAnswer) {
        this.isCorrectAnswer = isCorrectAnswer;
    }

    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }

    public String getDivideAnswer()
    {
        int val = firstExpression.number / secondExpression.number;
        int comma = firstExpression.comma - secondExpression.comma;
        return new MyNumber(val, comma).toString();
    }

    public MyNumber getDivideAnswerMyNum()
    {
        int val = firstExpression.number / secondExpression.number;
        int comma = firstExpression.comma - secondExpression.comma;
        return new MyNumber(val, comma);
    }

    public String questionDivideWithoutAnswer()
    {
        return firstExpression.toString() + " / " + secondExpression.toString() + " = ";
    }

    public String getIncorrectDivideAnswer1()
    {
        int val = firstExpression.number * secondExpression.number;
        int comma = firstExpression.comma + secondExpression.comma;
        return new MyNumber(val, comma).toString();
    }

    public String getIncorrectDivideAnswer2()
    {
        int val = firstExpression.number / secondExpression.number;
        int comma = firstExpression.comma - secondExpression.comma + lossNumberToAnswer;
        return new MyNumber(val, comma).toString();
    }

    public String getIncorrectDivideAnswer3()
    {
        int val = firstExpression.number / secondExpression.number;
        int comma = firstExpression.comma - secondExpression.comma - lossNumberToAnswer;
        return new MyNumber(val, comma).toString();
    }
}
