package com.example.cookit;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaSession2;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.usermgmt.LoginButton;
import com.example.cookit.KakaoLogin.KakaoSessionCallback;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginState;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {
    //private ISessionCallback mSessionCallback;
    private LoginButton mKakaoLoginBtnBasic;
    private OAuthLoginButton mNaverLoginBtnBasic;
    private Button mKakaoLoginBtn, mNaverLoginBtn, mGoogleLoginBtn;
    private KakaoSessionCallback sessionCallback;
    private OAuthLogin mNaverLoginModule;
    private OAuthLogin mNaverLoginAuth;
    final String NAVER_CLIENT_ID = "XZ_JSfyWTu29jvCTE1hx";
    final String NAVER_CLIENT_SECRET = "JzfFX1ikZa";
    private FirebaseAuth mGoogleLoginModule;

    @SuppressLint("HandlerLeak")
    private OAuthLoginHandler mNaverLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                Intent intent = new Intent(MainActivity.this, NaverLogin.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Naver Login Failed!", Toast.LENGTH_LONG);
            }
        }
    };

    Button.OnClickListener mGoogleLoginListener = new ImageView.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, GoogleLogin.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(MainActivity.this);


        mKakaoLoginBtn = findViewById(R.id.btn_kakao);
        mKakaoLoginBtnBasic = findViewById(R.id.btn_kakao_basic);
        mNaverLoginBtn = findViewById(R.id.btn_naver);
        mNaverLoginBtnBasic = findViewById(R.id.btn_naver_basic);
        mNaverLoginBtnBasic.setOAuthLoginHandler(mNaverLoginHandler);
        mGoogleLoginBtn = findViewById(R.id.btn_google);


        mKakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKakaoLoginBtnBasic.performClick();
            }
        });

        mNaverLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNaverLoginBtnBasic.performClick();
            }
        });

        mNaverLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNaverLoginBtnBasic.performClick();
            }
        });

        mGoogleLoginBtn.setOnClickListener(mGoogleLoginListener);

        mNaverLoginModule = OAuthLogin.getInstance();
        mNaverLoginModule.init(
                this
                , "XZ_JSfyWTu29jvCTE1hx"
                , "JzfFX1ikZa"
                , "네이버 아이디로 로그인"
        );

        mGoogleLoginModule = FirebaseAuth.getInstance();

        if (!HasKakaoSession() && !HasNaverSession() && !HasGoogleSession()) {
            sessionCallback = new KakaoSessionCallback(getApplicationContext(), MainActivity.this);
            Session.getCurrentSession().addCallback(sessionCallback);
        } else if (HasKakaoSession()) {
            sessionCallback = new KakaoSessionCallback(getApplicationContext(), MainActivity.this);
            Session.getCurrentSession().addCallback(sessionCallback);
            Session.getCurrentSession().checkAndImplicitOpen();
        } else if (HasNaverSession()) {
            Intent intent = new Intent(MainActivity.this, NaverLogin.class);
            startActivity(intent);
            finish();
        } else if (HasGoogleSession()) {
            Intent intent = new Intent(MainActivity.this, GoogleLogin.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    private boolean HasKakaoSession() {
        if (!Session.getCurrentSession().checkAndImplicitOpen()) {
            return false;
        }
        return true;
    }

    private boolean HasNaverSession() {
        if (OAuthLoginState.NEED_LOGIN.equals(mNaverLoginModule.getState(getApplicationContext())) ||
                OAuthLoginState.NEED_INIT.equals(mNaverLoginModule.getState(getApplicationContext()))) {
            return false;
        }
        return true;
    }

    private boolean HasGoogleSession() {
        if (mGoogleLoginModule.getCurrentUser() == null) {
            return false;
        }
        return true;
    }

    public void directToSnsRegister(Boolean result) {
        // 닉네임 만들었는지 여부 확인

            Intent intent = new Intent(MainActivity.this, snsRegister.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            if (result) {
                Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        }


    private void getAppKeyHash(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures){
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
                }
            }catch (Exception e){
            Log.e("name not found", e.toString());
        }
    }
}