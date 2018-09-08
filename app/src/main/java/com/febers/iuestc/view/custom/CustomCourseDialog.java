/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:39
 *
 */

package com.febers.iuestc.view.custom;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanCourse;
import com.febers.iuestc.util.CourseUtil;

public class CustomCourseDialog extends AlertDialog {

    private static final String TAG = "CustomCourseDialog";
    private AlertDialog dialog;
    private View view;
    private TextView tvCourseName, tvCourseName2;
    private TextView tvCourseClassRoom, tvCourseClassRoom2;
    private TextView tvCourseWeek, tvCourseWeek2;
    private TextView tvCourseTime, tvCourseTime2;
    private TextView tvCourseTeacher, tvCourseTeacher2;
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
        tvCourseWeek.setText("周次:  "+ course.getWeek());
        tvCourseTime.setText("时间:  "+ CourseUtil.getTimeDescription(course));
        tvCourseTeacher.setText("老师: "+course.getTeacher());
        dialog.setView(view);
    }

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

        tvCourseName2 = view.findViewById(R.id.tv_dialog_course_two_name);
        tvCourseClassRoom2 = view.findViewById(R.id.tv_dialog_course_two_classroom);
        tvCourseWeek2 = view.findViewById(R.id.tv_dialog_course_two_week);
        tvCourseTime2 = view.findViewById(R.id.tv_dialog_course_two_time);
        tvCourseTeacher2 = view.findViewById(R.id.tv_dialog_course_two_teacher);

        tvCourseName.setText(" "+course1.getName());
        tvCourseClassRoom.setText("地点: "+course1.getClassroom());
        tvCourseWeek.setText("周次:  "+ course1.getWeek());
        tvCourseTime.setText("时间:  "+ CourseUtil.getTimeDescription(course1));
        tvCourseTeacher.setText("老师: "+course1.getTeacher());

        tvCourseName2.setText(" "+course2.getName());
        tvCourseClassRoom2.setText("地点: "+course2.getClassroom());
        tvCourseWeek2.setText("周次:  "+ course2.getWeek());
        tvCourseTime2.setText("时间:  "+ CourseUtil.getTimeDescription(course2));
        tvCourseTeacher2.setText("老师: "+course2.getTeacher());
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
