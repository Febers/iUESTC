/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:03
 *
 */

package com.febers.iuestc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.module.ecard.model.BeanECardPayRecord;
import com.febers.iuestc.module.ecard.view.ECardRecrodActivity;

import java.util.List;

public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.ViewHolder> {

    private List<BeanECardPayRecord.data.consumes> consumesList;
    private Context mContext;

    public AdapterRecord(List<BeanECardPayRecord.data.consumes> consumesList) {
        this.consumesList = consumesList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecordTime;
        TextView tvRecordMoney;
        TextView tvRecordAfterPay;
        TextView tvRecordPosition;
        LinearLayout llRecord;

        public ViewHolder(View itemView) {
            super(itemView);

            tvRecordTime = itemView.findViewById(R.id.tv_pay_record_time);
            tvRecordMoney = itemView.findViewById(R.id.tv_pay_record_money);
            tvRecordAfterPay = itemView.findViewById(R.id.tv_pay_record_after_pay);
            tvRecordPosition = itemView.findViewById(R.id.tv_pay_record_position);
            llRecord = itemView.findViewById(R.id.ll_ecard_record_item);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pay_record, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BeanECardPayRecord.data.consumes consumes = consumesList.get(position);
        holder.tvRecordTime.setText(consumes.getTranstime().replaceAll("\\\"", ""));
        if (consumes.getAmount().contains("-")) {
            holder.tvRecordMoney.setTextColor(ContextCompat.getColor(BaseApplication.getContext(), R.color.colorAccent));
        }
        holder.tvRecordMoney.setText(consumes.getAmount().replaceAll("\\\"", ""));
        holder.tvRecordAfterPay.setText(consumes.getAftbala().replaceAll("\\\"", ""));
        holder.tvRecordPosition.setText(consumes.getPosition().replaceAll("\\\"", ""));
        holder.llRecord.setOnClickListener(v-> {
            mContext.startActivity(new Intent(mContext, ECardRecrodActivity.class));
        });
    }

    public void removeAll() {
        consumesList.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return consumesList.size();
    }
}
