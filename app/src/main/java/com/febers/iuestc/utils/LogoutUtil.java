package com.febers.iuestc.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.net.SingletonClient;

public class LogoutUtil {
    private static Context context = BaseApplication.getContext();

    public static boolean logoutSchool() {
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_is_login), false);
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_user_name), "");
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_user_id), "");
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_user_pw), "");
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_exam_final), false);
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_exam_middle), false);
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_course_count), 0);
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_get_course), false);
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_course_last_time), "");
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_now_week), 0);
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_library_history), false);
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_get_grade), false);
        MySharedPreferences.getInstance().put(context
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
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_is_login), false);
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_user_id), "");
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_user_pw), "");
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_bind_number), "");
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_user_phone), "");
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_el_balance), "");
        MySharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_ec_balance), "");
        SharedPreferences.Editor editor = context.getSharedPreferences("local_pay_record", 0).edit();
        editor.clear();
        editor.commit();
        return true;
    }
}
