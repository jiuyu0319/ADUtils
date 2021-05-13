package com.jz.dutils.googlead;

import android.app.Activity;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.jz.dutils.ADStatistics;
import com.jz.dutils.UtilsConfig;

import java.util.Date;

public class GoogleBanner {
    /**
     * 横幅测试广告id
     * @param frameLayout
     * @param activity
     */
    public void setBannerAd(FrameLayout frameLayout , Activity activity){
        setBannerAd(frameLayout,activity,"ca-app-pub-3940256099942544/6300978111","test");
    }
    /**
     * 横幅广告id
     * @param frameLayout
     * @param activity
     */
    public void setBannerAd(FrameLayout frameLayout, Activity activity, String googlecode,String key){
        long requestTime = Integer.valueOf(new Date().getTime() / 1000 + "");

        AdView adView = new AdView(activity);
        adView.setAdUnitId(googlecode);
        frameLayout.addView(adView);
        AdRequest adRequest =
                new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();
        //AdSize adSize = getAdSize(activity);
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                if (UtilsConfig.BannerAuto){
                    long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");
                    String action = "onAdClosed ";
                    ADStatistics.Init(activity, "google", googlecode, key, requestTime, responseTime, action);
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError i) {
                Log.e(activity.getClass().getSimpleName(),"onAdFailedToLoad    :"+i.getCode());
                super.onAdFailedToLoad(i);
                if (UtilsConfig.BannerAuto){
                    long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");
                    String action = "onAdFailedToLoad :错误码 "+i.getCode();
                    ADStatistics.Init(activity, "google", googlecode, key, requestTime, responseTime, action);
                }
            }

            @Override
            public void onAdLoaded() {
                Log.e(activity.getClass().getSimpleName(),"onAdLoaded    :");
                super.onAdLoaded();
                if (UtilsConfig.BannerAuto){
                    long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");
                    String action = "onAdLoaded ";
                    ADStatistics.Init(activity, "google", googlecode, key, requestTime, responseTime, action);
                }
            }
        });

        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
    }
}
