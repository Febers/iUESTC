/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.exam.model;

import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseModel;
import com.febers.iuestc.entity.BeanExam;
import com.febers.iuestc.module.exam.presenter.ExamContract;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.util.SPUtil;
import com.febers.iuestc.util.SemesterUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 按照semester的形式获取、存储考试内容
 */
public class ExamModelImpl extends BaseModel implements ExamContract.Model {

    private static final String TAG = "ExamModelImpl";
    private ExamContract.Presenter examPresenter;
    private List<BeanExam> mExamList = new ArrayList<>();
    private int mType;

    public ExamModelImpl(ExamContract.Presenter presenter) {
        super(presenter);
        examPresenter = presenter;
    }

    /**
     * 获取我的考试
     * @param type  2表示期中， 1表示期末(默认)
     */
    @Override
    public void examService(Boolean isRefresh, int type) {
        mType = type;
        new Thread(()-> {
            if (SPUtil.getInstance().get("exam_"+mType, false) && (!isRefresh)) {
                loadLocalExam();
                return;
            }
            getHttpData();
        }).start();
    }

    @Override
    protected void getHttpData() {
        try {
            OkHttpClient client = SingletonClient.getInstance();
            String semesterId = SemesterUtil.getSemesterId();
            String examUrl = "http://eams.uestc.edu.cn/eams/stdExamTable!examTable.action?" +
                    "semester.id=" + semesterId + "&examType.id=" + mType;
            Request request = new Request.Builder()
                    .url(examUrl)
                    .get()
                    .build();
            Response examRes = client.newCall(request).execute();
            String result = examRes.body().string();
            if (!userAuthenticate(result)) {
                return;
            }
            mExamList = ExamResolver.resolveUnderExamHtml(result);
            examPresenter.examResult(new BaseEvent<>(BaseCode.UPDATE, getPostExams(mExamList)));
            ExamStore.saveToFile(mExamList, mType);
        } catch (SocketTimeoutException e) {
            serviceError(NET_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
            serviceError(NET_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            serviceError(UNKNOWN_ERROR);
        }
    }

    private void loadLocalExam() {
        mExamList = ExamStore.getByFile(mType);
        examPresenter.examResult(new BaseEvent<>(BaseCode.LOCAL, getPostExams(mExamList)));
    }

    /**
     * 筛选已发布考试情况的科目，其他隐藏
     * @param allExam 所有科目
     * @return 已发布
     */
    private List<BeanExam> getPostExams(List<BeanExam> allExam) {
        List<BeanExam> postExams = new ArrayList<>();
        for (int i = 0; i < allExam.size(); i++) {
            if (allExam.get(i).getNoPost()) {
                continue;
            }
            postExams.add(allExam.get(i));
        }
        return postExams;
    }
}
