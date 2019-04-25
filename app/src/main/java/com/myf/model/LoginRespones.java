package com.myf.model;

public class LoginRespones {

    /**
     * code : 1
     * msg : 登录成功!
     * data : {"token":"5d9c20f1c5e93313f016228dd96c03687cbdc6af647d4e76503293c5b73ad84f","roleId":"1"}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * token : 5d9c20f1c5e93313f016228dd96c03687cbdc6af647d4e76503293c5b73ad84f
         * roleId : 1
         */

        public String token;
        public String roleId;
    }
}
