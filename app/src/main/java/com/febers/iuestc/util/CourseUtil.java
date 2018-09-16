/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午7:35
 *
 */

package com.febers.iuestc.util;

import android.util.Log;

import com.febers.iuestc.base.MyApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanCourse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.febers.iuestc.module.course.model.CourseConstants.WEEK_NORMAL;
import static com.febers.iuestc.module.course.model.CourseConstants.WEEK_SINGLE;
import static com.febers.iuestc.module.course.model.CourseConstants.WEEK_DOUBLE;
import static com.febers.iuestc.module.course.model.CourseConstants.COURSE_NOW_WEEK;
import static com.febers.iuestc.module.course.model.CourseConstants.COURSE_NO_NOW;
import static com.febers.iuestc.module.course.model.CourseConstants.COURSE_OUT_WEEK;

/**
 *方法一
 * 通过课程的周数判断是否在当前周
 * 三种情况 1-17周(单周)，1-17周
 * 传入周详情和当前周
 */

public class CourseUtil {

    private static final String TAG = "CourseUtil";

    /**
     * 根据传入的课程的01串，跟当前周数比较，判断是否为本周课程
     * 首先将01串转换为数组，然后以当前周为索引，判断所在位置是否为1
     * 如果为1，则为当前周数的课程，返回true
     * 如果为0，则非当前周的课程， 返回false
     */
    public static String checkIsInNowWeek(String week, int nowWeek) {
        try {
            //判断当前周为单周或者双周
            Boolean isDouble = (nowWeek%2 == 0);
            //首先判断单双周
            if (week.contains(WEEK_SINGLE)) {
                week = week.replace(WEEK_SINGLE,"");
                if (isDouble) {
                    return COURSE_NO_NOW;
                }
            } else if (week.contains(WEEK_DOUBLE)) {
                week = week.replace(WEEK_DOUBLE, "");
                if (!isDouble) {
                    return COURSE_NO_NOW;
                }
            } else {
                week = week.replace(WEEK_NORMAL,"");
            }
            String [] weeks = week.split("-");
            int startWeek = Integer.valueOf(weeks[0]);
            int endWeek = Integer.valueOf(weeks[1]);
            Boolean isShow = ((nowWeek>=startWeek) && (nowWeek<=endWeek));
            if (!isShow) {
                return COURSE_OUT_WEEK;
            }
            return COURSE_NOW_WEEK;
        } catch (Exception e) {
            e.printStackTrace();
            return COURSE_NOW_WEEK;
        }
    }

    /**
     * 判断周数类型，返回的结果有三种类型
     * @param rawCode = "01010101010101010100000000000000000000000000000000000";
     * @return  三周周类型
     */
    public static String getSimpleWeeks(String rawCode) {
        int startWeek = 1;
        int endWeek = 2;
        String  weekType = "";
        startWeek = rawCode.indexOf("1");
        endWeek = rawCode.lastIndexOf("1");
        String temp = rawCode.charAt(startWeek + 1) + "";

        if (temp.equals("1")) {
            weekType = WEEK_NORMAL;
        } else if (startWeek % 2 == 0) {
            weekType = WEEK_DOUBLE;
        } else if (startWeek % 2 != 0) {
            weekType = WEEK_SINGLE;
        }
        return weekType + (startWeek) + "-" + (endWeek);
    }

    /**
     * 将 0 01 转换为（周一一二节）
     * @param course 课程
     * @return 周一一二节
     */
    public static String getTimeDescription(BeanCourse course) {
        StringBuilder describe = new StringBuilder();
        try {
            describe.append("周")
                    .append(Integer.valueOf(course.getDay()) + 1)
                    .append("、")
                    .append(course.getTime())
                    .append("节");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return describe.toString();
    }

    /**
     * 获取当前时间并计算属于一年中的第几周
     * 减去已经保存下来的周
     * 返回相隔的周数，如果为负，则返回0
     * 在view，通过相差的周数选择相应的课表
     * @return 间隔的周数
     */
    public static int getWeekInterval() {
        int interval;
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
        String lastTime = CustomSPUtil.getInstance().get(MyApplication.getContext().getString(R.string.sp_course_last_time), time);

        try {
            date = sdf.parse(time);
            lastDate = sdf.parse(lastTime);
        } catch (ParseException e) {
            return 0;
        }
        calendar.setTime(date);
        lastCalendar.setTime(lastDate);
        interval = calendar.get(Calendar.WEEK_OF_YEAR) - lastCalendar.get(Calendar.WEEK_OF_YEAR);
        if (interval >= 0) {
            return interval;
        }
        return 0;
    }

    /**
     * 找出课表中时间相同的课程，然后标记
     * 判断课程重复的依据是天数和节数都相同
     * @param courseList 课程列表
     * @return courseList 标记之后的课程列表
     */
    public static List<BeanCourse> resolveRepeatCourse(List<BeanCourse> courseList) {
        for (int i = 0; i < courseList.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (courseList.get(i).getTime().equals(courseList.get(j).getTime()) && courseList.get(i).getDay().equals(courseList.get(j).getDay())) {
                    courseList.get(i).setRepeat(true);
                    courseList.get(j).setRepeat(true);
                }
            }
        }
        return courseList;
    }
}
