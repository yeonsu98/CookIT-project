package com.example.cookit;

import android.provider.BaseColumns;

public class UserDatabase {
    private UserDatabase(){

    }
    //테이블에 필요한 내용을 하나의 클래스에 정의
    public static class UserEntry{
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NICKNAME = "nickname";
        public static final String COLUMN_PROFILEIMG = "profileImg";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        COLUMN_ID + " TEXT," +
                        COLUMN_NICKNAME + " TEXT PRIMARY KEY," +
                        COLUMN_PROFILEIMG + " TEXT)";
        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
