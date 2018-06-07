/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:02
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
import com.febers.iuestc.module.exam.model.BeanExam;

import java.util.List;

public class AdapterExam extends RecyclerView.Adapter<AdapterExam.ViewHolder>{

    private Context mContext;
    private List<BeanExam> examList;

    public AdapterExam(List<BeanExam> examList) {
        this.examList = examList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber;
        TextView tvExamName;
        TextView tvExamTime;
        TextView tvExamPosition;
        TextView tvExamSeat;
        TextView tvExamStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNumber = itemView.findViewById(R.id.tv_exam_item_num);
            tvExamName = itemView.findViewById(R.id.tv_exam_name);
            tvExamTime = itemView.findViewById(R.id.tv_exam_time);
            tvExamPosition = itemView.findViewById(R.id.tv_exam_position);
            //tvExamSeat = itemView.findViewById(R.id.tv_exam_seat);
            tvExamStatus = itemView.findViewById(R.id.tv_exam_status);
        }
    }
    @Override
    public AdapterExam.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exam, parent, false);
        AdapterExam.ViewHolder holder = new AdapterExam.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterExam.ViewHolder holder, int position) {
        BeanExam exam = examList.get(position);

        holder.tvNumber.setText(String.valueOf(position+1));
        holder.tvExamName.setText(""+exam.getName() + " " + exam.getNum());

        holder.tvExamPosition.setText(exam.getPosition() + " "+ exam.getSeat());
        holder.tvExamTime.setText(exam.getTime());
        //holder.tvExamSeat.setText("座位: "+exam.getSeat());
        holder.tvExamStatus.setText(exam.getStatus());
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }
}
