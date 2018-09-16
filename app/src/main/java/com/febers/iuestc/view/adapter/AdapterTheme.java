/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-8 下午4:56
 *
 */

package com.febers.iuestc.view.adapter;

import android.content.Context;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanTheme;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;

import java.util.List;

public class AdapterTheme extends CommonBaseAdapter<BeanTheme> {

    public AdapterTheme(Context context, List<BeanTheme> datas) {
        this(context, datas, false);
    }

    public AdapterTheme(Context context, List<BeanTheme> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder viewHolder, BeanTheme beanTheme, int i) {
        viewHolder.setBgResource(R.id.iv_item_theme, beanTheme.getColor());
        viewHolder.setText(R.id.tv_item_theme_name, beanTheme.getName());
        if (beanTheme.getUsing()) {
            viewHolder.setText(R.id.tv_item_theme_is_using, "使用中");
        } else {
            viewHolder.setText(R.id.tv_item_theme_is_using, "");
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_theme;
    }
}
