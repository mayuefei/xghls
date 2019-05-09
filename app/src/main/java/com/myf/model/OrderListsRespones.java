package com.myf.model;

import java.util.List;

public class OrderListsRespones {

    /**
     * code : 1
     * msg : 获取订单信息成功!
     * data : {"expressTypeCount":"10","distributeCount":"3","notfindCount":"1","pickCount":"5","orderData":[{"orderId":"7","pickCode":"3333","expressName":"圆通","contactsName":"郭宏啊","contactsPhone":"18888888889","schoolName":"山西传媒学院","buildingName":"东区03","remark":"","status":"1","dromNum":"11","createTime":"2019-05-08 00:00:10","expressTimeType":"1"},{"orderId":"8","pickCode":"3333","expressName":"圆通","contactsName":"郭宏啊","contactsPhone":"18888888889","schoolName":"山西传媒学院","buildingName":"东区03","remark":"","status":"1","dromNum":"11","createTime":"2019-05-08 00:00:10","expressTimeType":"1"},{"orderId":"10","pickCode":"3333","expressName":"圆通","contactsName":"郭宏啊","contactsPhone":"18888888889","schoolName":"山西传媒学院","buildingName":"东区03","remark":"","status":"1","dromNum":"11","createTime":"2019-05-08 00:00:10","expressTimeType":"1"}]}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * expressTypeCount : 10
         * distributeCount : 3
         * notfindCount : 1
         * pickCount : 5
         * orderData : [{"orderId":"7","pickCode":"3333","expressName":"圆通","contactsName":"郭宏啊","contactsPhone":"18888888889","schoolName":"山西传媒学院","buildingName":"东区03","remark":"","status":"1","dromNum":"11","createTime":"2019-05-08 00:00:10","expressTimeType":"1"},{"orderId":"8","pickCode":"3333","expressName":"圆通","contactsName":"郭宏啊","contactsPhone":"18888888889","schoolName":"山西传媒学院","buildingName":"东区03","remark":"","status":"1","dromNum":"11","createTime":"2019-05-08 00:00:10","expressTimeType":"1"},{"orderId":"10","pickCode":"3333","expressName":"圆通","contactsName":"郭宏啊","contactsPhone":"18888888889","schoolName":"山西传媒学院","buildingName":"东区03","remark":"","status":"1","dromNum":"11","createTime":"2019-05-08 00:00:10","expressTimeType":"1"}]
         */

        public String expressTypeCount;
        public String distributeCount;
        public String notfindCount;
        public String pickCount;
        public List<OrderDataBean> orderData;

        public static class OrderDataBean {
            /**
             * orderId : 7
             * pickCode : 3333
             * expressName : 圆通
             * contactsName : 郭宏啊
             * contactsPhone : 18888888889
             * schoolName : 山西传媒学院
             * buildingName : 东区03
             * remark :
             * status : 1
             * dromNum : 11
             * createTime : 2019-05-08 00:00:10
             * expressTimeType : 1
             */

            public String orderId;
            public String pickCode;
            public String expressName;
            public String contactsName;
            public String contactsPhone;
            public String schoolName;
            public String buildingName;
            public String remark;
            public String status;
            public String dromNum;
            public String createTime;
            public String expressTimeType;
        }
    }
}
