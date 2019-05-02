/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午7:35
 *
 */

package com.febers.iuestc.module.login.model;

import android.content.Context;

import com.febers.iuestc.MyApp;
import com.febers.iuestc.R;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.util.SPUtil;

import static com.febers.iuestc.base.Constants.COURSE_COUNT;
import static com.febers.iuestc.base.Constants.COURSE_GOT;
import static com.febers.iuestc.base.Constants.IS_LOGIN;
import static com.febers.iuestc.base.Constants.NOW_WEEK;
import static com.febers.iuestc.base.Constants.USER_ID;
import static com.febers.iuestc.base.Constants.USER_NAME;
import static com.febers.iuestc.base.Constants.USER_PW;

public class LogoutHelper {

    private static Context context = MyApp.getContext();

    public static void logout() {
        SPUtil.getInstance().put(IS_LOGIN, false);
        SPUtil.getInstance().put(USER_NAME, "");
        SPUtil.getInstance().put(USER_ID, "");
        SPUtil.getInstance().put(USER_PW, "");
        SPUtil.getInstance().put(context.getString(R.string.sp_exam_final), false);
        SPUtil.getInstance().put(context.getString(R.string.sp_exam_middle), false);
        SPUtil.getInstance().put(COURSE_COUNT, 0);
        SPUtil.getInstance().put(COURSE_GOT, false);
        SPUtil.getInstance().put(context.getString(R.string.sp_course_last_time), "");
        SPUtil.getInstance().put(NOW_WEEK, 0);
        SPUtil.getInstance().put(context.getString(R.string.sp_library_history), false);
        SPUtil.getInstance().put(context.getString(R.string.sp_get_grade), false);
        SPUtil.getInstance().put(context
                .getString(R.string.sp_course_first_get), true);

        SingletonClient.reset();
    }
}
