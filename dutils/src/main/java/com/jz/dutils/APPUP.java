package com.jz.dutils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import com.jz.dutils.utils.CallServer;
import com.jz.dutils.utils.NetCallBack;
import com.jz.dutils.utils.NetUtils;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;
import com.yanzhenjie.nohttp.rest.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 升级
 */
public class APPUP {

    public static void Init(Activity context, NetCallBack callBack){
        Init(context,1,"http://api.vrmads.com/api/upgrade.php",callBack);
    }

    public static void Init(Activity context,int postKey,String url, NetCallBack callBack)  {
        JSONObject js = new JSONObject();
        try {
            js.put("version", context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
            js.put("brandmark",UtilsConfig.brandmark);
            js.put("channelmark", UtilsConfig.channelmark);
        } catch (JSONException | PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String encryptParams = NetUtils.encryptParams(js.toString());
        InitializationConfig config = InitializationConfig.newBuilder(context)
                .retry(UtilsConfig.retry)
                .build();
        NoHttp.initialize(config);

        StringRequest request = new StringRequest(url, RequestMethod.POST);

        request.setConnectTimeout(UtilsConfig.connectTimeout);
        request.setReadTimeout(UtilsConfig.readTimeout);
        request.setDefineRequestBody(encryptParams, Headers.HEAD_VALUE_CONTENT_TYPE_URLENCODED);
        request.setCancelSign(postKey); // 设置取消请求的标识符
        CallServer.getInstance().request(postKey, request, new SimpleResponseListener<String>() {
            @Override
            public void onStart(int what) {
                super.onStart(what);
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                super.onSucceed(what, response);
                String result = NetUtils.decryptParams(response.get());
                if (callBack!=null)callBack.onSucceed(what,result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    if (code==200){
                        JSONObject data = new JSONObject(jsonObject.getString("data"));
                        int methodtype = data.getInt("methodtype");

                        if (methodtype!= 1){
                            String downurl = data.getString("downurl");
                            String updesc="";
                            if (data.has("updesc")){
                                updesc= data.getString("updesc");
                            }
                            callBack.onAppUp(what,context,methodtype,downurl,updesc);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                super.onFailed(what, response);
                if (callBack!=null)callBack.onFailed(what,response);
            }
            @Override
            public void onFinish(int what) {
                super.onFinish(what);
            }
        });
    }
}
