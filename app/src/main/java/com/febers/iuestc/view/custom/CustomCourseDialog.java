/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:39
 *
 */

package com.febers.iuestc.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanCourse;
import com.febers.iuestc.util.CourseUtil;

import androidx.appcompat.app.AlertDialog;

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

    @SuppressLint("InflateParams")
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
        tvCourseName.setText(String.format(" %s", course.getName()));
        tvCourseClassRoom.setText(String.format("地点: %s", course.getClassroom()));
        tvCourseWeek.setText(String.format("周次:  %s", course.getWeek()));
        tvCourseTime.setText(String.format("时间:  %s", CourseUtil.getTimeDescription(course)));
        tvCourseTeacher.setText(String.format("老师: %s", course.getTeacher()));
        dialog.setView(view);
    }

    @SuppressLint("InflateParams")
    public CustomCourseDialog(Context context, BeanCourse course1, BeanCourse course2) {
        super(context, R.style.Theme_AppCompat_Dialog);
        dialog = new AlertDialog.Builder(context).create();
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_course_two, null);
        tvCourseName = view.findViewById(R.id.tv_dialog_course_one_name);
        tvCourseClassRoom = view.findViewById(R.id.tv_dialog_course_one_classroom);
        tvCourseWeek = view.findViewById(R.id.tv_dialog_course_one_week);
        tvCourseTime = view.findViewById(R.id.tv_dialog_course_one_time);
        tvCourseTeacher = view.findViewById(R.id.tv_dialog_course_one_teacher);

        TextView tvCourseName2 = view.findViewById(R.id.tv_dialog_course_two_name);
        TextView tvCourseClassRoom2 = view.findViewById(R.id.tv_dialog_course_two_classroom);
        TextView tvCourseWeek2 = view.findViewById(R.id.tv_dialog_course_two_week);
        TextView tvCourseTime2 = view.findViewById(R.id.tv_dialog_course_two_time);
        TextView tvCourseTeacher2 = view.findViewById(R.id.tv_dialog_course_two_teacher);

        tvCourseName.setText(String.format(" %s", course1.getName()));
        tvCourseClassRoom.setText(String.format("地点: %s", course1.getClassroom()));
        tvCourseWeek.setText(String.format("周次:  %s", course1.getWeek()));
        tvCourseTime.setText(String.format("时间:  %s", CourseUtil.getTimeDescription(course1)));
        tvCourseTeacher.setText(String.format("老师: %s", course1.getTeacher()));

        tvCourseName2.setText(String.format(" %s", course2.getName()));
        tvCourseClassRoom2.setText(String.format("地点: %s", course2.getClassroom()));
        tvCourseWeek2.setText(String.format("周次:  %s", course2.getWeek()));
        tvCourseTime2.setText(String.format("时间:  %s", CourseUtil.getTimeDescription(course2)));
        tvCourseTeacher2.setText(String.format("老师: %s", course2.getTeacher()));
        dialog.setView(view);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void hide() {
        dialog.hide();
    }
}
