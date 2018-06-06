package com.febers.iuestc.module.library.model;

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
