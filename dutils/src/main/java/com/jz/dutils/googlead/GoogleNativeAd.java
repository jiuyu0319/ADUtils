package com.jz.dutils.googlead;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.jz.dutils.ADStatistics;
import com.jz.dutils.R;
import com.jz.dutils.UtilsConfig;

import java.util.Date;

public class GoogleNativeAd {
    /**
     * 传入一个templateview    xmlView 文件    app:gnt_template_type="@layout/gnt_small_template_view"   小模板 中等模板
     *
     * @param activity
     * @param template
     */

    public void init(Activity activity, TemplateView template, String code, String key) {
        long requestTime = Integer.valueOf(new Date().getTime() / 1000 + "");

        AdLoader adLoader = new AdLoader.Builder(activity, code)
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the ad.
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(activity.getResources().getColor(R.color.colorf2f2f2))).build();
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);
                        template.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        // Handle the failure by logging, altering the UI, and so on.
                        if (loadAdError.getCode() == 3) {
                            template.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                        }

                        if (UtilsConfig.NativeAuto) {
                            long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");
                            String action = "onAdFailed :错误码 " + loadAdError.getCode();
                            ADStatistics.Init(activity, "google", code, key, requestTime, responseTime, action);
                        }
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        if (UtilsConfig.NativeAuto) {

                            long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");
                            String action = "onAdLoaded ";
                            ADStatistics.Init(activity, "google", code, key, requestTime, responseTime, action);
                        }
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        if (UtilsConfig.NativeAuto) {

                            long responseTime = Integer.valueOf(new Date().getTime() / 1000 + "");
                            String action = "onAdClicked ";
                            ADStatistics.Init(activity, "google", code, key, requestTime, responseTime, action);
                        }
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    /**
     * if (loadAdError.getCode()==3){
     * template.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
     * }
     *
     * @param activity
     * @param template
     * @param code
     * @param adListener
     */

    public void init(Activity activity, TemplateView template, String code, AdListener adListener) {

        AdLoader adLoader = new AdLoader.Builder(activity, code)
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // Show the ad.
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(activity.getResources().getColor(R.color.colorf2f2f2))).build();

                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);
                        template.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                    }

                })
                .withAdListener(adListener)
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }
}
