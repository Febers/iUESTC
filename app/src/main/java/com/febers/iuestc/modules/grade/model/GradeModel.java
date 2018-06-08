/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 上午11:30
 *
 */

package com.febers.iuestc.modules.grade.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.modules.grade.contract.GradeContract;
import com.febers.iuestc.modules.login.model.ILoginModel;
import com.febers.iuestc.modules.login.model.LoginModel;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.utils.RepeatLoginUtil;
import com.febers.iuestc.utils.CustomSharedPreferences;
import com.febers.iuestc.utils.ApiUtil;

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

public class GradeModel implements IGradeModel {

    private static final String TAG = "GradeModel";
    private Context context = BaseApplication.getContext();
    private GradeContract.Presenter gradePresenter;
    private int tryTime = 1;

    public GradeModel(GradeContract.Presenter gradePresenter) {
        this.gradePresenter = gradePresenter;
    }

    @Override
    public void gradeService(Boolean isRefresh, String semester) throws Exception {
        if (!isRefresh) {
            loadLocalGrade();
            return;
        }
        int studentType = CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_student_type), 0);
        new Thread( () -> {
            if (studentType == 0) {
                getUnderGrade();
            } else if (studentType == 1) {
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
                if (tryTime == 1) {
                    Boolean reLogin  = loginModel.reloginService();
                    if (!reLogin) {
                        gradePresenter.errorResult("登录状态发生改变，请重新登录");
                        return;
                    }
                    tryTime++;
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resolveUnderGradeHtml(String souceCode) {
        List<BeanAllGrade> allGradeList = new ArrayList<>();
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
                BeanAllGrade allGrade = new BeanAllGrade();
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
            CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_get_grade), true);
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
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.sp_grade), 0).edit();
        editor.putString("sourceCode", sourceCode); //暂时先保存源码,偷懒
        editor.apply();
    }

    private void loadLocalGrade() {
        new Thread(()-> {
            SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.sp_grade), 0);
            String sourceCode = preferences.getString("sourceCode", "");
            resolveUnderGradeHtml(sourceCode);
        }).start();
    }
}
