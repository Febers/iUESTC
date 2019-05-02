package com.febers.iuestc.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanUpdate;

import androidx.appcompat.app.AlertDialog;

public class CustomUpdateDialog extends AlertDialog {

    private AlertDialog dialog;
    private Context context;
    private Button btnCancel;
    private Button btnEnter;

    @SuppressLint("InflateParams")
    public CustomUpdateDialog(Context context, BeanUpdate update) {
        super(context);
        this.context = context;
        dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);
        TextView tvTitle = view.findViewById(R.id.tv_update_title);
        TextView tvSubTitle = view.findViewById(R.id.tv_update_sub_title);
        TextView tvBody = view.findViewById(R.id.tv_update_detail);
        btnCancel = view.findViewById(R.id.btn_update_cancel);
        btnEnter = view.findViewById(R.id.btn_update_enter);

        tvTitle.setText(String.format("新版本:%s", update.getVersionName()));
        tvSubTitle.setText(String.format("大小:%s", update.getSize()));
        tvBody.setText(String.format("更新说明:\n%s", update.getBody()));
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

    public Button getBtnCancel() {
        return btnCancel;
    }
}
