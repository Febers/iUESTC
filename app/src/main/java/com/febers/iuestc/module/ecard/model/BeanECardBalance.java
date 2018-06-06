package com.febers.iuestc.module.ecard.model;

/**
 * 一卡通余额
 * {"data":{"balance":"48.89","buyer_id":87289,"card_status":"\u6b63\u5e38","memo":"\u67e5\u8be2\u6210\u529f",
 * "retcode":0,"school_id":1,"student_name":"\u4f55\u4f5c\u803f","student_no":"2016030301007"},"retcode":"000000","retmsg":"\u6267\u884c\u6210\u529f"}

 */
public class BeanECardBalance {
    private String retcode;
    private String retmsg;
    private data data;

    public BeanECardBalance.data getData() {
        return data;
    }

    public String getRetcode() {
        return retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public class data {
        private String balance;
        private String buyer_id;
        private String card_status;
        private String memo;
        private String retcode;
        private String school_id;
        private String student_name;
        private String student_no;

        public String getBalance() {
            return balance;
        }

        public String getBuyer_id() {
            return buyer_id;
        }

        public String getCard_status() {
            return card_status;
        }

        public String getMemo() {
            return memo;
        }

        public String getRetcode() {
            return retcode;
        }

        public String getSchool_id() {
            return school_id;
        }

        public String getStudent_name() {
            return student_name;
        }

        public String getStudent_no() {
            return student_no;
        }
    }
}
