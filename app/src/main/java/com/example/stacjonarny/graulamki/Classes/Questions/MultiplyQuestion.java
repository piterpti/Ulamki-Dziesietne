package com.example.stacjonarny.graulamki.Classes.Questions;

import com.example.stacjonarny.graulamki.Classes.MyNumber;

/**
 * Created by Piter on 18/04/2016.
 */
public class MultiplyQuestion extends Question {


    public MultiplyQuestion(MyNumber firstExpression, MyNumber secondExpression) {
        super(firstExpression, secondExpression);
    }

    @Override
    public String getAnswer() {
        int val = firstExpression.number * secondExpression.number;
        int comma = firstExpression.comma + secondExpression.comma;
        return new MyNumber(val, comma).toString();
    }

    @Override
    public MyNumber getAnswerMyNum() {
        int val = firstExpression.number * secondExpression.number;
        int comma = firstExpression.comma + secondExpression.comma;
        return new MyNumber(val, comma);
    }

    @Override
    public String questionWithoutAnswer() {
        return firstExpression.toString() + " * " + secondExpression.toString();
    }

    @Override
    public String getIncorrectAnswer1() {
        int val = firstExpression.number / secondExpression.number;
        int comma = firstExpression.comma - secondExpression.comma;
        return new MyNumber(val, comma).toString();
    }

    @Override
    public String getIncorrectAnswer2() {
        int val = firstExpression.number * secondExpression.number;
        int comma = firstExpression.comma + secondExpression.comma + lossNumberToAnswer;
        return new MyNumber(val, comma).toString();
    }

    @Override
    public String getIncorrectAnswer3() {
        int val = firstExpression.number * secondExpression.number;
        int comma = firstExpression.comma + secondExpression.comma - lossNumberToAnswer;
        return new MyNumber(val, comma).toString();
    }
}
