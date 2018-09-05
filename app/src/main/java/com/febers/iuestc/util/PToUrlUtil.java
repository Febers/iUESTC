/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 下午1:05
 *
 */

package com.febers.iuestc.util;

/**
 * 将pisition转换为对应的url地址返回
 */
public class PToUrlUtil {
    public static String getUrl(int position) {
        switch (position) {
            //教室查询
            case 0:
                return "https://jwc.uestc.ga/wx/SchAbout!toClassroom.action";
               //return "http://wx.jwc.uestc.edu.cn/wx/SchAbout!toClassroom.action";
            case 1:
                return "https://jwc.uestc.ga/wx/CourseAbout!toTodayCourse.action";
            case 2:
                return "https://jwc.uestc.ga/wx/CourseAbout!toSearchCourse.action";
            case 3:
                return "https://jwc.uestc.ga/wx/SchAbout!toSearchTeach.action";
            case 4:
                return " https://jwc.uestc.ga/wx/SchAbout!findNewsInfo.action?" +
                        "partId=37,62,4028811d56bccc720156bcf9a16f0004,4028811d5688c21501568db010930007";
            case 5:
                return " https://jwc.uestc.ga/wx/SchAbout!findNewsInfo.action?partId=4028811d54f83c720154ff69e1890002,39";
            case 6:
                return "https://jwc.uestc.ga/wx/SchAbout!findNewsInfo.action?partId=38,4028811d5688c21501568daf83b20006";
            case 7:
                return "https://jwc.uestc.ga/wx/SchAbout!findNewsInfo.action?partId=40";
            case 8:
                return "https://jwc.uestc.ga/wx/SchAbout!getWxInfo.action?t=102";
            case 9:
                return "https://jwc.uestc.ga/wx/SchAbout!getWxInfo.action?t=103";
            default:
                break;
        }
        return "";
    }
}
