package com.jz.dutils.googlead;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;

public class GoogleInterstitialAd {
    private InterstitialAd mInterstitialAd;
    private Activity activity;


    public GoogleInterstitialAd(Activity activity,String code, AdListener adListener) {
        this.activity = activity;
        init(activity,adListener,code);
    }

    private void init(Activity activity, AdListener adListener, String code){
        this.activity = activity;
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(code);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(adListener);
    }
    private void init(Activity activity, String code){
        this.activity = activity;
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(code);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("GoogleInterstitialAd  ","错误码 :"+loadAdError.getCode());
                // Code to be executed when an ad request fails.
            }


            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                // mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    public void  showInterstitial(){
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            //Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            Log.d("GoogleInterstitialAd  ","showInterstitial 的时候 : if (mInterstitialAd != null && mInterstitialAd.isLoaded())");

        }
    }
}
