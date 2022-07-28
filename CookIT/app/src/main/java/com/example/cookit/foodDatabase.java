package com.example.cookit;

import android.speech.tts.TextToSpeech;

import androidx.annotation.IntegerRes;

import java.util.Date;

public class foodDatabase {
    private foodDatabase(){

    }
    //테이블에 필요한 내용을 하나의 클래스에 정의
    public static class FoodEntry{
        public static final String TABLE_NAME = "food";
        public static final String COLUMN_userId = "userId"; //가족 id
        //public static final String COLUMN_foodId = "foodId"; //재료 id
        public static final String COLUMN_foodName = "foodName"; //재료 이름
        public static final String COLUMN_quantity = "quantity"; //수량
        public static final String COLUMN_addDate = "addDate"; //입력 날짜
        public static final String COLUMN_frozen = "frozen"; //냉동여부
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_userId + "TEXT," +
                        //COLUMN_foodId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_foodName + " TEXT," +
                        COLUMN_frozen + " TEXT," +
                        COLUMN_quantity + " TEXT," +
                        COLUMN_addDate + " TEXT)";
        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}

