package com.jz.dutils.huaweiad;

import android.content.Context;
import android.util.Log;

import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.InterstitialAd;
import com.jz.dutils.ADStatistics;
import com.jz.dutils.UtilsConfig;

import java.util.Date;

public class HuaweiInterstitialAd {
    private InterstitialAd interstitialAd;

    public void init(Context context, AdListener adErrorAndClosed) {
        init(context, "testb4znbuh3n2", adErrorAndClosed);
    }
    public void init(Context context, String code, AdListener adErrorAndClosed) {
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdId(code);
        AdParam adParam = new AdParam.Builder().build();
        interstitialAd.setAdListener(adErrorAndClosed);
        interstitialAd.loadAd(adParam);
    }
    long requestTime;
    public void init(Context context, String code,String key,boolean auto) {
        requestTime = Integer.valueOf(new Date().getTime() / 1000 + "");

        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdId(code);
        AdParam adParam = new AdParam.Builder().build();
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // 广告获取成功时调用
                if (auto){
                    showInterstitialAd();
                }
                if (UtilsConfig.InterstitialAuto){
                    long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");

                    String action = "onAdLoaded ";
                    ADStatistics.Init(context, "huawei",code, key, requestTime, responseTime, action);
                }
            }

            @Override
            public void onAdFailed(int errorCode) {
                // 广告获取失败时调用
                Log.d("华为插页广告加载失败", "errcode  :" + errorCode);
                if (UtilsConfig.InterstitialAuto){
                    long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");

                    String action = "onAdFailedToLoad  错误码 : "+errorCode;
                    ADStatistics.Init(context, "huawei",code, key, requestTime, responseTime, action);
                }
            }

            @Override
            public void onAdClosed() {
                // 广告关闭时调用
                Log.d("华为插页广告g关闭", "onAdClosed  :");
               // interstitialAd.loadAd(adParam);
            }

            @Override
            public void onAdClicked() {
                // 广告点击时调用
                if (UtilsConfig.InterstitialAuto){
                    long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");

                    String action = "onAdClicked ";
                    ADStatistics.Init(context, "huawei",code, key, requestTime, responseTime, action);
                }
            }

            @Override
            public void onAdLeave() {
                //广告离开时调用
            }

            @Override
            public void onAdOpened() {
                // 广告打开时调用
            }

            @Override
            public void onAdImpression() {
                // 广告曝光时调用
            }
        });
        interstitialAd.loadAd(adParam);

    }

    public void showInterstitialAd() {
        // 显示广告
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Log.d("华为插页广告", "Ad did not load");
        }
    }
}
