package com.example.stacjonarny.graulamki.Classes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.stacjonarny.graulamki.Classes.SQL.AchievementDbHelper;
import com.example.stacjonarny.graulamki.MainActivity;
import com.example.stacjonarny.graulamki.R;
import static com.example.stacjonarny.graulamki.MainActivity.*;

public class DatabaseConnection {

    private Context context;

    public DatabaseConnection(Context context) {
        this.context = context;
        GetDataFromDatabase();
    }

    public void GetDataFromDatabase()
    {
        achievementDbHelper = new AchievementDbHelper(mainContext);
        achievementList = achievementDbHelper.getAllAchievements();
        if(FIRST_RUN)
        {
            AddAchievementsToDatabase();
            achievementList = achievementDbHelper.getAllAchievements();
            Activity a = (Activity) context;
            SharedPreferences sharedPreferences = a.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(MainActivity.FIRST_RUN_KEY, false);
            editor.commit();
        }
    }

    public void AddAchievementsToDatabase() {
        String[] achievements = context.getResources().getStringArray(R.array.achievments_array);
        for(String achieves : achievements) {
            String[] achieve = achieves.split(",");
            Achievement newAchieve = new Achievement(achieve[0], Integer.valueOf(achieve[1]), Integer.valueOf(achieve[2]));
            achievementDbHelper.insertAchievement(newAchieve.getName(), newAchieve.getCorrectAnswersRow(), newAchieve.getDifficultLevel());
            FIRST_RUN = false;
        }
    }
}