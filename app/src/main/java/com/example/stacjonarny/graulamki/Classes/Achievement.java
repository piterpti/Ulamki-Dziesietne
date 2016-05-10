package com.example.stacjonarny.graulamki.Classes;



import com.example.stacjonarny.graulamki.MainActivity;

import java.io.Serializable;

/**
 * Created by Piter on 21/04/2016.
 */
public class Achievement implements Comparable<Achievement>{

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    private boolean locked;
    private final int correctAnswersRow;
    private int status;
    private final int difficultLevel;

    public Achievement(String name, int correctAnswersRow, int difficultLevel) {
        this.name = name;
        this.correctAnswersRow = correctAnswersRow;
        this.difficultLevel = difficultLevel;
        locked = false;
        status = 0;
    }

    public Achievement(String name, boolean locked, int correctAnswersRow, int status, int difficultLevel) {
        this.name = name;
        this.locked = locked;
        this.correctAnswersRow = correctAnswersRow;
        this.status = status;
        this.difficultLevel = difficultLevel;
    }

    public boolean Unlock(int correctAnswersRow)
    {
        if(!locked)
            return false;
        if(correctAnswersRow >= this.correctAnswersRow)
        {
            locked = false;
            MainActivity.achievementDbHelper.updateAchievement(name, locked, correctAnswersRow, status, difficultLevel);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void AddToStatus(int toStatus) {
        status += toStatus;
    }

    public String getName() {
        return name;
    }

    public boolean isLocked() {
        return locked;
    }

    public int getCorrectAnswersRow() {
        return correctAnswersRow;
    }

    public int getStatus() {
        return status;
    }

    public int getDifficultLevel() {
        return difficultLevel;
    }

    @Override
    public int compareTo(Achievement another) {
       /* if(locked && !another.isLocked()) {
            return 1000;
        }*/
        return correctAnswersRow > another.getCorrectAnswersRow() ? 1 : -1;


    }
}
