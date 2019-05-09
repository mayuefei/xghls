package com.myf.model;

import java.io.Serializable;
import java.util.List;

public class UserInfoRespones implements Serializable {

    /**
     * code : 1
     * msg : 获取员工信息成功
     * data : {"userName":"传媒快递取件员","schoolName":"山西传媒学院","roleName":"快递取件员","expressData":[{"express_name":"圆通","id":1},{"express_name":"申通","id":2},{"express_name":"汇通","id":3}]}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean implements Serializable{
        /**
         * userName : 传媒快递取件员
         * schoolName : 山西传媒学院
         * roleName : 快递取件员
         * expressData : [{"express_name":"圆通","id":1},{"express_name":"申通","id":2},{"express_name":"汇通","id":3}]
         */

        public String userName;
        public String schoolName;
        public String roleName;
        public List<ExpressDataBean> expressData;

        public static class ExpressDataBean implements Serializable{
            /**
             * express_name : 圆通
             * id : 1
             */

            public String express_name;
            public String id;
        }
    }
}
