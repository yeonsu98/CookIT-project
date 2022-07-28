package com.example.cookit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MemberJoin extends Activity {

    UserDatabaseHelper dbHelper;
    private EditText mEtMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_member_join);

        mEtMember = findViewById(R.id.et_member);

        Button mBtnMemberJoin = findViewById(R.id.btn_memberJoin);
        mBtnMemberJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNick = mEtMember.getText().toString();
                String ptr = "";
                if(strNick.equals(ptr)) {
                    Toast.makeText(getApplicationContext(),"닉네임을 입력해주세요!", Toast.LENGTH_SHORT).show();
                }else{
                    if(strNick.equals("눈송이")){
                        Intent intent = new Intent(getApplicationContext(), AppMain.class);
                        Toast.makeText(MemberJoin.this,strNick+"님의 수락 후 초대가 완료됩니다!", Toast.LENGTH_SHORT).show();

                        startActivity(intent);

                    }else if (chkNotDuplicated(strNick)){
                        Toast.makeText(MemberJoin.this,"존재하지 않는 닉네임입니다!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button mBtnBack = findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppMain.class);
                startActivity(intent);
            }
        });








    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if( event.getAction() == MotionEvent.ACTION_OUTSIDE ) {
            return false;
        }
        return true;
    }

    public boolean chkNotDuplicated(String nickname){
        boolean ans = false;

        dbHelper = new UserDatabaseHelper(this);

        Cursor cursor = dbHelper.readRecord();

        while(cursor.moveToNext()){

            String nick = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabase.UserEntry.COLUMN_NICKNAME));
            if(nick.equals(nickname)) {
                return ans;
            }

        }

        return !ans;
    }


}

