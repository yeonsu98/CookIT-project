package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class snsRegister extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabase; //실시간 데이터베이스
    private EditText mEtNick;
    private List<String> userInfo = new ArrayList<>();
    private String strProfileImg, id; //회원가입 입력 필드
    private TextView tvId, tvEmail;
    private Button mBtnRegister, mBtnLogout; //버튼
    private ImageView iv_profile;
    UserDatabaseHelper dbHelper;
    //private SQLiteDatabase db = null;


    Button.OnClickListener mLogoutListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            GlobalAuthHelper.accountLogout(snsRegister.this, snsRegister.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns_register);

        //로그인 기록 있다면 메인으로
        RbPreference pref = new RbPreference(snsRegister.this);
        String nickname = pref.getValue("nickname", null);
        if (nickname != null){
            directToMain();
        }

        mEtNick = findViewById(R.id.et_snsRgNick);
        mBtnLogout = findViewById(R.id.btn_logout);
        mBtnRegister = findViewById(R.id.btn_snsRegister);

        initView();


        mBtnLogout.setOnClickListener(mLogoutListener);


        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 처리 시작
                String strNick = mEtNick.getText().toString();
                String ptr = "";
                if(strNick.equals(ptr)) {
                    Toast.makeText(getApplicationContext(),"닉네임을 입력해주세요!", Toast.LENGTH_SHORT).show();
                }else{
                    if(chkNotDuplicated(strNick)){
                        Intent intent = new Intent(getApplicationContext(), AppMain.class);


                        dbHelper = new UserDatabaseHelper(snsRegister.this);
                        dbHelper.insertRecord(strNick, strProfileImg);


                        RbPreference pref = new RbPreference(snsRegister.this);
                        pref.put("nickname", strNick);
                        pref.put("profileImg", strProfileImg);
                        pref.put("id", id);


                        startActivity(intent);
                 }else{

                         Toast.makeText(snsRegister.this,"동일한 닉네임이 존재합니다!", Toast.LENGTH_SHORT).show();

                  }
                }



            }
        });
    }

    private void initView() {
        GlobalHelper mGlobalHelper = new GlobalHelper();
        userInfo = mGlobalHelper.getGlobalUserLoginInfo();
        if( userInfo.size()!=0) {
            strProfileImg = userInfo.get(1);
            iv_profile = findViewById(R.id.iv_snsProfile);
            Glide.with(this).load(strProfileImg).into(iv_profile);

        }
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

    public void directToMainActivity(boolean b) {
        Intent intent = new Intent(snsRegister.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void directToMain() {
        Intent intent = new Intent(snsRegister.this, AppMain.class);
        startActivity(intent);
        //finish();
    }

}