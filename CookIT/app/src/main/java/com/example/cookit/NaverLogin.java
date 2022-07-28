package com.example.cookit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


import com.example.cookit.snsRegister;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.data.OAuthLoginState;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NaverLogin extends Activity {
    final String NAVER_CLIENT_ID = "XZ_JSfyWTu29jvCTE1hx";
    final String NAVER_CLIENT_SECRET = "JzfFX1ikZa";
    final String NAVER_RESPONSE_CODE = "00"; // 정상 반환 시 코드
    final String[] NAVER_JSON_KEY = {"id", "profile_image"};

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OAuthLogin mNaverLoginModule = OAuthLogin.getInstance();

        String accessToken = mNaverLoginModule.getAccessToken(getApplicationContext());
        if (accessToken != null && OAuthLoginState.OK.equals(mNaverLoginModule.getState(getApplicationContext()))) {
            ReqNHNUserInfo reqNaverUserInfo = new ReqNHNUserInfo();
            reqNaverUserInfo.execute(accessToken);
        } else {
            RefreshNHNToken tokenRefresh = new RefreshNHNToken();
            try {
                tokenRefresh.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ReqNHNUserInfo reqNaverUserInfo = new ReqNHNUserInfo();
            reqNaverUserInfo.execute(mNaverLoginModule.getAccessToken(getApplicationContext()));
        }
    }

    class RefreshNHNToken extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                OAuthLogin mNaverLoginModule = OAuthLogin.getInstance();
                mNaverLoginModule.refreshAccessToken(getApplicationContext());
            } catch (Exception e) {
                Log.e("Error RefreshNHNToken", e.toString());
            }
            return true;
        }
    }

    class ReqNHNUserInfo extends AsyncTask<String, Void, String> {
        String result;

        @Override
        protected String doInBackground(String... strings) {
            String token = strings[0];// 네이버 로그인 접근 토큰;
            String header = "Bearer " + token; // Bearer 다음에 공백 추가
            try {
                String apiURL = "https://openapi.naver.com/v1/nid/me";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", header);
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                result = response.toString();
                br.close();
                Log.d("ReqNHNUserInfo Response", result);
            } catch (Exception e) {
                Log.e("Error ReqNHNUserInfo", e.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(result);
                if (object.getString("resultcode").equals(NAVER_RESPONSE_CODE)) {
                    List<String> userInfo = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(object.getString("response"));
                    userInfo.add(String.format("%s-%s", "NAVER", jsonObject.getString(NAVER_JSON_KEY[0])));
                    //userInfo.add(jsonObject.getString(NAVER_JSON_KEY[1]));
                    //userInfo.add(jsonObject.getString(NAVER_JSON_KEY[2]));
                    userInfo.add(jsonObject.getString(NAVER_JSON_KEY[1]));
                    GlobalHelper mGlobalHelper = new GlobalHelper();
                    mGlobalHelper.setGlobalUserLoginInfo(userInfo);
                    Intent intent = new Intent(NaverLogin.this, snsRegister.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class DeleteTokenTask extends AsyncTask<Context, Void, Boolean> {
        Context context;
        snsRegister snsRegister;

        public DeleteTokenTask(Context mContext, snsRegister mActivity) {
            this.context = mContext;
            this.snsRegister = mActivity;
        }
        @Override
        protected Boolean doInBackground(Context... contexts) {
            return OAuthLogin.getInstance().logoutAndDeleteToken(contexts[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            snsRegister.directToMainActivity(result);
        }
    }
}