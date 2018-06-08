/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午7:35
 *
 */

package com.febers.iuestc.utils;

import android.util.Log;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *方法一
 * 通过课程的周数判断是否在当前周
 * 三种情况 1-17周(单周)，1-17周
 * 传入周详情和当前周
 */

public class CourseTimeUtil {

    private static final String TAG = "CourseTimeUtil";


    public static String checkIsInNowWeek(String week, int nowWeek) {
//        Log.d(TAG, "checkIsInNowWeek: week: " + week);
        String result = "true";
        //判断当前周为单周或者双周
        Boolean isDouble = (nowWeek%2 == 0);
        //首先判断单双周
        if (week.contains("单周")) {
            week = week.replace("周(单周)","");
            if (isDouble) {
//                Log.d(TAG, "isDouble: true and nowWeek is " + nowWeek + " nowWeek%2 is " + (nowWeek%2 == 0));
                return "false";
            }
        } else if (week.contains("双周")) {
            week = week.replace("周(双周)", "");
            if (!isDouble) {
//                Log.d(TAG, "isDouble: false and nowWeek is " + nowWeek + " nowWeek%2 is " + (nowWeek%2 == 0));
                return "false";
            }
        } else {
            week = week.replace("周","");
        }
        String [] weeks = week.split("-");
//        Log.d(TAG, "checkWeek: []" + weeks.toString());
        int startWeek = Integer.valueOf(weeks[0]);
        int endWeek = Integer.valueOf(weeks[1]);
//        Log.d(TAG, "checkWeek: start and end are " + startWeek + " " + endWeek);
        Boolean isShow = ((nowWeek>=startWeek) && (nowWeek<=endWeek));
        if (!isShow) {
            result = "false,noNow";
        }
        return result;
    }

    /**
     *判断周数类型，返回的结果有三种类型
     * @return  三周周类型
     */
    public static String getUnderCourseWeeks(String rawCode) {
        int startWeek = 1;
        int endWeek = 2;
        String  weekType = "";
//        rawCode = "01010101010101010100000000000000000000000000000000000";
        startWeek = rawCode.indexOf("1") - 1;
        endWeek = rawCode.lastIndexOf("1") - 1;
        String temp = rawCode.charAt(startWeek + 2) + "";

        if (temp.equals("1")) {
            weekType = "周";
        } else if (startWeek % 2 == 0) {
            weekType = "周(双周)";
        } else if (startWeek % 2 != 0) {
            weekType = "周(单周)";
        }
        return startWeek + "-" + endWeek + weekType;
    }

    /**
     * 将 0 01 转换为（周一一二节）
     * @param rawTime 原始
     * @return 周一一二节
     */
    public static String getTimeDes(String rawTime) {
        rawTime = rawTime.replace(" ", "");
        String describe = rawTime;
        try {
            int weekTime = Integer.valueOf(rawTime.charAt(0)+"") + 1;
            int dayTime = Integer.valueOf(rawTime.charAt(1)+"") + 1;
            describe = "周" + weekTime + "、 " + dayTime + "-" + (dayTime+1) + "节";
            if (dayTime == 9 && rawTime.endsWith("1")) {
                describe = "周" + weekTime + "、 " + dayTime + "-" +(dayTime+3) + "节";
            }
            if (dayTime == 9 && rawTime.endsWith("0")) {
                describe = "周" + weekTime + "、 " + dayTime + "-" + (dayTime+2) + "节";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return describe;
    }

    /*
    获取当前时间并计算属于一年中的第几周
    减去已经保存下来的周
    返回相隔的周数，如果为负，则返回0
    在view，通过相差的周数选择相应的课表
     */
    public static int intevalWeek() {
        int inteval;
        Date date;
        Date lastDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.setFirstDayOfWeek(Calendar.MONDAY);

        //获取当前标准格式的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date());
        //获取上次时间
        String lastTime = CustomSharedPreferences.getInstance().get(BaseApplication.getContext().getString(R.string.sp_course_last_time), time);

        try {
            date = sdf.parse(time);
            lastDate = sdf.parse(lastTime);
        } catch (ParseException e) {
            return 0;
        }
        calendar.setTime(date);
        lastCalendar.setTime(lastDate);
        inteval = calendar.get(Calendar.WEEK_OF_YEAR) - lastCalendar.get(Calendar.WEEK_OF_YEAR);
        if (inteval >= 0) {
            return inteval;
        }
        return 0;
    }
}
