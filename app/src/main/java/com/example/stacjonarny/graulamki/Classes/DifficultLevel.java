package com.example.stacjonarny.graulamki.Classes;

/**
 * Created by Piter on 15/04/2016.
 */
public class DifficultLevel {
    private String level;
    private int levelNum;
    private float timeToAnswer;
    private int questionCount;
    private String backgroundColor = "";

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    private boolean isActive = false;

    public DifficultLevel(String level, float timeToAnswer, int questionCount, int levelNum, String backgroundColor) {
        this.level = level;
        this.timeToAnswer = timeToAnswer;
        this.questionCount = questionCount;
        this.backgroundColor = backgroundColor;
        this.levelNum = levelNum;
    }

    public DifficultLevel(String level, float timeToAnswer, int questionCount, int levelNum) {
        this.level = level;
        this.timeToAnswer = timeToAnswer;
        this.questionCount = questionCount;
        this.levelNum = levelNum;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public String getLevel() {
        return level;
    }

    public float getTimeToAnswer() {
        return timeToAnswer;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }
}
