package com.jz.dutils.huaweiad;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.FrameLayout;

import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.banner.BannerView;
import com.jz.dutils.ADStatistics;

import java.util.Date;

public class HuaweiBanner {
    /**
     * 自定义回调
     * @param adFrameLayout
     * @param context
     * @param adid
     * @param adListener
     */
    public void setBannerAd(FrameLayout adFrameLayout, Context context, String adid, AdListener adListener){

        BannerView bannerView = new BannerView(context);
        // Set an ad slot ID.
        bannerView.setAdId(adid);
        // Set the background color and size based on user selection.
        BannerAdSize adSize = BannerAdSize.BANNER_SIZE_320_50;
        bannerView.setBannerAdSize(adSize);

        bannerView.setBackgroundColor(Color.WHITE);
        adFrameLayout.addView(bannerView);
        bannerView.setAdListener(adListener);
        bannerView.setBannerRefresh(30);
        bannerView.loadAd(new AdParam.Builder().build());
    }
    public void getBannerView(FrameLayout adFrameLayout, Activity context, String adid, String key){
        long requestTime = Integer.valueOf(new Date().getTime() / 1000 + "");

        BannerView bannerView = new BannerView(context);
        // Set an ad slot ID.
        bannerView.setAdId(adid);
        // Set the background color and size based on user selection.
        BannerAdSize adSize = BannerAdSize.BANNER_SIZE_320_50;
        bannerView.setBannerAdSize(adSize);

        bannerView.setBackgroundColor(Color.WHITE);
        adFrameLayout.addView(bannerView);
        bannerView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Called when an ad is loaded successfully.
                long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");
                String action = "onAdLoaded ";
                ADStatistics.Init(context,"huawei", adid, key, requestTime, responseTime, action);

            }

            @Override
            public void onAdFailed(int errorCode) {
                // Called when an ad fails to be loaded.

                Log.d("华为广告 onAdFailed",errorCode+"");
                long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");
                String action = "onAdFailed :错误码 " + errorCode;
                ADStatistics.Init(context, "huawei",adid, key, requestTime, responseTime, action);

            }

            @Override
            public void onAdOpened() {
                // Called when an ad is opened.
                //showToast(String.format("Ad opened "));
            }

            @Override
            public void onAdClicked() {
                // Called when a user taps an ad.
//            showToast("Ad clicked");
                long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");
                String action = "onAdClicked ";
                ADStatistics.Init(context, "huawei",adid, key, requestTime, responseTime, action);
            }

            @Override
            public void onAdLeave() {
                // Called when a user has left the app.
                //showToast("Ad Leave");
            }

            @Override
            public void onAdClosed() {
                long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");
                String action = "onAdClosed ";
                ADStatistics.Init(context, "huawei",adid, key, requestTime, responseTime, action);

            }
        } );
        bannerView.setBannerRefresh(30);
        bannerView.loadAd(new AdParam.Builder().build());
    }
}
