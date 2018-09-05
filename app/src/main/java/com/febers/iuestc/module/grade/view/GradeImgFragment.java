/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.grade.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.module.grade.presenter.GradeContract;
import com.febers.iuestc.entity.BeanGradeSummary;
import com.febers.iuestc.entity.BeanGrade;
import com.febers.iuestc.module.grade.presenter.GradePresenterImpl;
import com.febers.iuestc.util.GradeUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GradeImgFragment extends BaseFragment implements GradeContract.View{

    private static final String TAG = "GradeImgFragment";
    private LineChart lineChart;
    private ScatterChart scatterChart;
    private List<BeanGrade> gradeList = new ArrayList<>();
    private GradeContract.Presenter gradePresenter = new GradePresenterImpl(this);

    @Override
    protected int setContentView() {
        return R.layout.fragment_grade_img;
    }

    @Override
    protected void initView() {
        initLineChart();
        initScatterChart();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        dateRequest(false);
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
        gradePresenter.gradeRequest(isRefresh);
    }

    /**
     * x轴为学分， y轴为成绩
     */
    private void initScatterChart() {
        scatterChart = getActivity().findViewById(R.id.scatter_chart_grade);
        scatterChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        scatterChart.getAxisRight().setEnabled(false);
        scatterChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        Description description = new Description();
        description.setText("");
        scatterChart.setDescription(description);
        scatterChart.setNoDataText("加载中");
        scatterChart.setScaleEnabled(true);
        scatterChart.setDragEnabled(true);  //设置是否可拖拽
        scatterChart.setPinchZoom(true);    //设置xy轴是否同时可缩放
    }

    private void initLineChart() {
        lineChart = getActivity().findViewById(R.id.line_chart_grade);
        lineChart.getDescription().setText("");
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        lineChart.setNoDataText("加载中");
    }

    @Override
    public void showGrade(final List<BeanGradeSummary> allGrades, final List<BeanGrade> grades) {
        getActivity().runOnUiThread(()-> showLineChart(allGrades));
        getActivity().runOnUiThread(()-> showScatterChart(grades));
    }

    /**
     *显示折线图
     */
    private void showLineChart(List<BeanGradeSummary> grades) {
        Collections.sort(grades);
        List<Entry> xyVals = new ArrayList<>();

        //获取并设置相应的x轴
        XAxis xl = lineChart.getXAxis();
        xl.setDrawLabels(false);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        for (int i = 0; i < grades.size(); i++) {
            xyVals.add(new Entry(i, Float.valueOf(grades.get(i).getAverageGPA())));
        }
        YAxis yl = lineChart.getAxisLeft();
        yl.setDrawLabels(false);

        LineDataSet lineDataSet = new LineDataSet(xyVals, "绩点-学年统计图");
        lineDataSet.setCircleColors(ColorTemplate.COLORFUL_COLORS);
        LineData lineData = new LineData(lineDataSet);
        lineData.setHighlightEnabled(false);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }


    /**
     * 显示散点图
     */
    private void showScatterChart(List<BeanGrade> grades) {
        gradeList = grades;
        List<Entry> xyVals = new ArrayList<>();
        //设置纵轴,为分数
        for (int i = 0; i < gradeList.size(); i++) {
            float score = GradeUtil.getScoreFromGrade(gradeList.get(i).getFinalScore());
            float studyScore = Float.valueOf(gradeList.get(i).getStudyScore());
            xyVals.add(new Entry(score, studyScore));
        }
        Collections.sort(xyVals, new EntryXComparator());   //非常重要
        ScatterDataSet scatterDataSet = new ScatterDataSet(xyVals, "学分-成绩分布图");
        scatterDataSet.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        scatterDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        ScatterData scatterData = new ScatterData(scatterDataSet);
        scatterData.setDrawValues(false);
        scatterData.setHighlightEnabled(false);
        scatterChart.setData(scatterData);
        scatterChart.invalidate();
    }

    @Override
    public void statusToFail() {
    }
}
