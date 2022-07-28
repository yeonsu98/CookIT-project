package com.example.cookit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 1;

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.disableWriteAheadLogging();
    }

    public UserDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성
        db.execSQL(UserDatabase.UserEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 단순히 데이터를 삭제하고 다시 시작하는 정책이 적용될 경우
        db.execSQL(UserDatabase.UserEntry.SQL_DELETE_TABLE);
        onCreate(db);
    }
    void insertRecord(String nickname, String profileImg){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        //values.put(UserDatabase.UserEntry.COLUMN_ID, id);
        values.put(UserDatabase.UserEntry.COLUMN_NICKNAME, nickname);
        values.put(UserDatabase.UserEntry.COLUMN_PROFILEIMG, profileImg);

        db.insert(UserDatabase.UserEntry.TABLE_NAME, null, values);
    }

    void deleteRecord(String nickname){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Delete from user where nickname='" + nickname + "';";
        db.execSQL(sql);
    }


    public Cursor readRecord(){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                //UserDatabase.UserEntry.COLUMN_ID,
                UserDatabase.UserEntry.COLUMN_NICKNAME,
                UserDatabase.UserEntry.COLUMN_PROFILEIMG
        };

        Cursor cursor = db.query(
                UserDatabase.UserEntry.TABLE_NAME,
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
