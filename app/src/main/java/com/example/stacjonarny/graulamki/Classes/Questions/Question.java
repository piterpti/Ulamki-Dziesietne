package com.example.stacjonarny.graulamki.Classes.Questions;

import com.example.stacjonarny.graulamki.Classes.MyNumber;

import java.util.Random;

/**
 * Created by Piter on 15/04/2016.
 */
public abstract class Question implements QuestionMethods
{
    protected MyNumber firstExpression;
    protected MyNumber secondExpression;
    private boolean isCorrectAnswer;
    protected int lossNumberToAnswer;


    public Question(MyNumber firstExpression, MyNumber secondExpression) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        isCorrectAnswer = false;
        lossNumberToAnswer = 0;
        while(lossNumberToAnswer == 0)
        {
            lossNumberToAnswer = new Random().nextInt(5) - 2;
        }
    }

    public void setIsCorrectAnswer(boolean isCorrectAnswer) {
        this.isCorrectAnswer = isCorrectAnswer;
    }

    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }


}

interface QuestionMethods
{
    public String getAnswer();
    public MyNumber getAnswerMyNum();
    public String questionWithoutAnswer();
    public String getIncorrectAnswer1();
    public String getIncorrectAnswer2();
    public String getIncorrectAnswer3();

}
