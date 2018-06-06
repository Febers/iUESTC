package com.febers.iuestc.module.ecard.model;

/**
 * 一卡通详情
 * stMore: {"data":{"school_logo":"http://api.bionictech.cn/static/xifu_files/schools/school_1/school_1.png",
 * "ykt_type":1,"is_cert":"yes","yjf_balance":"0.00","mobile":"18889363143","school_name":"\u7535\u5b50\u79d1\u6280\u5927\u5b66",
 * "ykt_balance":-1,"real_name":"\u4f55\u4f5c\u803f","studentno":"2016030301007","nopassword_amount":0,"stuempno":"2016030301007",
 * "yjf_bind_id":"20160826010011729050","school_id":1},"retcode":"0000","retmsg":""}
 */
public class BeanECardDetail {
    private String retcode;
    private String retmsg;
    private data data;

    public BeanECardDetail.data getData() {
        return data;
    }

    public String getRetcode() {
        return retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public class data{
        private String school_logo;
        private String ykt_type;
        private String is_cert;
        private String yjf_balance;
        private String mobile;
        private String school_name;
        private String ykt_balance;
        private String real_name;
        private String studentno;
        private String nopassword_amount;
        private String stuempno;
        private String yjf_bind_id;
        private String school_id;

        public String getSchool_logo() {
            return school_logo;
        }

        public String getYkt_type() {
            return ykt_type;
        }

        public String getIs_cert() {
            return is_cert;
        }

        public String getYjf_balance() {
            return yjf_balance;
        }

        public String getMobile() {
            return mobile;
        }

        public String getSchool_name() {
            return school_name;
        }

        public String getYkt_balance() {
            return ykt_balance;
        }

        public String getReal_name() {
            return real_name;
        }

        public String getStudentno() {
            return studentno;
        }

        public String getNopassword_amount() {
            return nopassword_amount;
        }

        public String getStuempno() {
            return stuempno;
        }

        public String getYjf_bind_id() {
            return yjf_bind_id;
        }

        public String getSchool_id() {
            return school_id;
        }
    }
}
