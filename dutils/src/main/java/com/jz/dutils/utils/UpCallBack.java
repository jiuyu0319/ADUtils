package com.jz.dutils.utils;

import android.app.Activity;

import com.yanzhenjie.nohttp.rest.Response;

public interface UpCallBack {
    void onSucceed(int what , String response);
    void onAppUp(int i, Activity context, int what, String downurl, String updesc);
    void onFailed(int what , Response<String> response);
}
