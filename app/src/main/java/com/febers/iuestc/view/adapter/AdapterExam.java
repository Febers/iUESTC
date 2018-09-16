/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-8 上午11:03
 *
 */

package com.febers.iuestc.view.adapter;

import android.content.Context;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanExam;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;

import java.util.List;

public class AdapterExam extends CommonBaseAdapter<BeanExam> {

    public AdapterExam(Context context, List<BeanExam> datas) {
        this(context, datas, false);
    }

    public AdapterExam(Context context, List<BeanExam> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder viewHolder, BeanExam beanExam, int i) {
        viewHolder.setText(R.id.tv_exam_item_num, String.valueOf(i+1));
        viewHolder.setText(R.id.tv_exam_name, beanExam.getName()+beanExam.getNum());
        viewHolder.setText(R.id.tv_exam_time, beanExam.getTime());
        viewHolder.setText(R.id.tv_exam_position, beanExam.getPosition()+" "+beanExam.getSeat());
        viewHolder.setText(R.id.tv_exam_status, beanExam.getStatus());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_exam;
    }
}
