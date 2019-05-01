/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-3 下午2:56
 *
 */

package com.febers.iuestc.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanUserItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdapterUser extends ArrayAdapter<BeanUserItem> {

    private int resourceId;

    public AdapterUser(Context context, int textViewResourceId, List<BeanUserItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BeanUserItem userItem = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvUserItemParam = view.findViewById(R.id.tv_item_user_param);
            viewHolder.tvUserItemValue = view.findViewById(R.id.tv_item_user_value);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvUserItemParam.setText(userItem.getParam());
        viewHolder.tvUserItemValue.setText(userItem.getValue());
        return view;
    }

    class ViewHolder {
        TextView tvUserItemParam;
        TextView tvUserItemValue;
    }
}
