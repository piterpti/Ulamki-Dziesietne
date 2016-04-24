package com.example.stacjonarny.graulamki;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.stacjonarny.graulamki.Classes.Achievement;
import com.example.stacjonarny.graulamki.Classes.DifficultLevel;
import com.example.stacjonarny.graulamki.Classes.GameState;
import com.example.stacjonarny.graulamki.Classes.SQL.AchievementDbHelper;
import com.example.stacjonarny.graulamki.fragments.AboutGameFragment;
import com.example.stacjonarny.graulamki.fragments.AchievementFragment;
import com.example.stacjonarny.graulamki.fragments.Game;
import com.example.stacjonarny.graulamki.fragments.MainMenu;
import com.example.stacjonarny.graulamki.fragments.StartGameFragment;
import java.util.*;

public class MainActivity extends FragmentActivity {

    public static GameState gameState;
    public static int RED_COLOR;
    public static int GREEN_COLOR;
    public static int ORANGE_COLOR;
    public static int BUTTON_DEFAULT_COLOR;
    public static ArrayList<Achievement> achievementList;
    public static AchievementDbHelper achievementDbHelper;
    public static DifficultLevel gameDifficultLevel;
    public static DifficultLevel[] difficultLevels;
    public static Context mainContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainContext = this;
        setContentView(R.layout.activity_main);
        MainMenu main_menu_fragment = new MainMenu();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, main_menu_fragment);
        transaction.commit();
        GetColorFromResoureces();
        LoadDifficultLevels();
        Thread dataBaseConnection = new Thread(new DatabaseConnection());
        dataBaseConnection.start();
    }

    public void StartGameFragment(View view) {
        StartGameFragment start_game_fragment = new StartGameFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out);
        transaction.replace(R.id.fragment_container, start_game_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void StartAchievementFragment(View view) {
        AchievementFragment achievement_fragment = new AchievementFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //animation
        transaction.setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out);
        transaction.replace(R.id.fragment_container, achievement_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void StartAboutGameFragment(View view) {
        AboutGameFragment about_game_fragment = new AboutGameFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out);
        transaction.replace(R.id.fragment_container, about_game_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
//        achievementDbHelper.clearAchievementsDatabase();
//        achievementDbHelper.resetAllAchievements();
//        Thread dataBaseConnection = new Thread(new DatabaseConnection());
//        dataBaseConnection.start();
    }

    public void ExitAplication(View view) {
        System.exit(0);
    }

    private void GetColorFromResoureces() {
        RED_COLOR = ContextCompat.getColor(this, R.color.colorRed);
        GREEN_COLOR = ContextCompat.getColor(this, R.color.colorGreen);
        ORANGE_COLOR = ContextCompat.getColor(this, R.color.colorOrange);
        BUTTON_DEFAULT_COLOR = ContextCompat.getColor(this, R.color.colorButtonDefault);
    }

    public void LoadDifficultLevels()
    {
        MainActivity.difficultLevels = new DifficultLevel[5];
        String [] tab = getResources().getStringArray(R.array.difficulty_levels);
        for(int i = 0; i < MainActivity.difficultLevels.length; i++)
        {
            String[] temp = tab[i].split(",");
            MainActivity.difficultLevels[i] = new DifficultLevel(temp[0], Float.valueOf(temp[2]),Integer.valueOf(temp[1]), Integer.parseInt(temp[3]) ,temp[4]);
        }
    }

    class DatabaseConnection implements Runnable {

        @Override
        public void run() {
            GetDataFromDatabase();
        }

        public void GetDataFromDatabase()
        {
            achievementDbHelper = new AchievementDbHelper(mainContext);
            achievementList = achievementDbHelper.getAllAchievements();
            AddAchievementsToDatabase();
            achievementList = achievementDbHelper.getAllAchievements();
        }

        public void AddAchievementsToDatabase() {
            String[] achievements = getResources().getStringArray(R.array.achievments_array);
            for(String achieves : achievements) {
                String[] achieve = achieves.split(",");
                Achievement newAchieve = new Achievement(achieve[0], Integer.valueOf(achieve[1]), Integer.valueOf(achieve[2]));
                boolean ifExist = false;
                for(Achievement a : achievementList) {
                    if(a.getName().equals(newAchieve.getName())) {
                        ifExist = true;
                    }
                }
                if(!ifExist)
                {
                    achievementDbHelper.insertAchievement(newAchieve.getName(), newAchieve.getCorrectAnswersRow(), newAchieve.getDifficultLevel());
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Game gameFragmnet = null;
        gameFragmnet = (Game) getSupportFragmentManager().findFragmentByTag("GAME");
        if (gameFragmnet != null && gameFragmnet.isVisible()) {
            if (gameFragmnet.BackPresed()) {
            }
        }
        if(gameFragmnet == null)
            super.onBackPressed();
    }
}


