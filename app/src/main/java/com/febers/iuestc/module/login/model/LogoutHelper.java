package com.febers.iuestc.module.login.model;

import android.content.Context;

import com.febers.iuestc.MyApp;
import com.febers.iuestc.R;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.util.SPUtil;

import static com.febers.iuestc.base.Constants.COURSE_COUNT;
import static com.febers.iuestc.base.Constants.COURSE_FIRST_GET;
import static com.febers.iuestc.base.Constants.COURSE_GET;
import static com.febers.iuestc.base.Constants.COURSE_LAST_TIME;
import static com.febers.iuestc.base.Constants.EXAM_FIRST_TERM;
import static com.febers.iuestc.base.Constants.EXAM_SECOND_TERM;
import static com.febers.iuestc.base.Constants.GRADE_GOT;
import static com.febers.iuestc.base.Constants.IS_LOGIN;
import static com.febers.iuestc.base.Constants.COURSE_NOW_WEEK;
import static com.febers.iuestc.base.Constants.USER_ID;
import static com.febers.iuestc.base.Constants.USER_NAME;
import static com.febers.iuestc.base.Constants.USER_PW;

public class LogoutHelper {

    private static Context context = MyApp.getContext();

    public static void logout() {
        SPUtil.INSTANCE().put(IS_LOGIN, false);
        SPUtil.INSTANCE().put(USER_NAME, "");
        SPUtil.INSTANCE().put(USER_ID, "");
        SPUtil.INSTANCE().put(USER_PW, "");
        SPUtil.INSTANCE().put(EXAM_FIRST_TERM, false);
        SPUtil.INSTANCE().put(EXAM_SECOND_TERM, false);
        SPUtil.INSTANCE().put(COURSE_COUNT, 0);
        SPUtil.INSTANCE().put(COURSE_GET, false);
        SPUtil.INSTANCE().put(COURSE_LAST_TIME, "");
        SPUtil.INSTANCE().put(COURSE_NOW_WEEK, 0);
        SPUtil.INSTANCE().put(context.getString(R.string.sp_library_history), false);
        SPUtil.INSTANCE().put(GRADE_GOT, false);
        SPUtil.INSTANCE().put(COURSE_FIRST_GET, true);

        SingletonClient.reset();
    }
}
