package com.example.stacjonarny.graulamki.Classes;

/**
 * Created by Piter on 15/04/2016.
 */
public class DifficultLevel {
    private String level;
    private float timeToAnswer;
    private int questionCount;
    private String backgroundColor = "";

    public DifficultLevel(String level, float timeToAnswer, int questionCount, String backgroundColor) {
        this.level = level;
        this.timeToAnswer = timeToAnswer;
        this.questionCount = questionCount;
        this.backgroundColor = backgroundColor;
    }

    public DifficultLevel(String level, float timeToAnswer, int questionCount) {
        this.level = level;
        this.timeToAnswer = timeToAnswer;
        this.questionCount = questionCount;
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
