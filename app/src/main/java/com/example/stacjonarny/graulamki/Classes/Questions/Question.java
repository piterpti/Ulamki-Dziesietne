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

    public String spaceBetweenDigits(String toEdit)
    {
        int counter = -1;
        String withSpaces = "";
        for(int i = toEdit.length() - 1; i >= 0; i--)
        {
            if(toEdit.charAt(i) == '.')
            {
                counter = 0;
            } else {
                counter++;
            }

            if(counter >= 3) {
                counter = 0;
                withSpaces += " ";
            }
            withSpaces += toEdit.charAt(i);

        }

        return new StringBuilder(withSpaces).reverse().toString();
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
