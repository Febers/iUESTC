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

public class CustomCourseDialog extends AlertDialog {

    private AlertDialog dialog;
    private View view;
    private TextView tvCourseName;
    private TextView tvCourseClassRoom;
    private TextView tvCourseWeek;
    private TextView tvCourseTime;
    private TextView tvCourseTeacher;

    public CustomCourseDialog(Context context, BeanCourse course) {
        super(context);
        dialog = new AlertDialog.Builder(context).create();
        view = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        tvCourseName = view.findViewById(R.id.tv_dialog_course_name);
        tvCourseClassRoom = view.findViewById(R.id.tv_dialog_course_classroom);
        tvCourseWeek = view.findViewById(R.id.tv_dialog_course_week);
        tvCourseTime = view.findViewById(R.id.tv_course_time);
        tvCourseTeacher = view.findViewById(R.id.tv_course_teacher);
        tvCourseName.setText(course.getName());
        tvCourseClassRoom.setText(course.getClassroom());
        tvCourseWeek.setText(course.getWeek());
        tvCourseTime.setText(course.getTime());
        tvCourseTeacher.setText(course.getTeacher());
        dialog.setView(view);
    }
}
