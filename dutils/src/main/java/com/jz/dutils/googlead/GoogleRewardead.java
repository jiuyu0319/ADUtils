package com.jz.dutils.googlead;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.jz.dutils.utils.rewardeadInterface;

public class GoogleRewardead {
    private RewardedAd rewardedAd;
    int isLoading=0;
    private Activity context;
    private String AD_UNIT_ID;
    private rewardeadInterface adinterface;
    public void loadRewardedAd(Activity context, String AD_UNIT_ID , rewardeadInterface interfaces) {
        this.context = context;
        this.AD_UNIT_ID = AD_UNIT_ID;
        this.adinterface = interfaces;
        if (rewardedAd == null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(context, AD_UNIT_ID, adRequest, new RewardedAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull RewardedAd rewardedAds) {
                    super.onAdLoaded(rewardedAds);
                    isLoading = 1;
                    rewardedAd = rewardedAds;
                }
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    isLoading=2;
                    rewardedAd = null;
                }
            });
        }
    }
    public void showRewardedad(){
        if (rewardedAd!= null){
            rewardedAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdShowedFullScreenContent() {
                            // Called when ad is shown.

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // Called when ad fails to show.
                            // Don't forget to set the ad reference to null so you
                            // don't show the ad a second time.
                            rewardedAd = null;

                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when ad is dismissed.
                            // Don't forget to set the ad reference to null so you
                            // don't show the ad a second time.
                            rewardedAd = null;

                            // Preload the next rewarded ad.
                            isLoading=0;
                            adinterface.onClose();
                            loadRewardedAd(context,AD_UNIT_ID,adinterface);
                        }
                    });



            if (isLoading==1){
                rewardedAd.show(
                        context,
                        new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                // Handle the reward.
                                int rewardAmount = rewardItem.getAmount();
                                String rewardType = rewardItem.getType();
                                adinterface.onSuccess();
                            }
                        });
            } else{
                adinterface.onError();
            }
        } else {
            if (isLoading==2){
                adinterface.onClose();

            }
        }

    }
}
