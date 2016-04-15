package com.example.stacjonarny.graulamki.Classes;

/**
 * Created by Piter on 15/04/2016.
 */
public class GameState {

    private DifficultLevel difficultLevel;
    private int currentTask;

    public GameState() {
        currentTask = 1;
    }

    public void setDifficultLevel(DifficultLevel difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public void setCurrentTask(int currentTask) {
        this.currentTask = currentTask;
    }

    public int getCurrentTask() {
        return currentTask;
    }

    public  void nextTask()
    {
        currentTask++;
    }

    public DifficultLevel getDifficultLevel() {
        return difficultLevel;
    }
}
