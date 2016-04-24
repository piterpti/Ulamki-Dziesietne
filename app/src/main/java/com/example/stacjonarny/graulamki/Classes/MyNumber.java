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
            if(number >= 10 && comma == -1) {
                s += number / 10;
                s += "." + (number - number / 10 * 10);
                return s;
            }
            else
            {
                s += "0.";
            }
            for(int i = 0; i < -comma - 1; i++)
            {
                s += "0";
            }
            s += number + "";
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
