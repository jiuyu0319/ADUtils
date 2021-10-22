package com.jz.dutils.wangxiong;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.jz.dutils.ADStatistics;
import com.jz.dutils.UtilsConfig;
import com.wangxiong.sdk.callBack.InterstitialAdCallBack;
import com.wangxiong.sdk.view.InterstitialAdLoader;

import java.util.Date;

/**
 * Author: djq
 * PackName: com.jz.dutils.wangxiong
 * Time : 2021/10/22 15:07
 * Describe :
 */
public class WangXiongInterstitialAd {
    String TAG = "WangXiongInterstitialAd";

    private Activity activity;
    InterstitialAdLoader interactionLoader;


    private void init(Activity activity, AdListener adListener, String code) {
        this.activity = activity;

    }
    long requestTime;

    private String code;
    private String key;
    public void init(Activity activity, String code, String key, boolean auto) {

        requestTime = Integer.valueOf(new Date().getTime() / 1000 + "");
        this.activity = activity;
        this.code = code;
        this.key = key;

        interactionLoader = new InterstitialAdLoader(activity, code,new InterstitialAdCallBack() {

            @Override
            public void onAdClick() {

                if (UtilsConfig.log)   Log.e(TAG,"插屏广告-被点击");
            }

            @Override
            public void onAdFail(String error) {
                if (UtilsConfig.log)  Log.e(TAG,"插屏广告-error = "+error);
                if (UtilsConfig.InterstitialAuto){
                    long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");

                    String action = "onAdFailedToLoad  错误码 : "+error;
                    ADStatistics.Init(activity, "wangxiong",code, key, requestTime, responseTime, action);
                }
            }

            @Override
            public void onAdLoaded() {
                if (auto){
                    showAd();
                }
                if (UtilsConfig.InterstitialAuto){
                    long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");

                    String action = "onAdLoaded ";
                    ADStatistics.Init(activity, "wangxiong",code, key, requestTime, responseTime, action);
                }
                if (UtilsConfig.log)   Log.e(TAG,"插屏广告-缓存完成");
            }

            @Override
            public void onAdShow() {
                if (UtilsConfig.log)   Log.e(TAG,"插屏广告-展示");
            }

            @Override
            public void onAdClose() {
                if (UtilsConfig.log)  Log.e(TAG,"插屏广告-关闭");
            }

            @Override
            public void onAdVideoStart() {
                if (UtilsConfig.InterstitialAuto){
                    long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");

                    String action = "onAdShowedFullScreenContent ";
                    ADStatistics.Init(activity, "wangxiong",code, key, requestTime, responseTime, action);
                }
                if (UtilsConfig.log)   Log.e(TAG,"插屏广告-视频开始播放");
            }

            @Override
            public void onAdVideoComplete() {
                if (UtilsConfig.log)   Log.e(TAG,"插屏广告-视频播放完成");
            }

        });
        interactionLoader.loadAd();
    }
    public void showAd(){
        if(interactionLoader != null){
            interactionLoader.showAd();
        }

    }
}
