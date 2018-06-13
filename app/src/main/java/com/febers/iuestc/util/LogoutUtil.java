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

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.net.SingletonClient;

public class LogoutUtil {
    private static Context context = BaseApplication.getContext();

    public static boolean logoutSchool() {
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_is_login), false);
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_user_name), "");
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_user_id), "");
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_user_pw), "");
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_exam_final), false);
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_exam_middle), false);
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_course_count), 0);
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_get_course), false);
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_course_last_time), "");
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_now_week), 0);
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_library_history), false);
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_get_grade), false);
        CustomSharedPreferences.getInstance().put(context
                .getString(R.string.sp_course_first_get), true);

        SharedPreferences.Editor editor = context.getSharedPreferences("local_course", 0).edit();
        editor.clear();
        editor.commit();
        editor = context.getSharedPreferences("exam_1", 0).edit();
        editor.clear();
        editor.commit();
        editor = context.getSharedPreferences("exam_2", 0).edit();
        editor.clear();
        editor.commit();
        editor = context.getSharedPreferences("book_history", 0).edit();
        editor.clear();
        editor.commit();
        editor = context.getSharedPreferences(context.getString(R.string.sp_grade), 0).edit();
        editor.clear();
        editor.commit();
        SingletonClient.reset();
        return true;
    }

    public static boolean logoutECard() {
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_is_login), false);
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_user_id), "");
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_user_pw), "");
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_bind_number), "");
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_user_phone), "");
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_el_balance), "");
        CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_ec_balance), "");
        SharedPreferences.Editor editor = context.getSharedPreferences("local_pay_record", 0).edit();
        editor.clear();
        editor.commit();
        return true;
    }
}
