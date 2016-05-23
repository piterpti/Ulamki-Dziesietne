package com.example.stacjonarny.graulamki.Classes.Questions;

import com.example.stacjonarny.graulamki.Classes.MyNumber;

/**
 * Created by Piter on 18/04/2016.
 */
public class DivideQuestion extends Question {

    public DivideQuestion(MyNumber firstExpression, MyNumber secondExpression) {
        super(firstExpression, secondExpression);

    }

    public String getAnswer()
    {
        int val = firstExpression.number / secondExpression.number;
        int comma = firstExpression.comma - secondExpression.comma;
        MyNumber toReturn = new MyNumber(val, comma);
        return spaceBetweenDigits(toReturn.toString(), toReturn.comma);
    }

    public MyNumber getAnswerMyNum()
    {
        int val = firstExpression.number / secondExpression.number;
        int comma = firstExpression.comma - secondExpression.comma;
        return new MyNumber(val, comma);
    }

    public String questionWithoutAnswer()
    {
        String first = super.spaceBetweenDigits(firstExpression.toString(), firstExpression.comma);
        String second = super.spaceBetweenDigits(secondExpression.toString(), secondExpression.comma);
        return (first + " / " + second);
    }

    public String getIncorrectAnswer1()
    {
        int val = firstExpression.number / secondExpression.number;
        int comma = firstExpression.comma + secondExpression.comma;
        String temp = new MyNumber(val, comma).toString();
        if(temp.equals(getAnswer()))
        {
            comma++;
            temp = new MyNumber(val, comma).toString();
        }
        while (temp.equals(getIncorrectAnswer2()) || temp.equals(getIncorrectAnswer3())){
            comma++;
            temp = new MyNumber(val, comma).toString();
        }
        return spaceBetweenDigits(temp, comma);
    }

    public String getIncorrectAnswer2()
    {
        int val = firstExpression.number / secondExpression.number;
        int comma = firstExpression.comma - secondExpression.comma + lossNumberToAnswer;
        MyNumber toReturn = new MyNumber(val, comma);
        return spaceBetweenDigits(toReturn.toString(), toReturn.comma);
    }

    public String getIncorrectAnswer3()
    {
        int val = firstExpression.number / secondExpression.number;
        int comma = firstExpression.comma - secondExpression.comma - lossNumberToAnswer;
        MyNumber toReturn = new MyNumber(val, comma);
        return spaceBetweenDigits(toReturn.toString(), toReturn.comma);
    }
}
