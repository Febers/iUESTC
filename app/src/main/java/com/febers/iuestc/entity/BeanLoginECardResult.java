/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:35
 *
 */

package com.febers.iuestc.entity;

/**
 * 登录喜付之后返回json数据的JavaBean类
 * 成功时返回
 * {"data":{"userid":"87289"},"retcode":"0000","retmsg":"\u767b\u5f55\u6210\u529f"}
 * 密码错误返回
 * {"data":{},"retcode":"2104","retmsg":"\u8d26\u53f7\u6216\u5bc6\u7801\u9519\u8bef"}
 */
public class BeanLoginECardResult {

    private String retcode;
    private String retmsg;
    private data data;

    public String getRetcode() {
        return retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public data getData() {
        return data;
    }

    public class data {

        private String userid;

        public String getUserid() {
            return userid;
        }
    }
}
