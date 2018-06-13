/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:35
 *
 */

package com.febers.iuestc.entity;

/**
 * 电费余额
 * {"result":true,"msg":"操作成功","data":{"room":"121523","balance":"43.37","amount":"19.95","stuNo":null,
 * "schoolCode":null,"feeName":null,"endDate":null,"remark":null,"chargeDetailIds":null,"existRoom":"1","status":"1",
 * "orgDesc":null},"page":null,"retcode":"000000","isLogin":true}

 */
public class BeanElecBalance {
    private String result;
    private String msg;
    private data data;

    public BeanElecBalance.data getData() {
        return data;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public class data{
        private String room;
        private String balance;
        private String amount;
        private String stuNo;
        private String schoolCode;
        private String feeName;
        private String endDate;
        private String remark;
        private String chargeDetailIds;
        private String existRoom;
        private String status;
        private String orgDesc;
        private String page;
        private String retcode;
        private String isLogin;

        public String getRoom() {
            return room;
        }

        public String getBalance() {
            return balance;
        }

        public String getAmount() {
            return amount;
        }

        public String getStuNo() {
            return stuNo;
        }

        public String getSchoolCode() {
            return schoolCode;
        }

        public String getFeeName() {
            return feeName;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getRemark() {
            return remark;
        }

        public String getChargeDetailIds() {
            return chargeDetailIds;
        }

        public String getExistRoom() {
            return existRoom;
        }

        public String getStatus() {
            return status;
        }

        public String getOrgDesc() {
            return orgDesc;
        }

        public String getPage() {
            return page;
        }

        public String getRetcode() {
            return retcode;
        }

        public String getIsLogin() {
            return isLogin;
        }
    }
}
