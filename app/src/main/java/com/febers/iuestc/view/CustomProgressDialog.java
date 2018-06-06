package com.febers.iuestc.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.febers.iuestc.R;


public class CustomProgressDialog extends AlertDialog {
    private Context context;
    private View view;
    private AlertDialog dialog;
    private ProgressBar progressBar;

    public CustomProgressDialog(Context context) {
        this(context, "请稍侯");
    }

    public CustomProgressDialog(Context context, String title) {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.context = context;
        dialog = new AlertDialog.Builder(context).create();
        view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
        progressBar = view.findViewById(R.id.pb_progress);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle(title);
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
