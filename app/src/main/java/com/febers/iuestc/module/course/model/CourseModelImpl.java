/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.course.model;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseModel;
import com.febers.iuestc.entity.BeanCourse;
import com.febers.iuestc.module.course.presenter.CourseContract;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.util.CustomSPUtil;
import com.febers.iuestc.util.ApiUtil;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.febers.iuestc.util.CourseUtil.resolveRepeatCourse;

public class CourseModelImpl extends BaseModel implements CourseContract.ICourseModel {

    private static final String TAG = "CourseModelImpl";

    private List<BeanCourse> mCourseList = new ArrayList<>();
    private CourseContract.Presenter mCoursePresenter;
    private String mCourseHtml;

    public CourseModelImpl(CourseContract.Presenter coursePresenter) {
        super(coursePresenter);
        mCoursePresenter = coursePresenter;
    }

    @Override
    public void updateCourseService(Boolean isRefresh) {
        if (!isLogin()) {
            return;
        }
        Boolean gotCourse = CustomSPUtil.getInstance().get(mContext
                .getString(R.string.sp_get_course), false);
        new Thread( () ->{
            if (gotCourse && (!isRefresh)) {
                getSavedCourse();
                return;
            }
            if (mStudentType == UNDERGRADUATE) {
                getUnderCourseHtml();
            } else if (mStudentType == POSTGRADUATE) {
                getPostCourseHtml();
            }
        }).start();
    }

    /**
     * 获取本科生课表信息
     * post格式如下
        ignoreHead:1
        setting.kind:std
        startWeek:1
        semester.id:如果为0则表示当前学期
        ids:143831
        ids来自http://eams.uestc.edu.cn/eams/courseTableForStd.action里的一行代码
     */
    private void getUnderCourseHtml() {
        try {
            OkHttpClient client = SingletonClient.getInstance();
            Request request = new Request.Builder()
                    .url(ApiUtil.UNDER_COURSE_IDS_URL)
                    .build();
            Response response_ids = client.newCall(request).execute();
            String text_ids = response_ids.body().string();
            if (text_ids.contains("重复登录")) {
                if (TRY_TIMES <= 2) {
                    getUnderCourseHtml();
                    TRY_TIMES++;
                    return;
                } else {
                    mCoursePresenter.loginStatusFail();
                    return;
                }
            }
            String ids = getUnderIds(text_ids);

            FormBody body = new FormBody.Builder()
                    .add("ignoreHead", "1")
                    .add("setting.kind", "std")
                    .add("startWeek", "1")
                    .add("semester.id", "0")
                    .add("ids", ids)
                    .build();
            request = new Request.Builder()
                    .post(body)
                    .url(ApiUtil.UNDER_COURSE_TABLE_URL)
                    .build();
            Response response = client.newCall(request).execute();
            mCourseHtml = response.body().string();
            if (mCourseHtml.contains("登录规则")) {
                mCoursePresenter.loginStatusFail();
                return;
            }
            mCourseList = CourseResolver.resolveUnderCourseHtml(new StringBuilder(mCourseHtml));

        } catch (SocketTimeoutException e) {
            mCoursePresenter.underCourseResult(new BaseEvent<>(BaseCode.ERROR, new ArrayList<>()));
            CustomSPUtil.getInstance().put("get_course", false);
            serviceError(NET_TIMEOUT);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            mCoursePresenter.underCourseResult(new BaseEvent<>(BaseCode.ERROR, new ArrayList<>()));
            CustomSPUtil.getInstance().put("get_course", false);
            serviceError(UNKONOW_ERROR);
            return;
        }
        CustomSPUtil.getInstance().put("get_course", true);
        BaseEvent<List<BeanCourse>> event = new BaseEvent<>(BaseCode.UPDATE, resolveRepeatCourse(mCourseList));
        mCoursePresenter.underCourseResult(event);
    }

    //加载本地文件获得的课程列表
    private void getSavedCourse() {
        try {
            List<BeanCourse> courseList = CourseStore.get();
            mCoursePresenter.underCourseResult(new BaseEvent<>(BaseCode.LOCAL, resolveRepeatCourse(courseList)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取在网页中的ids
     */
    private String getUnderIds(String sourceCode) {
        int a = sourceCode.indexOf("ids");
        int start = sourceCode.indexOf("\"", a+4);
        int end = sourceCode.indexOf("\"", start+1);
        return sourceCode.substring(start+1, end);
    }

    private void getPostCourseHtml() {
        //TODO 获取并解析研究生课表
    }

}
