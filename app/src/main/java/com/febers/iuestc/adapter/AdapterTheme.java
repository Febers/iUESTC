/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-2 上午2:45
 *
 */

package com.febers.iuestc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanTheme;
import com.febers.iuestc.entity.EventTheme;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class AdapterTheme extends RecyclerView.Adapter<AdapterTheme.ViewHolder> {

    private static final String TAG = "AdapterTheme";
    private Context mContext;
    private List<BeanTheme> themeList;

    public AdapterTheme(List<BeanTheme> themeList) {
        this.themeList = themeList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivTheme;
        TextView tvTheme;
        TextView tvIsUsing;

        public ViewHolder(View itemView) {
            super(itemView);

            ivTheme = itemView.findViewById(R.id.iv_item_theme);
            tvTheme = itemView.findViewById(R.id.tv_item_theme_name);
            tvIsUsing = itemView.findViewById(R.id.tv_item_theme_is_using);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_theme, parent, false);
        AdapterTheme.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BeanTheme theme = themeList.get(position);

        holder.ivTheme.setImageResource(theme.getColor());
        holder.tvTheme.setText(theme.getName());
        if (theme.getUsing()) {
            holder.tvIsUsing.setText("使用中");
        } else {
            holder.tvIsUsing.setText("");
        }
        holder.itemView.setOnClickListener( (View view) -> {
            if (!theme.getUsing()) {
                EventBus.getDefault().post(new EventTheme(true, position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return themeList.size();
    }
}
