package com.example.stacjonarny.graulamki;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;


import com.example.stacjonarny.graulamki.Classes.Achievement;
import com.example.stacjonarny.graulamki.Classes.DatabaseConnection;
import com.example.stacjonarny.graulamki.Classes.DifficultLevel;
import com.example.stacjonarny.graulamki.Classes.GameState;
import com.example.stacjonarny.graulamki.Classes.SQL.AchievementDbHelper;
import com.example.stacjonarny.graulamki.fragments.AboutGameFragment;
import com.example.stacjonarny.graulamki.fragments.AchievementFragment;
import com.example.stacjonarny.graulamki.fragments.Game;
import com.example.stacjonarny.graulamki.fragments.GameSummary;
import com.example.stacjonarny.graulamki.fragments.MainMenu;
import com.example.stacjonarny.graulamki.fragments.DifficultLevelFragment;
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
    public static ArrayList<Achievement> unlockedAchievements;
    public final static String SOUND_KEY = "SOUNDONOFF";
    public final static String DIFFICULT_LEVEL_KEY = "DIFFICULT_LEVEL_START";
    public final static String FIRST_RUN_KEY = "FIRST_RUN";
    public final static String MENU_FRAGMENT = "MENU_FRAGMENT";
    public static boolean SOUND = true;
    public static boolean FIRST_RUN = true;


    public static final boolean DEBUG_MODE = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainMenu main_menu_fragment = new MainMenu();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(savedInstanceState == null)
        {
            transaction.add(R.id.fragment_container, main_menu_fragment, MENU_FRAGMENT);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        init();
    }

    private void init() {
        mainContext = this;
        GetColorFromResoureces();
        LoadDifficultLevels();
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SOUND = sharedPreferences.getBoolean(SOUND_KEY, true);
        FIRST_RUN = sharedPreferences.getBoolean(FIRST_RUN_KEY, true);
        MainActivity.gameDifficultLevel = difficultLevels[sharedPreferences.getInt(DIFFICULT_LEVEL_KEY, 0)];
        new DatabaseConnection(mainContext);
    }

    public void StartGameFragment(View view) {
        Bundle args = new Bundle();
        Game play_mode_fragment = new Game();
        play_mode_fragment.setArguments(args);
        play_mode_fragment.setRetainInstance(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, play_mode_fragment,DifficultLevelFragment.GAME_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
        play_mode_fragment.CreateGame();
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
        new DatabaseConnection(MainActivity.mainContext);

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
    }

    public void StartDiffLevelFragment(View view) {
        DifficultLevelFragment start_game_fragment = new DifficultLevelFragment();
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

    @Override
    public void onBackPressed() {
        MainMenu mainMenu = null;
        mainMenu = (MainMenu) getSupportFragmentManager().findFragmentByTag(MENU_FRAGMENT);
        if(mainMenu != null && mainMenu.isVisible()) {
            System.exit(0);
        }

        boolean callSuper = true;
        Game gameFragment = null;
        GameSummary gameSummaryFragment = null;
        gameFragment = (Game) getSupportFragmentManager().findFragmentByTag(DifficultLevelFragment.GAME_FRAGMENT_TAG);
        gameSummaryFragment = (GameSummary) getSupportFragmentManager().findFragmentByTag(Game.GAME_SUMMARY_TAG);
        if (gameFragment != null && gameFragment.isVisible()) {
            if(gameFragment.BackPressed()) {
                callSuper = false;
            }
        }
        if(gameSummaryFragment != null && gameSummaryFragment.isVisible()) {
            gameSummaryFragment.BackToMenu();
            callSuper = false;
        }
        if(callSuper)
        {
            super.onBackPressed();
        }
    }
}


