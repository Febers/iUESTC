/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:35
 *
 */

package com.febers.iuestc.entity;

/**
 * jsonp152420082932({
 "ResponseData": ":二楼C区 5行16列3层*CB2031488",
 "ResponseDetails": "非新书成功",
 "ResponseStatus": 1
 })
 */
public class BeanBookPosition {

    private String ResponseData;
    private String ResponseDetails;
    private String ResponseStatus;

    public String getResponseData() {
        return ResponseData;
    }

    public String getResponseDetails() {
        return ResponseDetails;
    }

    public String getResponseStatus() {
        return ResponseStatus;
    }
}
