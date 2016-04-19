package com.example.stacjonarny.graulamki.Classes;

import com.example.stacjonarny.graulamki.fragments.Game;

import java.util.*;
/**
 * Created by Piter on 15/04/2016.
 */
public class QuestionGenerator {

    private static Random r = new Random();

    public static Question generateQuestion(Game.QuestionType type)
    {
        int i1;
        int i2;
        do
        {
            i1 = r.nextInt(9) + 1; // loss number from range 1-9
            i2 = r.nextInt(i1) + 1;
        }
        while(i1 % i2 != 0);
        MyNumber ex1 = convertToRange(i1);
        MyNumber ex2 = convertToRange(i2);
        Question q = null;
        if(type == Game.QuestionType.DIVIDE) {
            q = new DivideQuestion(ex1, ex2);
        }
        else if(type == Game.QuestionType.MULTIPLY) {
            q = new MultiplyQuestion(ex1, ex2);
        }
        return q;
    }

    public static MyNumber convertToRange(int number)
    {
        int comma = r.nextInt(10) - 4; // loss number form range -4 : 5
        if(number == 1 && comma == 0)
        {
            comma = 1;
        }
        return new MyNumber(number, comma);
    }

}
