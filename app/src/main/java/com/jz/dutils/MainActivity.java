package com.jz.dutils;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.jz.dutils.utils.NetCallBack;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NoHttp.initialize(this); // NoHttp默认初始化。

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
        },"xindianying");
    }
}