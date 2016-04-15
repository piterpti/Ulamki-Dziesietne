package com.example.stacjonarny.graulamki.Classes;

/**
 * Created by Piter on 15/04/2016.
 */
public class MyNumber {
    public int number;
    public int comma; // -4,-3,-2,-1,1,2,3,4,5;

    public MyNumber(int number, int comma) {
        this.number = number;
        this.comma = comma;
    }

    @Override
    public String toString() {
        String s = "";
        if(comma < 0)
        {
            s += "0.";
            comma = -comma;
            for(int i = 0; i < comma - 1; i++)
            {
                s += "0";
            }
            s += number + "";
            comma = -comma;
        }
        else
        {
            s += number;
            for(int i = 0; i < comma;i++)
            {
                s += "0";
            }
        }
        return s;
    }
}
