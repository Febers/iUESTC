/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-5 下午10:45
 *
 */

package com.febers.iuestc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.module.user.model.BeanSetting;

import java.util.List;

public class AdapterSetting extends ArrayAdapter<BeanSetting> {

    private int resourceId;

    public AdapterSetting(Context context, int textViewResourceId, List<BeanSetting> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BeanSetting beanSetting = getItem(position);

        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.settingImage = view.findViewById(R.id.iv_user_setting_item);
            viewHolder.settingText = view.findViewById(R.id.tv_user_setting_item);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.settingImage.setImageResource(beanSetting.getImageId());
        viewHolder.settingText.setText(beanSetting.getName());
        return view;
    }

    class ViewHolder {
        TextView settingText;
        ImageView settingImage;
    }
}
