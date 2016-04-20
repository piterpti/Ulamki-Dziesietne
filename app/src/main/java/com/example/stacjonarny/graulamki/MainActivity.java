package com.example.stacjonarny.graulamki;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;


import com.example.stacjonarny.graulamki.Classes.GameState;
import com.example.stacjonarny.graulamki.Classes.SqlDatabase.SqlAdapter;
import com.example.stacjonarny.graulamki.fragments.AboutGameFragment;
import com.example.stacjonarny.graulamki.fragments.AchievementFragment;
import com.example.stacjonarny.graulamki.fragments.MainMenu;
import com.example.stacjonarny.graulamki.fragments.StartGameFragment;

public class MainActivity extends FragmentActivity {

    public static GameState gameState;
    public static int RED_COLOR;
    public static int GREEN_COLOR;
    public static int ORANGE_COLOR;
    public static int BUTTON_DEFAULT_COLOR;
    public SqlAdapter sqlAdapter;

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
        ConnectToDatabase();
    }

    public void StartGameFragment(View view) {
        StartGameFragment start_game_fragment = new StartGameFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, start_game_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void StartAchievementFragment(View view) {
        AchievementFragment achievement_fragment = new AchievementFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, achievement_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void StartAboutGameFragment(View view) {
        AboutGameFragment about_game_fragment = new AboutGameFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, about_game_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void ExitAplication(View view) {
        System.exit(0);
    }

    public void ConnectToDatabase()
    {
        sqlAdapter = new SqlAdapter(this);
        sqlAdapter.open();
        sqlAdapter.insertTodo("5 pod rzad!", 1);
        Log.d("blabla",sqlAdapter.getTodo(1).getName());
    }
}
