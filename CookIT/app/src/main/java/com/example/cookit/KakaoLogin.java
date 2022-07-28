package com.example.cookit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.widget.Toast;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;
import java.util.List;

public class KakaoLogin extends Activity {
    public static class KakaoSessionCallback implements ISessionCallback{
        private Context mContext;
        private MainActivity mainActivity;

        public KakaoSessionCallback(Context context, MainActivity activity){
            this.mContext = context;
            this.mainActivity = activity;
        }

        @Override
        public void onSessionOpened() {
            //로그인 요청
            requestMe();
        }
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Toast.makeText(mContext, "KaKao 로그인 오류가 발생했습니다." + exception.toString(), Toast.LENGTH_SHORT).show();
        }
        protected void requestMe(){
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        //세션이 닫힘..
                        mainActivity.directToSnsRegister(false);
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        //사용자 정보 가져오기
                        List<String> userInfo = new ArrayList<>();
                        userInfo.add(String.valueOf(result.getId()));
                        //userInfo.add(result.getKakaoAccount().getProfile().getNickname());
                        //userInfo.add(result.getKakaoAccount().getEmail());
                        userInfo.add(result.getKakaoAccount().getProfile().getProfileImageUrl());
                        //intent.putExtra("name", result.getKakaoAccount().getProfile().getNickname());
                        //String profileImg = result.getKakaoAccount().getProfile().getProfileImageUrl();

                        GlobalHelper mGlobalHelper = new GlobalHelper();
                        mGlobalHelper.setGlobalUserLoginInfo(userInfo);

                        mainActivity.directToSnsRegister(true);
                    }
                });
            }


        };


    }


