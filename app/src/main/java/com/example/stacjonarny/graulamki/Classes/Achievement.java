package com.example.stacjonarny.graulamki.Classes;



import com.example.stacjonarny.graulamki.MainActivity;

import java.io.Serializable;

/**
 * Created by Piter on 21/04/2016.
 */
public class Achievement implements Serializable, Comparable<Achievement>{

    private String name;
    private boolean locked;
    private final int correctAnswersRow;
    private int status;
    private int difficultLevel;

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

    private void Unlock()
    {
        locked = false;
        MainActivity.achievementDbHelper.updateAchievement(name, locked, correctAnswersRow, status, difficultLevel);
    }

    public boolean Check(int correctAnswersRow)
    {
        if(correctAnswersRow >= this.correctAnswersRow) {
            Unlock();
        }
        return locked;
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
    public boolean equals(Object o) {
        Achievement a = (Achievement) o;
        if(a.getName().equals(a))
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "name='" + name + '\'' +
                ", locked=" + locked +
                ", correctAnswersRow=" + correctAnswersRow +
                ", status=" + status +
                ", difficultLevel=" + difficultLevel +
                '}';
    }

    @Override
    public int compareTo(Achievement another) {
        if(difficultLevel == another.getDifficultLevel()) {
            return correctAnswersRow > another.getCorrectAnswersRow() ? 1 : -1;
        }
        return  difficultLevel > another.getDifficultLevel() ? 1 : -1;
    }
}
