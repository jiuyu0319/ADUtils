package com.jz.dutils;

import android.content.Context;
import android.os.Build;

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

/**
 * 统计
 */
public class StatisticsUtils {


    public static void Init(Context context,int postKey,String brandmark,String system,String channelmark,String url){
        Init(context,postKey,brandmark,system,channelmark,url);
    }

    public static void Init(Context context,String brandmark,String system,String channelmark,String url){
        Init(context,2,brandmark,system,channelmark,url);
    }

    public static void Init(Context context,int postKey,String brandmark,String system,String channelmark){
        JSONObject js = new JSONObject();

        try {
            js.put("brandmark", brandmark);
            js.put("system",system);
            js.put("channelmark",channelmark);  //GOOGLE-Global
            js.put("equipment", Build.MODEL);
            js.put("equipmentid",getUniqueId(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        InitializationConfig config = InitializationConfig.newBuilder(context)
                .retry(1)
                .build();
        NoHttp.initialize(config);

        StringRequest request = new StringRequest("http://api.vrmads.com/api/statistics.php", RequestMethod.POST);

        request.setConnectTimeout(10000);
        request.setReadTimeout(10000);
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
