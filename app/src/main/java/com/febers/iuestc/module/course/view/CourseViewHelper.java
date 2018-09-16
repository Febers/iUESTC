/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-7 下午4:05
 *
 */

package com.febers.iuestc.module.course.view;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanCourse;
import com.febers.iuestc.util.CourseUtil;
import com.febers.iuestc.util.CustomSPUtil;
import com.febers.iuestc.util.RandomUtil;
import com.febers.iuestc.view.custom.CustomCourseDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.febers.iuestc.module.course.model.CourseConstants.COURSE_NO_NOW;
import static com.febers.iuestc.module.course.model.CourseConstants.COURSE_OUT_WEEK;


public class CourseViewHelper implements View.OnClickListener {

    private static final String TAG = "CourseViewHelper";

    private int[][] mButtomIdArray = {
            {R.id.bt_course_001, R.id.bt_course_023, R.id.bt_course_045, R.id.bt_course_067, R.id.bt_course_089, R.id.bt_course_01011},
            {R.id.bt_course_101, R.id.bt_course_123, R.id.bt_course_145, R.id.bt_course_167, R.id.bt_course_189, R.id.bt_course_11011},
            {R.id.bt_course_201, R.id.bt_course_223, R.id.bt_course_245, R.id.bt_course_267, R.id.bt_course_289, R.id.bt_course_21011},
            {R.id.bt_course_301, R.id.bt_course_323, R.id.bt_course_345, R.id.bt_course_367, R.id.bt_course_389, R.id.bt_course_31011},
            {R.id.bt_course_401, R.id.bt_course_423, R.id.bt_course_445, R.id.bt_course_467, R.id.bt_course_489, R.id.bt_course_41011},
            {R.id.bt_course_501, R.id.bt_course_523, R.id.bt_course_545, R.id.bt_course_567, R.id.bt_course_589, R.id.bt_course_51011},
            {R.id.bt_course_601, R.id.bt_course_623, R.id.bt_course_645, R.id.bt_course_667, R.id.bt_course_689, R.id.bt_course_61011}};

    private int[] mBackground = {R.drawable.cornerbg_blue, R.drawable.cornerbg_green, R.drawable.cornerbg_orange,
            R.drawable.cornerbg_teal, R.drawable.cornerbg_cyan, R.drawable.cornerbg_red};

    private List<BeanCourse> mCourseList = new ArrayList<>();
    private CustomCourseDialog customCourseDialog;
    private FragmentActivity mActivity;

    public CourseViewHelper(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    void create(List<BeanCourse> courseList, int nowWeek, List<Button> buttonList) {
        mCourseList.clear();
        mCourseList.addAll(courseList);
        //与上次打开相比,更新周数
        int interval = CourseUtil.getWeekInterval();
        nowWeek = nowWeek + interval;

        for (int i = 0; i < mCourseList.size(); i++) {
            BeanCourse course = mCourseList.get(i);
            //通过判断周数判断是否要显示课程
            String week = mCourseList.get(i).getWeek();
            String result = CourseUtil.checkIsInNowWeek(week, nowWeek);
            if (result.equals(COURSE_NO_NOW)) {
                continue;
            }
            String stDay = course.getDay();
            String stTime = course.getTime();
            //为了确定在课表中的位置，取第一个数字
            Character timeFirst = stTime.charAt(0);
            int positionDay = Integer.valueOf(stDay);
            int positionTime = Integer.valueOf(timeFirst+"");
            positionTime = positionTime / 2;
            if (stTime.equals("1011")) {
                //解决最后两节课时坐标计算错误的问题
                positionTime = 5;
            }

            Button btn = mActivity.findViewById(mButtomIdArray[positionDay][positionTime]);
            setButtonHeight(btn, 1f);
            btn.setTag(R.id.btn_course_day_time, course.getDay()+course.getTime());
            //修改高度
            if (positionTime == 4 && stTime.contains("12")) {
                setButtonHeight(btn, 2f);
            } else if (positionTime == 4 && stTime.contains("11")) {
                setButtonHeight(btn, 1.5f);
            }

            btn.setText(course.getDetail());
            btn.setTextColor(Color.parseColor("#ffffff"));
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            if (Build.VERSION.SDK_INT >= 17) {
                btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }

            if (mCourseList.get(i).getRepeat()) {
                btn.setBackgroundResource(R.drawable.corner_with_rectangle);
            } else {
                btn.setBackgroundResource(mBackground[RandomUtil.getRandomFrom0(6)]);
            }

            if (result.equals(COURSE_OUT_WEEK)) {
                if (!course.getRepeat()) {
                    btn.setBackgroundResource(R.drawable.cornerbg_gray);
                }
            }
            btn.setVisibility(View.VISIBLE);
            btn.setOnClickListener(this);
            //获取当前标准格式的时间并保存
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String time = sdf.format(new Date());
            CustomSPUtil.getInstance().put(mActivity.getString(R.string.sp_course_last_time), time);
            CustomSPUtil.getInstance().put(mActivity.getString(R.string.sp_now_week), nowWeek);
            buttonList.add(btn);
        }
    }

    @Override
    public void onClick(View v) {
        String nameAndDayAndTime = v.getTag(R.id.btn_course_day_time).toString();
        for (int i = 0; i < mCourseList.size(); i++) {
            BeanCourse course1 = mCourseList.get(i);
            if ((course1.getDay()+course1.getTime()).equals(nameAndDayAndTime)) {
                if (!mCourseList.get(i).getRepeat()) {
                    customCourseDialog = new CustomCourseDialog(mActivity, course1);
                    customCourseDialog.show();
                    return;
                } else {
                    for (int j = 0; j < mCourseList.size(); j++) {
                        BeanCourse course2 = mCourseList.get(j);
                        if (i == j) {
                            continue;
                        }
                        if ((course1.getDay()+course1.getTime())
                                .equals(course2.getDay()+course2.getTime())) {
                            customCourseDialog = new CustomCourseDialog(mActivity,
                                    course1, course2);
                            customCourseDialog.show();
                            return;
                        }
                    }
                }
            }
        }
    }

    private void setButtonHeight(Button button, float multi) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
        params.height = ((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, multi*96, mActivity.getResources().getDisplayMetrics()));
        button.setLayoutParams(params);
    }
}
