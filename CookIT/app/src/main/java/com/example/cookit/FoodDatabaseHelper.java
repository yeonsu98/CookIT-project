
package com.example.cookit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class FoodDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 1;

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.disableWriteAheadLogging();
    }

    public FoodDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성
        db.execSQL(foodDatabase.FoodEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 단순히 데이터를 삭제하고 다시 시작하는 정책이 적용될 경우
        db.execSQL(foodDatabase.FoodEntry.SQL_DELETE_TABLE);
        onCreate(db);
    }
    void insertRecord(String userId, String foodName, String frozen, String quantity, String addDate){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(foodDatabase.FoodEntry.COLUMN_userId, userId);
        //values.put(foodDatabase.FoodEntry.COLUMN_foodId, foodId);
        values.put(foodDatabase.FoodEntry.COLUMN_foodName, foodName);
        values.put(foodDatabase.FoodEntry.COLUMN_frozen, frozen);
        values.put(foodDatabase.FoodEntry.COLUMN_quantity, quantity);
        values.put(foodDatabase.FoodEntry.COLUMN_addDate, addDate);

        db.insert(foodDatabase.FoodEntry.TABLE_NAME, null, values);
    }


    public Cursor readRecord(){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                foodDatabase.FoodEntry.COLUMN_userId,
                //foodDatabase.FoodEntry.COLUMN_foodId,
                foodDatabase.FoodEntry.COLUMN_foodName,
                foodDatabase.FoodEntry.COLUMN_frozen,
                foodDatabase.FoodEntry.COLUMN_quantity,
                foodDatabase.FoodEntry.COLUMN_addDate
        };

        Cursor cursor = db.query(
                foodDatabase.FoodEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        return cursor;


    }
}