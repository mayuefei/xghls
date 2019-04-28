package com.myf.okhttp;

import android.content.Context;
import android.text.TextUtils;

import com.myf.util.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpApi implements MyfApi {
    private static final String TAG = "OkHttpApi";
    // TODO: 2019/3/8 加载时间
    private final static int CONNECT_TIMEOUT = 30;
    private final static int WRITE_TIMEOUT = 30;
    private final static int READ_TIMEOUT = 30;
    private static final OkHttpApi INSTANCE = new OkHttpApi();
    /**
     * 用于开关okHttp debug信息
     */
    private final static boolean DEBUG = true;
    private static OkHttpClient sOkHttpClient;

    /**
     * 获取OKHttp实例
     *
     * @return
     */
    public static OkHttpApi getInstance() {
        return INSTANCE;
    }

    /**
     * 创建实例
     *
     * @param context
     */
    public static void init(Context context) {
        if (sOkHttpClient == null) {
            synchronized (OkHttpApi.class) {
                if (sOkHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .retryOnConnectionFailure(true)
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
//                            .cache(new Cache(context.getCacheDir(), cacheSize));
                    if (DEBUG) {
                        builder.addInterceptor(new LoggingInterceptor());
                    }
                    sOkHttpClient = builder.build();
                }
            }
        }
    }

    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            LogUtil.i(TAG, String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            LogUtil.i(TAG, String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }

    private void asyncGet(String Url, Map<String, String> params,
                          StringCallback callback) {
        Set<String> keySet = params.keySet();
        GetBuilder getBuilder = OkHttpUtils.get().url(Url);
        getBuilder.addHeader("Connection", "close");
        for (String key : keySet) {
            try {
                if (TextUtils.isEmpty(params.get(key))) {
                    getBuilder.addParams(key, params.get(key));
                } else {
                    getBuilder.addParams(key, URLEncoder.encode(params.get(key), "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        getBuilder.build().execute(callback);
    }

    /**
     * 登录接口
     * @param username
     * @param password
     * @param device_type
     * @param callback
     * @param tag
     */
    @Override
    public void getLoginRespones(String username, String password, String device_type, StringCallback callback, String tag) {
        Map<String,String> params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);
        params.put("device_type",device_type);
        asyncGet(CARD_URL_LOGIN,params,callback);
    }

    /**
     * 骑手端员工信息
     * @param token
     * @param callback
     * @param tag
     */
    @Override
    public void getUserInfoRespones(String token, StringCallback callback, String tag) {
        Map<String,String> params = new HashMap<>();
        params.put("token",token);
        asyncGet(CARD_USER_INFO,params,callback);
    }

    /**
     * 代取订单列表接口
     * @param token 判断员工唯一值
     * @param role_id 员工角色ID
     * @param express_time_type 取件时间类型
     * @param status    订单状态
     * @param express_id    快递公司ID
     * @param start_time    开始时间
     * @param end_time  结束时间
     * @param keyword   关键字
     * @param dorm_order    宿舍排序
     * @param pay_order     宿舍排序
     * @param status_order  宿舍排序
     */
    @Override
    public void getExpressListsRespones(String token, String role_id,String express_time_type, String status, String express_id, String start_time, String end_time, String keyword, String dorm_order, String pay_order, String status_order,StringCallback callback,String tag) {
        Map<String,String> params= new HashMap<>();
        params.put("token",token);
        params.put("role_id",role_id);
        params.put("express_time_type",express_time_type);
        params.put("status",status);
        params.put("express_id",express_id);
        params.put("start_time",start_time);
        params.put("end_time",end_time);
        params.put("keyword",keyword);
        params.put("dorm_order",dorm_order);
        params.put("pay_order",pay_order);
        params.put("status_order",status_order);
        asyncGet(CARD_EXPRESS_LISTS,params,callback);
    }

    @Override
    public void getVerifyImageRespones(String logonId, StringCallback callback, String tag) {
        Map<String, String> params = new HashMap<>();
        params.put("update", "6666");
//        asyncGet(VERIFYIMAGE, params, callback);
    }
}