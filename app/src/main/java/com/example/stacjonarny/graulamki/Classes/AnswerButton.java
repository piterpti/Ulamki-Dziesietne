package com.example.stacjonarny.graulamki.Classes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.stacjonarny.graulamki.MainActivity;


public class AnswerButton extends Button {

    private boolean isCorrect;

    public AnswerButton(Context context) {
        super(context);
        isCorrect = false;
        setSingleLine(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        params.setMargins(5, 20, 5 ,20);
        params.weight =  0.3f;
        setLayoutParams(params);
        getBackground().setColorFilter(MainActivity.BUTTON_DEFAULT_COLOR, PorterDuff.Mode.MULTIPLY);
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
