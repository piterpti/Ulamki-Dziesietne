package com.example.stacjonarny.graulamki.Classes.SQL;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.stacjonarny.graulamki.Classes.Achievement;
import com.example.stacjonarny.graulamki.MainActivity;

import java.util.*;
import java.util.Collections;

/**
 * Created by Piter on 21/04/2016.
 */
public class AchievementDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String ACHIEVEMENT_TABLE_NAME = "achievements";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOCKED = "locked";
    public static final String COLUMN_ANSWERS = "answers";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_DIFFICULT_LEVEL = "difflevel";

    public AchievementDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ACHIEVEMENT_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " + COLUMN_LOCKED + " INTEGER, " + COLUMN_ANSWERS + " INTEGER, " +
                COLUMN_STATUS + " INTEGER, " + COLUMN_DIFFICULT_LEVEL + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + ACHIEVEMENT_TABLE_NAME);
        onCreate(db);
    }


    public boolean insertAchievement(String name, int correctAnswerRow, int difficultLevel, int starImage)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_LOCKED, 1);
        contentValues.put(COLUMN_ANSWERS, correctAnswerRow);
        contentValues.put(COLUMN_STATUS, starImage);
        contentValues.put(COLUMN_DIFFICULT_LEVEL, difficultLevel);
        db.insert(ACHIEVEMENT_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DATABASE_NAME + " WHERE " + COLUMN_ID + " = " + id + "", null);
        db.close();
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ACHIEVEMENT_TABLE_NAME);
        db.close();
        return numRows;
    }

    public boolean updateAchievement(String name, boolean locked, int correctAnswerRow, int status, int difficultLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int isLocked = locked ? 1 : 0;
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_LOCKED, isLocked);
        contentValues.put(COLUMN_ANSWERS, correctAnswerRow);
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(COLUMN_DIFFICULT_LEVEL, difficultLevel);
        db.update(ACHIEVEMENT_TABLE_NAME, contentValues, COLUMN_NAME + " = ? AND " + COLUMN_DIFFICULT_LEVEL + " = ?", new String[]{name, (difficultLevel + "")});
        db.close();
        return true;
    }

    public void clearAchievementsDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + ACHIEVEMENT_TABLE_NAME);
        db.close();
    }

    public Integer deleteAchievement(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer toReturn = db.delete(ACHIEVEMENT_TABLE_NAME,"id = ?", new String[]{Integer.toString(id)});
        db.close();
        return toReturn;
    }

    public Integer deleteAchievement(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer toReturn =  db.delete(ACHIEVEMENT_TABLE_NAME,"name = ?", new String[]{name});
        db.close();
        return toReturn;
    }

    public void resetAllAchievements() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOCKED, 1);
        db.update(ACHIEVEMENT_TABLE_NAME, cv, null, null);
        db.close();
    }

    public ArrayList<Achievement> getAllAchievements() {
        ArrayList<Achievement> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + ACHIEVEMENT_TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){

            String name = res.getString(res.getColumnIndex(COLUMN_NAME));
            boolean locked = res.getInt(res.getColumnIndex(COLUMN_LOCKED)) == 1 ? true : false ;
            int answers = res.getInt(res.getColumnIndex(COLUMN_ANSWERS));
            int status = res.getInt(res.getColumnIndex(COLUMN_STATUS));
            int diffLevel = res.getInt(res.getColumnIndex(COLUMN_DIFFICULT_LEVEL));
            if(diffLevel == MainActivity.gameDifficultLevel.getLevelNum())
            {
                Achievement newAchievement = new Achievement(name, locked, answers, status, diffLevel);
                arrayList.add(newAchievement);
            }
            res.moveToNext();
        }
        db.close();
        //Collections.sort(arrayList);
        return arrayList;
    }
}
