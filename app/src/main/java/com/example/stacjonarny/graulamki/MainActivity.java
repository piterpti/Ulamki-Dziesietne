package com.example.stacjonarny.graulamki;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;


import com.example.stacjonarny.graulamki.Classes.Achievement;
import com.example.stacjonarny.graulamki.Classes.DifficultLevel;
import com.example.stacjonarny.graulamki.Classes.GameState;
import com.example.stacjonarny.graulamki.Classes.SQL.AchievementDbHelper;
import com.example.stacjonarny.graulamki.fragments.AboutGameFragment;
import com.example.stacjonarny.graulamki.fragments.AchievementFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainMenu main_menu_fragment = new MainMenu();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, main_menu_fragment);
        transaction.commit();
        RED_COLOR = ContextCompat.getColor(this, R.color.colorRed);
        GREEN_COLOR = ContextCompat.getColor(this, R.color.colorGreen);
        ORANGE_COLOR = ContextCompat.getColor(this, R.color.colorOrange);
        BUTTON_DEFAULT_COLOR = ContextCompat.getColor(this, R.color.colorButtonDefault);
        GetDataFromDatabase();
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
        PrintAchievementList();
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
        Log.d("piotrek", "WARNING DELETE ALL ACHEIVEMENTS");
        achievementDbHelper.clearAchievementsDatabase();
        achievementDbHelper.resetAllAchievements();
        GetDataFromDatabase();
    }

    public void ExitAplication(View view) {
        System.exit(0);
    }

    public void PrintAchievementList()
    {
        for(Achievement a : achievementList) {
            Log.d("piotrek", a.toString());
        }
    }

    public void GetDataFromDatabase()
    {
        achievementDbHelper = new AchievementDbHelper(this);
//        achievementDbHelper.clearDatabase();
        achievementList = achievementDbHelper.getAllAchievements();
        AddAchievementsToDatabase();
        achievementList = achievementDbHelper.getAllAchievements();
        PrintAchievementList();

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
