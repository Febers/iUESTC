/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:03
 *
 */

package com.febers.iuestc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.modules.grade.model.BeanGrade;

import java.util.List;

public class AdapterGrade extends RecyclerView.Adapter<AdapterGrade.ViewHolder>{

    private Context context;
    private List<BeanGrade> gradeList;

    public AdapterGrade(List<BeanGrade> gradeList) {
        this.gradeList = gradeList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName;
        TextView tvCourseType;
        TextView tvCourseStudyScore;
        TextView tvCourseFinal;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tv_grade_course_name);
            tvCourseType = itemView.findViewById(R.id.tv_grade_course_type);
            tvCourseStudyScore = itemView.findViewById(R.id.tv_grade_course_studyscore);
            tvCourseFinal = itemView.findViewById(R.id.tv_grade_course_final);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grade_per, parent, false);
        AdapterGrade.ViewHolder holder = new AdapterGrade.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BeanGrade grade = gradeList.get(position);
        holder.tvCourseName.setText(grade.getCourseName());
        holder.tvCourseType.setText(grade.getCourseType());
        holder.tvCourseStudyScore.setText("学分:"+grade.getStudyScore());
        holder.tvCourseFinal.setText(grade.getFinalScore());
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }
}
