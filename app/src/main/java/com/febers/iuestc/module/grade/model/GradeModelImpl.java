package com.febers.iuestc.module.grade.model;

import com.febers.iuestc.base.BaseModel;
import com.febers.iuestc.entity.BeanGradeSummary;
import com.febers.iuestc.entity.BeanGrade;
import com.febers.iuestc.module.grade.presenter.GradeContract;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.util.SPUtil;
import com.febers.iuestc.util.ApiUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.febers.iuestc.base.Constants.GRADE_GOT;

public class GradeModelImpl extends BaseModel implements GradeContract.Model {

    private static final String TAG = "GradeModelImpl";
    private GradeContract.Presenter gradePresenter;

    public GradeModelImpl(GradeContract.Presenter gradePresenter) {
        super(gradePresenter);
        this.gradePresenter = gradePresenter;
    }

    @Override
    public void gradeService(Boolean isRefresh, String semester) {
        new Thread(()-> {
            if (!isRefresh) {
                loadSavedGrade();
            } else {
                getHttpData();
            }
        }).start();
    }

    /**
     * 本科生
     */
    @Override
    protected void getHttpData() {
        OkHttpClient client = SingletonClient.getInstance();
        try {
            Request request = new Request.Builder()
                    .url(ApiUtil.ALL_GRADE_URL)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            String stRes = response.body()+"";
            if (!userAuthenticate(stRes)) {
                return;
            }
            resolveUnderGradeHtml(stRes);
        } catch (Exception e) {
            e.printStackTrace();
            serviceError(UNKNOWN_ERROR);
        }
    }

    private void resolveUnderGradeHtml(String sourceCode) {
        List<BeanGradeSummary> allGradeList = new ArrayList<>();
        List<BeanGrade> gradeList = new ArrayList<>();
        if (sourceCode.equals("")) {
            gradePresenter.gradeResult("无", allGradeList, gradeList);
            return;
        }
        try {
            Document document = Jsoup.parse(sourceCode);
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
            GradeStore.saveSourceCode(sourceCode);
            SPUtil.INSTANCE().put(GRADE_GOT, true);

            Collections.sort(allGradeList);
            gradePresenter.gradeResult("成功", allGradeList, gradeList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSavedGrade() {
        new Thread(()-> resolveUnderGradeHtml(GradeStore.getSourceCode())).start();
    }
}
