package com.myf.okhttp;

import com.zhy.http.okhttp.callback.StringCallback;

public interface MyfApi {

//    public String COMMON_IP = "http://179.169.13.113"; // uat3
//    public String COM_PORT = "7007";// uat3
//    public String COM_PORT = "7007";// uat3
//    public String COMMON_IP = "http://59.48.96.118"; // 测试地址
//    public String COM_PORT = "7007";// 测试地址
//    public String ADD_STRING = "mobileServer/"; // 附加串
//    public String COMMON_URL_START = COMMON_IP + ":" + COM_PORT + "/" + ADD_STRING; // 拼接地址

    public String COMMON_IP = "http://192.168.5.122"; // 李阳本地服务
    public String ADD_STRING = "admin/mobile"; // 附加串
    public String COMMON_URL_START = COMMON_IP + "/api/" + ADD_STRING + "/"; // 拼接地址
    /**
     * 刷新EMP_SID 登录之前调用
     */
//    public String VERIFYIMAGE = COMMON_URL_START + "VerifyImage";
    void getVerifyImageRespones(String logonId, StringCallback callback, String tag);
    /**
     * 登录接口
     */
    public String CARD_URL_LOGIN = COMMON_URL_START + "login";
    void getLoginRespones(String username,String password,String device_type,StringCallback callback,String tag);
    /**
     * 骑手端员工信息
     */
    public String CARD_USER_INFO = COMMON_URL_START + "user_info";
    void getUserInfoRespones(String token,StringCallback callback,String tag);
}

