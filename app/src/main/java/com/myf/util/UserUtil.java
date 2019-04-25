package com.myf.util;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.myf.MyfApplication;
import com.myf.model.LoginRespones;

public class UserUtil {
    private static final String PREF_ROLE_ID_KEY = "PREF_ROLE_ID_KEY";//员工角色id:5取件员和6派件员
    private UserUtil(){}
    /**
     * 用户登录
     */
    public static void loginIn(LoginRespones loginRespones){
        InitComm.init().isLogin = true;
        InitComm.init().userToken = loginRespones.data.token;
        InitComm.init().userRoleId = loginRespones.data.roleId;
        SharedPreferencesUtil.putSharedPreferences(MyfApplication.getContext(),PREF_ROLE_ID_KEY,loginRespones.data.roleId);
    }
    /**
     * 用户退出登录操作
     */
    public static void loginOut(){
        InitComm.init().isLogin = false;
        InitComm.init().userToken = null;
        Intent loginIntent = new Intent(Constant.BROADCAST_ACTION_LOGIN_STATE_CHANGE);
        LocalBroadcastManager.getInstance(MyfApplication.getContext()).sendBroadcast(loginIntent);
    }
}
