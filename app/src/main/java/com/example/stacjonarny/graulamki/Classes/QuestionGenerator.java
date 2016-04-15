package com.example.stacjonarny.graulamki.Classes;
import android.util.Log;

import java.util.*;
/**
 * Created by Piter on 15/04/2016.
 */
public class QuestionGenerator {

    private static Random r = new Random();

    public static Question generateQuestion(int type)
    {
        int i1;
        int i2;
        do
        {
            i1 = r.nextInt(9) + 1; // loss number from range 1-9
            i2 = r.nextInt(i1) + 1;
        }
        while(i1 % i2 != 0);
        MyNumber ex1 = converToRange(i1);
        MyNumber ex2 = converToRange(i2);
        Question q = new Question(ex1, ex2, type);
        return q;
    }

    public static MyNumber converToRange(int number)
    {
        int comma = r.nextInt(10) - 4; // loss number form range -4 : 5
        return new MyNumber(number, comma);
    }

}
