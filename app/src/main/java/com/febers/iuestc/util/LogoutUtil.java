/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午7:35
 *
 */

package com.febers.iuestc.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.febers.iuestc.MyApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.net.SingletonClient;

public class LogoutUtil {

    private static Context context = MyApplication.getContext();

    public static boolean logoutSchool() {
        CustomSPUtil.getInstance().put(context.getString(R.string.sp_is_login), false);
        CustomSPUtil.getInstance().put(context.getString(R.string.sp_user_name), "");
        CustomSPUtil.getInstance().put(context.getString(R.string.sp_user_id), "");
        CustomSPUtil.getInstance().put(context.getString(R.string.sp_user_pw), "");
        CustomSPUtil.getInstance().put(context.getString(R.string.sp_exam_final), false);
        CustomSPUtil.getInstance().put(context.getString(R.string.sp_exam_middle), false);
        CustomSPUtil.getInstance().put(context.getString(R.string.sp_course_count), 0);
        CustomSPUtil.getInstance().put(context.getString(R.string.sp_get_course), false);
        CustomSPUtil.getInstance().put(context.getString(R.string.sp_course_last_time), "");
        CustomSPUtil.getInstance().put(context.getString(R.string.sp_now_week), 0);
        CustomSPUtil.getInstance().put(context.getString(R.string.sp_library_history), false);
        CustomSPUtil.getInstance().put(context.getString(R.string.sp_get_grade), false);
        CustomSPUtil.getInstance().put(context
                .getString(R.string.sp_course_first_get), true);

        SharedPreferences.Editor editor = context.getSharedPreferences("local_course", 0).edit();
        editor.clear();
        editor.apply();
        editor = context.getSharedPreferences("exam_1", 0).edit();
        editor.clear();
        editor.apply();
        editor = context.getSharedPreferences("exam_2", 0).edit();
        editor.clear();
        editor.apply();
        editor = context.getSharedPreferences("book_history", 0).edit();
        editor.clear();
        editor.apply();
        editor = context.getSharedPreferences(context.getString(R.string.sp_grade), 0).edit();
        editor.clear();
        editor.apply();
        editor = context.getSharedPreferences("user", 0).edit();
        editor.clear();
        editor.apply();
        SingletonClient.reset();
        return true;
    }
}
