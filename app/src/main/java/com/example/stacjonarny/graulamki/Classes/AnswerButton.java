package com.example.stacjonarny.graulamki.Classes;

import com.example.stacjonarny.graulamki.R;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.stacjonarny.graulamki.MainActivity;

import info.hoang8f.widget.FButton;


public class AnswerButton extends FButton {

    private boolean isCorrect;

    public AnswerButton(Context context) {
        super(context);
        isCorrect = false;
        setSingleLine(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        params.setMargins(5, 20, 5 ,20);
        params.weight =  0.2f;
      //  setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.answerButtonTextSize));
        setLayoutParams(params);
        //getBackground().setColorFilter(MainActivity.BUTTON_DEFAULT_COLOR, PorterDuff.Mode.MULTIPLY);
        setSoundEffectsEnabled(false);
        //
        setButtonColor(ContextCompat.getColor(getContext(), R.color.colorButtonDefault1));
        setShadowColor(ContextCompat.getColor(getContext(), R.color.colorButtonDefault2));
        setShadowEnabled(true);
        setShadowHeight(20);
        setCornerRadius(40);
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
