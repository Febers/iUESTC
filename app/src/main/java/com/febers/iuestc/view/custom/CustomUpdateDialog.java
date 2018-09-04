/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-9 下午2:30
 *
 */

package com.febers.iuestc.view.custom;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanUpdate;

public class CustomUpdateDialog extends AlertDialog {

    private AlertDialog dialog;
    private Context context;
    private View view;
    private Button btnCancal;
    private Button btnEnter;

    public CustomUpdateDialog(Context context, BeanUpdate update) {
        super(context);
        this.context = context;
        dialog = new AlertDialog.Builder(context).create();
        view = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);
        TextView tvTitle = view.findViewById(R.id.tv_update_title);
        TextView tvSubTitle = view.findViewById(R.id.tv_update_sub_title);
        TextView tvBody = view.findViewById(R.id.tv_update_detail);
        btnCancal = view.findViewById(R.id.btn_update_cancal);
        btnEnter = view.findViewById(R.id.btn_update_enter);

        tvTitle.setText("新版本:"+update.getVersionName());
        tvSubTitle.setText("大小:"+update.getSize());
        tvBody.setText("更新说明:\n"+update.getBody());
        dialog.setView(view);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public Button getBtnEnter() {
        return btnEnter;
    }

    public Button getBtnCancal() {
        return btnCancal;
    }
}
