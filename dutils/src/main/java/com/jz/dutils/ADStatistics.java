package com.jz.dutils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.jz.dutils.utils.CallServer;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;
import com.yanzhenjie.nohttp.rest.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static com.jz.dutils.utils.NetUtils.decryptParams;
import static com.jz.dutils.utils.NetUtils.encryptParams;
import static com.jz.dutils.utils.NetUtils.getUniqueId;

public class ADStatistics {
    public static  void Init(Activity context,String advertisers, String adid, String key, long requestTime, long responseTime, String action){
        Init(context,"http://api.vrmads.com/api/getAdJson.php",3,advertisers,adid,key,requestTime,responseTime,action);
    }

    public static  void Init(Context context,String advertisers, String adid, String key, long requestTime, long responseTime, String action){
        Init(context,"http://api.vrmads.com/api/getAdJson.php",3,advertisers,adid,key,requestTime,responseTime,action);
    }


    public  static void  Init(Activity context,String url ,int postKey, String advertisers, String adid, String key, long requestTime, long responseTime, String action){
        JSONObject js = new JSONObject();
        try {
            js.put("brandmark", UtilsConfig.brandmark);
            js.put("system",UtilsConfig.system);
            js.put("channelmark",UtilsConfig.channelmark);  //GOOGLE-Global
            js.put("equipment", Build.MODEL);
            js.put("equipmentid",getUniqueId(context));
            js.put("advertisers",advertisers);
            js.put("adid",adid);
            js.put("key",key);
            js.put("requestTime",requestTime);
            js.put("responseTime",responseTime);
            js.put("action",action);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject j = new JSONObject();
        try {
            j.put("jsonparam",js.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        InitializationConfig config = InitializationConfig.newBuilder(context)
                .retry(UtilsConfig.retry)
                .build();
        NoHttp.initialize(config);

        StringRequest request = new StringRequest(url, RequestMethod.POST);

        request.setConnectTimeout(10000);
        request.setReadTimeout(10000);
        request.setDefineRequestBody(encryptParams(j.toString()), Headers.HEAD_VALUE_CONTENT_TYPE_URLENCODED);
        request.setCancelSign(postKey); // 设置取消请求的标识符
        CallServer.getInstance().request(postKey, request, new SimpleResponseListener<String>() {
            @Override
            public void onStart(int what) {
                super.onStart(what);
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                super.onSucceed(what, response);
                String result = decryptParams(response.get());
                //Log.e("11111111111111",result);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                super.onFailed(what, response);
            }

            @Override
            public void onFinish(int what) {
                super.onFinish(what);
            }
        });
    }
    public  static void  Init(Context context, String url , int postKey, String advertisers, String adid, String key, long requestTime, long responseTime, String action){
        JSONObject js = new JSONObject();
        try {
            js.put("brandmark", UtilsConfig.brandmark);
            js.put("system",UtilsConfig.system);
            js.put("channelmark",UtilsConfig.channelmark);  //GOOGLE-Global
            js.put("equipment", Build.MODEL);
            js.put("equipmentid",getUniqueId(context));
            js.put("advertisers",advertisers);
            js.put("adid",adid);
            js.put("key",key);
            js.put("requestTime",requestTime);
            js.put("responseTime",responseTime);
            js.put("action",action);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject j = new JSONObject();
        try {
            j.put("jsonparam",js.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        InitializationConfig config = InitializationConfig.newBuilder(context)
                .retry(UtilsConfig.retry)
                .build();
        NoHttp.initialize(config);

        StringRequest request = new StringRequest(url, RequestMethod.POST);

        request.setConnectTimeout(10000);
        request.setReadTimeout(10000);
        request.setDefineRequestBody(encryptParams(j.toString()), Headers.HEAD_VALUE_CONTENT_TYPE_URLENCODED);
        request.setCancelSign(postKey); // 设置取消请求的标识符
        CallServer.getInstance().request(postKey, request, new SimpleResponseListener<String>() {
            @Override
            public void onStart(int what) {
                super.onStart(what);
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                super.onSucceed(what, response);
                String result = decryptParams(response.get());
                //Log.e("11111111111111",result);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                super.onFailed(what, response);
            }

            @Override
            public void onFinish(int what) {
                super.onFinish(what);
            }
        });
    }
}
