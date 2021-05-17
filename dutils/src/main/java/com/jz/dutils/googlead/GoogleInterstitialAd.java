package com.jz.dutils.googlead;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.jz.dutils.ADStatistics;
import com.jz.dutils.UtilsConfig;

import java.util.Date;

public class GoogleInterstitialAd {
    private InterstitialAd interstitialAd;

    private Activity activity;


    private void init(Activity activity, AdListener adListener, String code){
        this.activity = activity;

    }
    public void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null) {
            interstitialAd.show(activity);
        } else {
          //  Toast.makeText(activity, "Ad did not load", Toast.LENGTH_SHORT).show();
            startGame();
        }
    }

    boolean isStartGame=false;
    private void startGame() {
        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (interstitialAd == null) {
            isStartGame = true;
            init(activity,code,key,false);
        }

    }
    long requestTime;

    private String code;
    private String key;
    public void init(Activity activity, String code,String key,boolean auto) {
        requestTime = Integer.valueOf(new Date().getTime() / 1000 + "");
        this.activity = activity;
        this.code = code;
        this.key = key;
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                activity,
                code,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        GoogleInterstitialAd.this.interstitialAd = interstitialAd;
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        GoogleInterstitialAd.this.interstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        GoogleInterstitialAd.this.interstitialAd = null;
                                    }
                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        if (UtilsConfig.InterstitialAuto){
                                            long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");

                                            String action = "onAdShowedFullScreenContent ";
                                            ADStatistics.Init(activity, "google",code, key, requestTime, responseTime, action);
                                        }
                                    }
                                });
                        if (isStartGame) {
                            interstitialAd.show(activity);
                            isStartGame =false;
                        }
                        if (auto){
                            interstitialAd.show(activity);
                        }

                        if (UtilsConfig.InterstitialAuto){
                            long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");

                            String action = "onAdLoaded ";
                            ADStatistics.Init(activity, "google",code, key, requestTime, responseTime, action);
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        GoogleInterstitialAd.this.interstitialAd = null;

                        if (UtilsConfig.InterstitialAuto){
                            long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");

                            String action = "onAdFailedToLoad  错误码 : "+loadAdError.getCode();
                            ADStatistics.Init(activity, "google",code, key, requestTime, responseTime, action);
                        }

                    }
                });
    }

}
