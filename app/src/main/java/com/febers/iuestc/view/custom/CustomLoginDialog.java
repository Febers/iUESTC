/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-7 下午11:10
 *
 */

package com.febers.iuestc.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AlertDialog;

public class CustomLoginDialog extends AlertDialog{

    private Context context;
    private TextInputEditText tieUserId, tieUserPw;
    private TextView tvTitle;
    private AlertDialog dialog;
    private OnLoginListener loginListener = null;
    private String stId = "";
    private String stPw = "";

    @SuppressLint("InflateParams")
    public CustomLoginDialog(Context context) {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.context = context;
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        tvTitle = view.findViewById(R.id.tv_dialog_title);
        tieUserId = view.findViewById(R.id.tie_user_id);
        tieUserId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stId = s.toString();
            }
        });
        tieUserPw = view.findViewById(R.id.tie_user_pw);
        tieUserPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                stPw = s.toString();
            }
        });
        Button btnEnter = view.findViewById(R.id.bt_dialog_login_enter);
        Button btnCancel = view.findViewById(R.id.bt_dialog_login_cancel);
        btnEnter.setOnClickListener(view1 -> loginListener.onClick(view1));
        btnCancel.setOnClickListener(view12 -> loginListener.onClick(view12));
        dialog.setView(view);
    }

    public String getStId() {
        return stId;
    }

    public String getStPw() {
        return stPw;
    }

    public TextInputEditText getTieUserId() {
        return tieUserId;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setShowTitle(Boolean show) {
        if (show) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.INVISIBLE);
        }
    }

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.hide();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public boolean isShowing() {
        return dialog.isShowing();
    }

    //定义点击监听接口
    public interface OnLoginListener {
        void onClick(View v);
    }

    public void setOnLoginListener(OnLoginListener onLoginListener) {
        loginListener = onLoginListener;
    }
}
