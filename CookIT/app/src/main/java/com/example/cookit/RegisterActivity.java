package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class RegisterActivity extends AppCompatActivity{
    UserDatabaseHelper dbHelper;
    private TextView textView;
    private ImageView iv_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textView = findViewById(R.id.tv_resultText);

        Intent intent = getIntent();

        //String id = intent.getExtras().getString("id");
        String nickname = intent.getExtras().getString("nickname");
        String profileImg = intent.getExtras().getString("profileImg");

        dbHelper = new UserDatabaseHelper(this);
        dbHelper.insertRecord(nickname, profileImg);

        RbPreference pref = new RbPreference(this);
        pref.put("nickname", nickname);
        pref.put("profileImg", profileImg);

        //printTable();


    }

    private void printTable(){
        Cursor cursor = dbHelper.readRecord();
        String result = "";

        result += "row 개수 : " + cursor.getCount() + "\n";
        while(cursor.moveToNext()){
            //String id = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabase.UserEntry.COLUMN_ID));
            String nickname = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabase.UserEntry.COLUMN_NICKNAME));
            String profileImg = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabase.UserEntry.COLUMN_PROFILEIMG));
            iv_profile = findViewById(R.id.iv_resultProfile);
            Glide.with(this).load(profileImg).into(iv_profile);

            result += nickname + "\n";
        }
        textView.setText(result);
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

}