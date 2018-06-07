/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:04
 *
 */

package com.febers.iuestc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *时间工具
 */
public class TimeUtil {
    /*
    获取两个时间间隔，以天数的形式返回
    获取的都为毫秒，之后再转变为天数
     */
    public static int getDateInteval(String beginTime, String endTime) {

        System.out.print("开始时间:");
        String str1 = beginTime;  //"yyyyMMdd"格式 如 20131022
        System.out.println("\n结束时间:");
        String str2 = endTime;  //"yyyyMMdd"格式 如 20131022

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");//输入日期的格式
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = simpleDateFormat.parse(str1);
            endDate = simpleDateFormat.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(beginDate);
        cal2.setTime(endDate);
        double dayCount = (cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*3600*24);//从间隔毫秒变成间隔天数
        System.out.println("\n相差"+dayCount+"天");
        return 0;
    }
}
