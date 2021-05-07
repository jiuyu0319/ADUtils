package com.jz.dutils;

import android.content.Context;
import android.util.Log;

import com.jz.dutils.utils.CallServer;
import com.jz.dutils.utils.NetCallBack;
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

public class AdInit {
    public static void Init(Context context, int postKey ,NetCallBack callBack){
        Init(context,postKey,"http://api.vrmads.com/api/adlist.php",callBack);
    }
    public static void Init(Context context, int postKey, String url ,NetCallBack callBack){

        JSONObject js = new JSONObject();
        try {
            js.put("brandmark", UtilsConfig.brandmark);
            js.put("channelmark",UtilsConfig.channelmark);  //GOOGLE-Global

        } catch (JSONException e) {
            e.printStackTrace();
        }
        InitializationConfig config = InitializationConfig.newBuilder(context)
                .retry(UtilsConfig.retry)
                .build();
        NoHttp.initialize(config);

        StringRequest request = new StringRequest(url, RequestMethod.POST);

        request.setConnectTimeout(UtilsConfig.connectTimeout);
        request.setReadTimeout(UtilsConfig.readTimeout);
        request.setDefineRequestBody(encryptParams(js.toString()), Headers.HEAD_VALUE_CONTENT_TYPE_URLENCODED);
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
                callBack.onSucceed(what,result);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                super.onFailed(what, response);
                callBack.onFailed(what,response);
            }

            @Override
            public void onFinish(int what) {
                super.onFinish(what);
            }
        });
    }
}
