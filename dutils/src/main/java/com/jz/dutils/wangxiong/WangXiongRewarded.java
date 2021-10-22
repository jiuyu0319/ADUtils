package com.jz.dutils.wangxiong;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.reward.RewardAd;
import com.jz.dutils.ADStatistics;
import com.jz.dutils.UtilsConfig;
import com.jz.dutils.utils.rewardeadInterface;
import com.wangxiong.sdk.callBack.RewardVideoAdCallBack;
import com.wangxiong.sdk.view.RewardVideoLoader;

import java.util.Date;

/**
 * Author: djq
 * PackName: com.jz.dutils.wangxiong
 * Time : 2021/10/22 15:29
 * Describe :
 */
public class WangXiongRewarded {
    private String TAG = "WangXiongRewarded";
    private RewardAd rewardAd;
    int isLoading = 0;
    private Activity context;
    private String AD_UNIT_ID;
    private rewardeadInterface adinterface;
    public static RewardVideoLoader rewardVideoLoader;
    boolean loaded = false;
    long requestTime;
    public void loadRewardedAd(Activity context, String code, rewardeadInterface interfaces) {
        requestTime = Integer.valueOf(new Date().getTime() / 1000 + "");

        this.context = context;
        this.AD_UNIT_ID = AD_UNIT_ID;
        this.adinterface = interfaces;
        if (rewardAd == null) {
            rewardAd = new RewardAd(context, AD_UNIT_ID);

        }

        rewardVideoLoader = new RewardVideoLoader(context, code, new RewardVideoAdCallBack() {
            @Override
            public void onAdShow() {
                if (UtilsConfig.InterstitialAuto){
                    long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");

                    String action = "onAdShow ";
                    ADStatistics.Init(context, "wangxiong",code, code, requestTime, responseTime, action);
                }
                //Log.i(TAG, "激励视频广告展示");
            }

            @Override
            public void onAdVideoCache() {
               // Log.i(TAG, "激励视频广告收到数据");
                loaded = true;
                isLoading = 1;

            }

            @Override
            public void onAdClick() {
                if (UtilsConfig.log)  Log.e(TAG, "激励视频广告被点击");
            }

            @Override
            public void onAdClose() {
               // Log.i(TAG, "激励视频广告关闭");
                if (UtilsConfig.InterstitialAuto){
                    long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");

                    String action = "onAdClose ";
                    ADStatistics.Init(context, "wangxiong",code, code, requestTime, responseTime, action);
                }

                if (isLoading == 3) {
                    adinterface.onClose();
                } else {
                    adinterface.onError();
                }
                isLoading = 0;
                rewardVideoLoader.loadAd();
            }

            @Override
            public void onAdFail(String error) {
                isLoading = 2;

                if (UtilsConfig.log)  Log.e(TAG, "激励视频广告加载失败:" + error);
                if (UtilsConfig.InterstitialAuto){
                    long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");
                    String action = "onAdFail  错误码 : "+error;
                    ADStatistics.Init(context, "wangxiong",code, code, requestTime, responseTime, action);
                }
            }

            @Override
            public void onAdVideoComplete() {
                if (UtilsConfig.log)   Log.e(TAG, "激励视频广告播放完成");
            }

            @Override
            public void onReward(String trans_id) {
                isLoading = 3;
                adinterface.onSuccess();
                if (UtilsConfig.log)   Log.e(TAG, "可以发放奖励了,trans_id = " + trans_id);
            }
        });
        loaded = false;
        rewardVideoLoader.setOrientation(RewardVideoLoader.VERTICAL);//不填就默认是RewardVideoLoader.VERTICAL

        rewardVideoLoader.loadAd();


    }
    public void showRewardedad() {
        if (isLoading ==1){
            rewardVideoLoader.showAd();

        }else if (isLoading ==2){
            adinterface.onClose();
        }
    }
}
