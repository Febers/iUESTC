/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.course.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseModel;
import com.febers.iuestc.entity.BeanCourse;
import com.febers.iuestc.module.course.presenter.CourseContract;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.util.ApiUtil;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.febers.iuestc.util.CourseTimeUtil.getUnderCourseWeeks;

public class CourseModel extends BaseModel implements ICourseModel {

    private static final String TAG = "CourseModel";

    private List<BeanCourse> mCourseList = new ArrayList<>();
    private Context mContext = BaseApplication.getContext();
    private CourseContract.Presenter mCoursePresenter;
    private String mCourseHtml;

    public CourseModel(CourseContract.Presenter coursePresenter) {
        super(coursePresenter);
        mCoursePresenter = coursePresenter;
    }

    @Override
    public void updateCourseService(Boolean isRefresh) {
        if (!isLogin()) {
            return;
        }
        Boolean gotCourse = CustomSharedPreferences.getInstance().get(mContext
                .getString(R.string.sp_get_course), false);
        if (gotCourse && (!isRefresh)) {
            localCourseService();
            return;
        }
        new Thread( () ->{
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
            }
        } catch (SocketTimeoutException e) {
            CustomSharedPreferences.getInstance().put("get_course", false);
            serviceError(NET_TIMEOUT);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            CustomSharedPreferences.getInstance().put("get_course", false);
            serviceError(UNKONOW_ERROR);
            return;
        }
        //解析源码成List格式，发送给presenter，并存储
        resolveUnderCourseHtml(mCourseHtml);
    }

    private void resolveUnderCourseHtml(String html) {
        try {
            int pStart = html.indexOf("activity = new TaskActivity");
            if (pStart == -1) {
                Log.d(TAG, "resolveUnderCourseHtml: pStart = -1");
                mCoursePresenter.underCourseResult(new BaseEvent<>(BaseCode.ERROR, new ArrayList<>()));
                return;
            }
            int pEnd = html.lastIndexOf("table0.marshalTable");
            String s1 = html.substring(pStart, pEnd);

            pStart = s1.indexOf("activity = new TaskActivity");
            pEnd = s1.lastIndexOf("activity = new TaskActivity");

            List<String> partList = new ArrayList<>();
            for (int pNext = 0; ; ) {
                pStart = pNext;
                if (pStart == pEnd) {   //说明只剩最后一节课的信息
                    partList.add(s1.substring(pEnd, s1.length()-1));
                    break;
                }
                pNext = s1.indexOf("activity = new TaskActivity", pStart+1);
                partList.add(s1.substring(pStart, pNext-1));
            }
            //保存课程数目
            CustomSharedPreferences.getInstance()
                    .put(getStringById(R.string.sp_course_count), partList.size());
            for (int i = 0; i < partList.size(); i++) {
                getPerUnderCourse(partList.get(i), i);
            }
            BaseEvent<List<BeanCourse>> event = new BaseEvent(BaseCode.UPDATE, resolveRepeatCourse(mCourseList));
            mCoursePresenter.underCourseResult(event);
        } catch (Exception e) {
            e.printStackTrace();
            serviceError(UNKONOW_ERROR);
            return;
        }
        CustomSharedPreferences.getInstance().put(getStringById(R.string.sp_get_course), true);
    }

    /**
     * 通过传入的每节课的字符串，提取每节课
     * 通过循环的方式先获取前几个属性
     */
    private void getPerUnderCourse(String stCourse, int i) {
        List<String> courseDetail = new ArrayList<>();
        try {
            int pStart = stCourse.indexOf("\"");
            int pNext = 0;
            for (int j = 0; j < 7; j++) {
                pNext = stCourse.indexOf("\"", pStart+1);
                //多个老师之间逗号隔开，会影响下面的解析，所以把出现的逗号都改成顿号
                courseDetail.add(stCourse.substring(pStart+1, pNext).replaceAll(",", "、"));
                pStart = stCourse.indexOf("\"", pNext+1);
            }
            int pEquote = stCourse.indexOf("=", pNext);
            Character day = stCourse.charAt(pEquote+1);
            courseDetail.add(day.toString());

            int pPlusStart = stCourse.indexOf("+", pNext);
            int pFenhao = stCourse.indexOf(";", pPlusStart);
            int pPlusEnd = stCourse.lastIndexOf("+");

            String stTime = "";
            for (int k = 0; k < 10; k++) {
                stTime += stCourse.substring(pPlusStart+1, pFenhao);
                if (pPlusStart == pPlusEnd) {
                    break;
                }
                pPlusStart = stCourse.indexOf("+", pPlusStart+1);
                pFenhao = stCourse.indexOf(";", pPlusStart);
            }
            courseDetail.add(stTime);
            //[10674, 李春梅, 12150(K0200910.23), 电子技术应用实验 I(K0200910.23), 320, 基础实验大楼339,
            // 01010101010101010100000000000000000000000000000000000, 0, 23]
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //保存每节课的信息,同时去掉两边的[]以便还原
        SharedPreferences spLocalCourse = mContext.getSharedPreferences("local_course", 0);
        SharedPreferences.Editor editor = spLocalCourse.edit();
        editor.putString("beanCourse" + i, courseDetail.toString().replace("[", "").replace("]", ""));
        editor.commit();

        //teacher, name, classroom, week, time
        BeanCourse beanCourse = new BeanCourse(courseDetail.get(1), courseDetail.get(3), courseDetail.get(5),
                getUnderCourseWeeks(courseDetail.get(6)), " "+courseDetail.get(7)+" "+courseDetail.get(8));
        mCourseList.add(beanCourse);
    }

    //加载本地文件获得的课程列表
    @Override
    public void localCourseService() {
        try {
            List<BeanCourse> courseList = new ArrayList<>();
            int i = CustomSharedPreferences.getInstance().get("course_count", 10);
            for (int j = 0; j < i; j++) {
                SharedPreferences spLocalCourse = BaseApplication.getContext().getSharedPreferences("local_course", 0);
                String s = spLocalCourse.getString("beanCourse"+j, "");
                String [] ss = s.split(",");
                ss[6] = getUnderCourseWeeks(ss[6]);    //转换成单双周
                List<String> list = Arrays.asList(ss);
                //get(6)为1-17周, get(7)为(周)0，get(8)为 01(节)
                BeanCourse beanCourse = new BeanCourse(list.get(1), list.get(3), list.get(5), list.get(6), list.get(7)+list.get(8));
                courseList.add(beanCourse);
            }
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

    private List<BeanCourse> resolveRepeatCourse(List<BeanCourse> courseList) {
        for (int i = 0; i < courseList.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (courseList.get(i).getTime().equals(courseList.get(j).getTime())) {
                    courseList.get(i).setRepeat(true);
                    courseList.get(j).setRepeat(true);
                }
            }
        }
        return courseList;
    }
}
