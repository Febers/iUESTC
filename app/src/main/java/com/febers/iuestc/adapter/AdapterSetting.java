/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-8 下午4:23
 *
 */

package com.febers.iuestc.adapter;

import android.content.Context;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanSetting;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;

import java.util.List;

public class AdapterSetting extends CommonBaseAdapter<BeanSetting> {

    public AdapterSetting(Context context, List<BeanSetting> datas) {
        this(context, datas, false);
    }

    public AdapterSetting(Context context, List<BeanSetting> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder viewHolder, BeanSetting beanSetting, int i) {
        viewHolder.setText(R.id.tv_user_setting_item, beanSetting.getName());
        viewHolder.setBgResource(R.id.iv_user_setting_item, beanSetting.getImageId());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_more_setting;
    }
}
