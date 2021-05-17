package com.jz.dutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.jz.dutils.googlead.GoogleInterstitialAd;
import com.jz.dutils.googlead.GoogleNativeAd;
import com.jz.dutils.utils.ADUtils;
import com.jz.dutils.utils.NetCallBack;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Response;

public class MainActivity extends AppCompatActivity {
    private static final long GAME_LENGTH_MILLISECONDS = 3000;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String TAG = "MyActivity";

    private InterstitialAd interstitialAd;
    private CountDownTimer countDownTimer;
    private Button retryButton;
    private boolean gameIsInProgress;
    private long timerMilliseconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     /*   NoHttp.initialize(this); // NoHttp默认初始化。

        APPUP.Init(this, new NetCallBack() {
            @Override
            public void onSucceed(int i, String s) {

            }

            @Override
            public void onAppUp(int i, Activity activity, int i1, String s, String s1) {
                Log.e("APPUP222", "onAppUp: "+i1+"_______"+s+"________"+s1 );

            }

            @Override
            public void onFailed(int i, Response<String> response) {

            }
        },"xindianying");*/
        // Initialize the Mobile Ads SDK.
       /* MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        GoogleInterstitialAd ad = new GoogleInterstitialAd();
        ad.init(this, AD_UNIT_ID,"aaa");
        //   loadAd();

        // Create the "retry" button, which tries to show an interstitial between game plays.
        retryButton = findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.showInterstitial();
            }
        });*/

        new GoogleNativeAd().init(this,findViewById(R.id.fl_adplaceholder),ADMOB_AD_UNIT_ID,"aaa");

    }

    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";

}