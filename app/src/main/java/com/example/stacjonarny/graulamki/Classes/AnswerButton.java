package com.example.stacjonarny.graulamki.Classes;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Piter on 15/04/2016.
 */
public class AnswerButton extends Button {

    private boolean isCorrect;

    public AnswerButton(Context context) {
        super(context);
        isCorrect = false;
        setSingleLine(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight =  0.5f;
        setLayoutParams(params);
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
        setBackgroundColor(Color.argb(255,0,255,0));
    }

    public boolean isCorrect() {
        setBackgroundColor(Color.argb(255,150,150,150));
        return isCorrect;
    }
}
