package com.febers.iuestc.utils;

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
 * Created by 23033 on 2018/3/31.
 */

public class CourseWeekUtil {

    private static final String TAG = "CourseWeekUtil";

    public static String  check(String week, int nowWeek) {
//        Log.d(TAG, "check: week: " + week);
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
        String lastTime = MySharedPreferences.getInstance().get(BaseApplication.getContext().getString(R.string.sp_course_last_time), time);

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
