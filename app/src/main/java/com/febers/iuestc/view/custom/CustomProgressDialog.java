package com.febers.iuestc.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.febers.iuestc.R;

import androidx.appcompat.app.AlertDialog;


public class CustomProgressDialog extends AlertDialog {
    private Context context;
    private View view;
    private AlertDialog dialog;
    private ProgressBar progressBar;
    private TextView tvMsg;

    public CustomProgressDialog(Context context) {
        this(context, "请稍侯");
    }


    @SuppressLint("InflateParams")
    public CustomProgressDialog(Context context, String title) {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.context = context;
        dialog = new AlertDialog.Builder(context).create();
        view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
        progressBar = view.findViewById(R.id.pb_progress);
        tvMsg = view.findViewById(R.id.pb_msg);
        dialog.setCanceledOnTouchOutside(false);
        tvMsg.setText(title);
        dialog.setView(view);
    }

    public void show() {
        dialog.show();
    }
    public void hide() {
        dialog.dismiss();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public boolean isShowing() {
        return super.isShowing();
    }
}
