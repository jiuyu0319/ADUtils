package com.jz.dutils.utils;

import android.app.Activity;

import com.yanzhenjie.nohttp.rest.Response;

public interface NetCallBack {
    void onSucceed(int what , String response);
    void onFailed(int what , Response<String> response);
}
