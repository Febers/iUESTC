/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 上午11:30
 *
 */

package com.febers.iuestc.module.grade.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseModel;
import com.febers.iuestc.entity.BeanGradeSummary;
import com.febers.iuestc.entity.BeanGrade;
import com.febers.iuestc.module.grade.presenter.GradeContract;
import com.febers.iuestc.module.login.model.ILoginModel;
import com.febers.iuestc.module.login.model.LoginModel;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.util.RepeatLoginUtil;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.util.ApiUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GradeModel extends BaseModel implements IGradeModel {

    private static final String TAG = "GradeModel";
    private GradeContract.Presenter gradePresenter;

    public GradeModel(GradeContract.Presenter gradePresenter) {
        super(gradePresenter);
        this.gradePresenter = gradePresenter;
    }

    @Override
    public void gradeService(Boolean isRefresh, String semester) throws Exception {
        if (!isRefresh) {
            loadLocalGrade();
            return;
        }
        new Thread( () -> {
            if (mStudentType == UNDERGRADUATE) {
                getUnderGrade();
            } else if (mStudentType == POSTGRADUATE) {
                getPostGrade();
            }
        }).start();
    }

    /**
     * 本科生
     */
    private void getUnderGrade() {
        OkHttpClient client = SingletonClient.getInstance();
        try {
            Request request = new Request.Builder()
                    .url(ApiUtil.ALL_GRADE_URL)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            String stRes = response.body().string();
            if (stRes.contains("登录规则")) {
                ILoginModel loginModel = new LoginModel();
                if (FIRST_TRY) {
                    Boolean reLogin  = loginModel.reloginService();
                    if (!reLogin) {
                        serviceError(LOGIN_STATUS_ERRO);
                        return;
                    }
                    FIRST_TRY = false;
                    getUnderGrade();
                    return;
                }
            } else if (stRes.contains("重复登录")) {
                String url = RepeatLoginUtil.check(stRes);
                request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                response = client.newCall(request).execute();
                stRes = response.body().string();
            }
            resolveUnderGradeHtml(stRes);
        } catch (SocketTimeoutException e) {
            serviceError(NET_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
            serviceError(NET_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            serviceError(UNKONOW_ERROR);
        }
    }

    private void resolveUnderGradeHtml(String souceCode) {
        List<BeanGradeSummary> allGradeList = new ArrayList<>();
        List<BeanGrade> gradeList = new ArrayList<>();
        if (souceCode.equals("")) {
            gradePresenter.gradeResult("无", allGradeList, gradeList);
            return;
        }
        try {
            Document document = Jsoup.parse(souceCode);
            Elements elements = document.select("table[class=\"gridtable\"]");
            Element all = elements.get(0);
            Element per = elements.get(1);

            //依次为学年、学期、门数、总学分、绩点（5）
            elements = all.select("tr");
            for (int i = 1; i < elements.size()-2; i++) {
                Element e = elements.get(i);
                Elements elements1 = e.select("td");
                BeanGradeSummary allGrade = new BeanGradeSummary();
                for (int j = 0; j < elements1.size(); j++) {
                    allGrade.setYear(elements1.get(0).text());
                    allGrade.setSemester(elements1.get(1).text());
                    allGrade.setCourseCount(elements1.get(2).text());
                    allGrade.setAllScore(elements1.get(3).text());
                    allGrade.setAverageGPA(elements1.get(4).text());
                }
                allGradeList.add(allGrade);
            }

            //每门课占8，依次为学年学期、课程代码、课程序号、课程名称、课程类别、学分、总评成绩、最终成绩
            elements = per.select("tr");
            //遍历每个课程, 第一个为null， 忽略
            for (int i = 1; i < elements.size(); i++) {
                Element e = elements.get(i);
                Elements elements1 = e.select("td");
                BeanGrade grade = new BeanGrade();
                //遍历课程的八个属性
                for (int j = 0; j < elements1.size(); j++) {
                    grade.setSemester(elements1.get(0).text());
                    grade.setCourseCode(elements1.get(1).text());
                    grade.setCourseNum(elements1.get(2).text());
                    grade.setCourseName(elements1.get(3).text());
                    grade.setCourseType(elements1.get(4).text());
                    grade.setStudyScore(elements1.get(5).text());
                    grade.setScore(elements1.get(6).text());
                    grade.setFinalScore(elements1.get(7).text());
                }
                gradeList.add(grade);
            }
            saveUnderGrade(souceCode);
            CustomSharedPreferences.getInstance().put(mContext.getString(R.string.sp_get_grade), true);
            Collections.sort(allGradeList);
            gradePresenter.gradeResult("成功", allGradeList, gradeList);
        }catch (NullPointerException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPostGrade() {
        //TODO 研究生成绩
    }

    private void saveUnderGrade(String sourceCode) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(mContext.getString(R.string.sp_grade), 0).edit();
        editor.putString("sourceCode", sourceCode); //暂时先保存源码,偷懒
        editor.apply();
    }

    private void loadLocalGrade() {
        new Thread(()-> {
            SharedPreferences preferences = mContext.getSharedPreferences(mContext.getString(R.string.sp_grade), 0);
            String sourceCode = preferences.getString("sourceCode", "");
            resolveUnderGradeHtml(sourceCode);
        }).start();
    }
}
