/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-8 下午12:09
 *
 */

package com.febers.iuestc.view.adapter;

import android.content.Context;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanGrade;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;

import java.util.List;

public class AdapterGrade extends CommonBaseAdapter<BeanGrade> {

    public AdapterGrade(Context context, List<BeanGrade> datas) {
        this(context, datas, false);
    }

    public AdapterGrade(Context context, List<BeanGrade> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder viewHolder, BeanGrade beanGrade, int i) {
        viewHolder.setText(R.id.tv_grade_course_name, beanGrade.getCourseName());
        viewHolder.setText(R.id.tv_grade_course_type, beanGrade.getCourseType());
        viewHolder.setText(R.id.tv_grade_course_studyscore, "学分:"+beanGrade.getStudyScore());
        viewHolder.setText(R.id.tv_grade_course_final, beanGrade.getFinalScore());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_grade_per;
    }
}
