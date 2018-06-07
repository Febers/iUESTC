/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 上午12:08
 *
 */

package com.febers.iuestc.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.module.course.model.BeanCourse;
import com.febers.iuestc.utils.CourseTimeUtil;

public class CustomCourseDialog extends AlertDialog {

    private static final String TAG = "CustomCourseDialog";
    private AlertDialog dialog;
    private View view;
    private TextView tvCourseName;
    private TextView tvCourseClassRoom;
    private TextView tvCourseWeek;
    private TextView tvCourseTime;
    private TextView tvCourseTeacher;
    private Context context;

    public CustomCourseDialog(Context context, BeanCourse course) {
        super(context, R.style.Theme_AppCompat_Dialog);
        dialog = new AlertDialog.Builder(context).create();
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_course, null);
        tvCourseName = view.findViewById(R.id.tv_dialog_course_name);
        tvCourseClassRoom = view.findViewById(R.id.tv_dialog_course_classroom);
        tvCourseWeek = view.findViewById(R.id.tv_dialog_course_week);
        tvCourseTime = view.findViewById(R.id.tv_dialog_course_time);
        tvCourseTeacher = view.findViewById(R.id.tv_dialog_course_teacher);
        tvCourseName.setText(" "+course.getName());
        tvCourseClassRoom.setText("地点: "+course.getClassroom());
        tvCourseWeek.setText("周次:  "+ CourseTimeUtil.getUnderCourseWeeks(course.getWeek()));
        tvCourseTime.setText("时间: "+ CourseTimeUtil.getTimeDes(course.getTime()));
        tvCourseTeacher.setText("老师: "+course.getTeacher());
        dialog.setView(view);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
