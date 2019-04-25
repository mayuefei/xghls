package com.myf.util;

public class InitComm {
    private static InitComm initComm;
    private InitComm(){
    }
    public static InitComm init(){
        if (initComm == null){
            initComm = new InitComm();
        }
        return initComm;
    }
    public boolean isLogin;

    public String userToken = null;//申请服务器访问的Token
    public String userRoleId = null;//员工角色id:5取件员和6派件员
}
