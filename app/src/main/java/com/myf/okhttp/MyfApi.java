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

//    public String COMMON_IP = "http://192.168.5.122"; // 李阳本地服务
    public String COMMON_IP = "http://www.wevox.cn";// 生产地址
    public String ADD_STRING = "admin/mobile/"; // 附加串
    public String COMMON_URL_START = COMMON_IP + "/api/" + ADD_STRING; // 拼接地址
    /**
     * 刷新EMP_SID 登录之前调用
     */
//    public String VERIFYIMAGE = COMMON_URL_START + "VerifyImage";
    void getVerifyImageRespones(String logonId, StringCallback callback, String tag);
    /**
     * 登录接口
     */
    public String CARD_URL_LOGIN = COMMON_URL_START + "login";
    void getLoginRespones(String username,String password,String registration_id,StringCallback callback,String tag);
    /**
     * 骑手端员工信息
     */
    public String CARD_USER_INFO = COMMON_URL_START + "user_info";
    void getUserInfoRespones(String token,StringCallback callback,String tag);
    /**
     * 代取订单列表接口
     */
    public String CARD_ORDER_LISTS = COMMON_URL_START + "order_lists";
    void getExpressListsRespones(String token,String role_id,String express_time_type,String status,String express_id,String start_time,String end_time,String keyword,String dorm_order,String pay_order,String status_order,StringCallback callback,String tag);
    /**
     * 代取订单更改状态接口
     */
    public String CARD_EDIT_STATUS = COMMON_URL_START + "edit_status";
    void getEditStatusRespones(String token,String order_id,String status,StringCallback callback,String tag);
    /**
     * 关闭订单状态接口
     */
    public String CARD_CLOSE_ORDER = COMMON_URL_START + "close_order";
    void getCloseOrderRespones(String token,String order_id,StringCallback callback,String tag);
}

