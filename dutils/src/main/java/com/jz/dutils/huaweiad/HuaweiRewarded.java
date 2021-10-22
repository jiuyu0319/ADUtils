package com.jz.dutils.huaweiad;

import android.app.Activity;

import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.reward.Reward;
import com.huawei.hms.ads.reward.RewardAd;
import com.huawei.hms.ads.reward.RewardAdLoadListener;
import com.huawei.hms.ads.reward.RewardAdStatusListener;
import com.jz.dutils.utils.rewardeadInterface;

public class HuaweiRewarded {
    private RewardAd rewardAd;
    int isLoading = 0;
    private Activity context;
    private String AD_UNIT_ID;
    private rewardeadInterface adinterface;

    public void loadRewardedAd(Activity context, String AD_UNIT_ID, rewardeadInterface interfaces) {
        this.context = context;
        this.AD_UNIT_ID = AD_UNIT_ID;
        this.adinterface = interfaces;
        if (rewardAd == null) {
            rewardAd = new RewardAd(context, AD_UNIT_ID);

        }
        rewardAd.loadAd(new AdParam.Builder().build(), listener);

    }

    RewardAdLoadListener listener = new RewardAdLoadListener() {
        @Override
        public void onRewardedLoaded() {
            // 激励广告加载成功
            isLoading = 1;
        }

        @Override
        public void onRewardAdFailedToLoad(int errorCode) {
            // 激励广告加载失败
            isLoading = 2;
        }
    };

    public void showRewardedad() {

        if (isLoading == 1) {
            if (rewardAd.isLoaded()) {
                rewardAd.show(context, new RewardAdStatusListener() {
                    @Override
                    public void onRewardAdOpened() {
                        // 激励广告被打开
                    }

                    @Override
                    public void onRewardAdFailedToShow(int errorCode) {
                        // 激励广告展示失败
                        adinterface.onClose();
                    }

                    @Override
                    public void onRewardAdClosed() {

                        if (isLoading == 3) {
                            adinterface.onClose();
                        } else {
                            adinterface.onError();
                        }
                        isLoading = 0;
                        rewardAd.loadAd(new AdParam.Builder().build(), listener);
                    }

                    @Override
                    public void onRewarded(Reward reward) {
                        // 激励广告奖励达成，发放奖励
                        isLoading = 3;
                        adinterface.onSuccess();

                    }
                });
            }
        } else if (isLoading ==2){
            adinterface.onClose();
        }

    }
}
