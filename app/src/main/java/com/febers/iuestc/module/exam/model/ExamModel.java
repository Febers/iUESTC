package com.febers.iuestc.module.exam.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.module.exam.contract.ExamContract;
import com.febers.iuestc.module.login.model.ILoginModel;
import com.febers.iuestc.module.login.model.LoginModel;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.utils.CustomSharedPreferences;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
public class ExamModel implements IExamModel {

    private static final String TAG = "ExamModel";
    private ExamContract.Presenter examPresenter;
    private List<BeanExam> examList = new ArrayList<>();
    private int tryTime = 1;
    private int mType;
    private Context context = BaseApplication.getContext();

    public ExamModel(ExamContract.Presenter presenter) {
        examPresenter = presenter;
    }

    /**
     * 获取我的考试
     * @param type  2表示期中， 1表示期末(默认)
     */
    @Override
    public void examService(Boolean isRefresh, int type) {
        mType = type;
        if (!BaseApplication.isLogin()) {
            return;
        }
        if (CustomSharedPreferences.getInstance().get("exam_"+mType, false) && (!isRefresh)) {
            loadLocalExam(mType);
            return;
        }
        getExamHtml(mType);
    }

    private void getExamHtml(final int type) {
        new Thread( () -> {
            try {
                OkHttpClient client = SingletonClient.getInstance();
                String examUrl = "http://eams.uestc.edu.cn/eams/stdExamTable!examTable.action?" +
                        "semester.id="+context.getString(R.string.sp_semester)+"&examType.id="+type;
                Request request = new Request.Builder()
                        .url(examUrl)
                        .get()
                        .build();
                Response examRes = client.newCall(request).execute();
                String result = examRes.body().string();
                if (result.contains("登录规则")) {
                    ILoginModel loginModel = new LoginModel();
                    if (tryTime == 1) {
                        Boolean reLogin  = loginModel.reloginService();
                        if (!reLogin) {
                            examPresenter.errorResult("登录状态发生改变，请重新登录");
                            return;
                        }
                        tryTime++;
                        getExamHtml(type);
                        return;
                    }
                }else if (result.contains("重复登录")) {
                    request = new Request.Builder()
                            .url(examUrl)
                            .get()
                            .build();
                    examRes = client.newCall(request).execute();
                    result = examRes.body().string();
                }
                resolveExam(result);
            } catch (SocketTimeoutException e) {
                examPresenter.errorResult("网络超时");
            } catch (IOException e) {
                e.printStackTrace();
                examPresenter.errorResult("检测到异常");
            } catch (Exception e) {
                e.printStackTrace();
                examPresenter.errorResult("检测到异常");
            }
        }).start();
    }

    private void resolveExam(String sourceCode) {
        Document docFinal = Jsoup.parse(sourceCode);
        Elements elsFinal = docFinal.select("tr[align=\"center\"][onclick=\"onRowChange(event)\"]");
        for (int i = 0; i < elsFinal.size(); i++) {
            Element e = elsFinal.get(i);
            BeanExam exam = new BeanExam();
            Elements perExam = e.select("td");
            exam.setNum(perExam.get(0).text());
            exam.setName(perExam.get(1).text());
            if (perExam.size() < 6) {
                exam.setNoPost(true);
                examList.add(exam);
                continue;
            }
            if (perExam.size() == 7) {
                exam.setDate(perExam.get(2).text());
                exam.setTime(perExam.get(3).text());
                exam.setPosition("位置未发布");
                exam.setNoPost(false);
                examList.add(exam);
                continue;
            }
            if (perExam.size() == 8) {
                exam.setDate(perExam.get(2).text());
                exam.setTime(perExam.get(3).text());
                exam.setPosition(perExam.get(4).text());
                exam.setSeat(perExam.get(5).text());
                exam.setStatus(perExam.get(6).text());
                exam.setOther(perExam.get(7).text());
                exam.setNoPost(false);
                examList.add(exam);
            }
        }
        saveExam(examList);
        examPresenter.examResult(getPostExams(examList));
        examList.clear();
    }

    private void saveExam(List<BeanExam> list) {
        SharedPreferences.Editor editor = BaseApplication.getContext().getSharedPreferences("exam_"+mType,
                0).edit();
        for (int i = 0; i < list.size(); i++) {
            BeanExam exam = list.get(i);
            editor.putString("exam"+i, exam.getNum()+"@"+exam.getName()+"@"+exam.getDate()+"@"+exam.getTime()
                    +"@"+exam.getPosition()+"@"+exam.getSeat()+"@"+exam.getStatus()+"@"+exam.getOther()+"@"+exam.getNoPost());
        }
        editor.putInt("size", list.size());
        editor.commit();
        CustomSharedPreferences.getInstance().put("exam_"+mType, true);
    }

    private void loadLocalExam(int type) {
        List<BeanExam> list = new ArrayList<>();
        String examName = "exam_1";
        if (type == 2) {
            examName = "exam_2";
        }
        SharedPreferences preferences = BaseApplication.getContext().getSharedPreferences(examName, 0);
        int size = preferences.getInt("size", 0);
        for (int i = 0; i < size; i++) {
            BeanExam exam = new BeanExam();
            String s = preferences.getString("exam"+i, "");
            String[] ss = s.split("@");
            exam.setNum(ss[0]);
            exam.setName(ss[1]);
            exam.setDate(ss[2]);
            exam.setTime(ss[3]);
            exam.setPosition(ss[4]);
            exam.setSeat(ss[5]);
            exam.setStatus(ss[6]);
            exam.setOther(ss[7]);
            exam.setNoPost(Boolean.valueOf(ss[8]));
            list.add(exam);
        }
        examPresenter.examResult(getPostExams(list));
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
