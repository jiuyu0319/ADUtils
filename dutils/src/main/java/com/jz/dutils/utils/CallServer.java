package com.jz.dutils.utils;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

/**
 * 发起请求的类 单利模型
 * 全局请求只用这一个队列
 * Created by Mac on 2018/8/20.
 */

public class CallServer {

    private static  CallServer instance;
    private RequestQueue queue; // 请求队列
    private DownloadQueue downloadQueue; // 下载队列

    public static  CallServer getInstance() {
        if (instance == null) {
            synchronized ( CallServer.class) {
                if (instance == null)
                    instance = new  CallServer();
            }
        }
        return instance;
    }

    private CallServer() {
        queue = NoHttp.newRequestQueue(5);
        downloadQueue = NoHttp.newDownloadQueue();
    }

    /**
     * 发起请求
     * @param what
     * @param request
     * @param listener
     * @param <T>
     */
    public <T> void request(int what, Request<T> request, SimpleResponseListener<T> listener) {
        queue.add(what, request, listener);
    }

    /**
     * 发起下载请求
     * @param what
     * @param request
     * @param listener
     */
    public void downloadRequest(int what, DownloadRequest request, DownloadListener listener) {
        downloadQueue.add(what, request, listener);
    }

    /**
     * 程序退出时,调用此方法释放资源
     */
    public void stop() {
        queue.stop();
    }

    /**
     * 取消某一个请求
     * @param whatRequest
     */
    public void cancelRequest(int whatRequest) {
        queue.cancelBySign(whatRequest);
    }
}
